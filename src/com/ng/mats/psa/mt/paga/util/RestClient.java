package com.ng.mats.psa.mt.paga.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.json.me.JSONObject;

import com.ng.mats.psa.mt.paga.data.MoneyTransfer;
import com.ng.mats.psa.mt.paga.data.PagaPropertyValues;

public class RestClient {
	// http://localhost:8080/RESTfulExample/json/product/get
	private static final Logger logger = Logger.getLogger(RestClient.class
			.getName());

	public static void main(String[] args) {
		logger.info("Starting connection....");
		MoneyTransfer moneyTransfer = new PagaPropertyValues()
				.getPropertyValues();
		PagaClient pagaClient = new PagaClient();
		// pagaClient.performCashIn(moneyTransfer);
		pagaClient.performCashOut(moneyTransfer);
		// pagaClient.performWalletToBank(moneyTransfer);

	}

	public static String connectToPaga(String endPointUrl,
			String stringBuilder, JSONObject inputObject,
			MoneyTransfer moneyTransfer) {
		String output = "";
		try {
			String appUrl = moneyTransfer.getAppUrl().concat(endPointUrl);
			URL url = new URL(
			// "https://yfs4jh264ds.mypaga.com/paga-web/customer/"
					appUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("principal", moneyTransfer.getAppUsername());
			// agent api key user name
			conn.setRequestProperty("credentials", moneyTransfer.getPassword());
			conn.setSSLSocketFactory(getFactory(
					moneyTransfer.getTrustStoreLocation(),
					moneyTransfer.getTrustStorePassword()));

			// agent api key password param value number) birth identification
			// number
			logger.info("after setting connection parameters");

			String hashedString = RestClient.hashSHA512(stringBuilder, 1, true)
					.toString();

			String input = inputObject.toString();

			conn.setRequestProperty("hash", hashedString);
			logger.info("URL ---->" + appUrl);
			logger.info("String before hashing --->" + stringBuilder);
			logger.info("Principal ---->" + moneyTransfer.getAppUsername());
			logger.info("Credential ---->" + moneyTransfer.getPassword());
			logger.info("The input parameters --->" + input);
			logger.info("The hashed string ------>" + hashedString);
			// input should be the data formatted as a JSON.

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.close();
			if (conn.getResponseCode() != 201 && conn.getResponseCode() != 200) {

				logger.info("Failed : HTTP error code" + conn.getResponseCode());
				// throw new RuntimeException("Failed : HTTP error code"
				// + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				logger.info(output);
				break;
			}
			conn.disconnect();

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
		return output;
	}

	public static String generateReferencenNumber(int length) {
		// random PIN number
		String transactionId = "";
		transactionId = RandomStringUtils.randomNumeric(length);
		return transactionId;
	}

	public static Object hashSHA512(String message, int iterations,
			boolean returnHex) {
		try {
			MessageDigest mda = MessageDigest.getInstance("SHA-512");
			byte[] digest = mda.digest(message.getBytes());
			if (returnHex) {
				return new String(Hex.encodeHex(digest));
			} else {
				return digest;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static SSLSocketFactory getFactory(String pKeyFilePath,
			String pKeyPassword) {
		SSLSocketFactory socketFactory = null;
		logger.info("------------------------Before creating the File");
		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			System.setProperty("javax.net.debug", "ssl");
			System.setProperty("https.protocols", "SSLv3");
			// System.setProperty("https.protocols", "TLSV");
			File pKeyFile = new File(pKeyFilePath);
			logger.info("-------------------------After locating the jks");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance("SunX509");
			KeyStore keyStore = KeyStore.getInstance("JKS");

			InputStream keyInput = new FileInputStream(pKeyFile);
			keyStore.load(keyInput, pKeyPassword.toCharArray());
			keyInput.close();

			keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());

			SSLContext context = SSLContext.getInstance("SSL");
			context.init(keyManagerFactory.getKeyManagers(), null,
					new SecureRandom());
			socketFactory = context.getSocketFactory();
			;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generatedcatch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.security.cert.CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socketFactory;
	}
}
