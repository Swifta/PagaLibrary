package com.ng.mats.psa.mt.paga.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.ng.mats.psa.mt.paga.data.MoneyTransfer;
import com.ng.mats.psa.mt.paga.data.PagaPropertyValues;
import com.ng.mats.psa.mt.paga.data.PagaResponse;

public class RestClient {
	// http://localhost:8080/RESTfulExample/json/product/get
	private static final Logger logger = Logger.getLogger(RestClient.class
			.getName());

	public static void main(String[] args) throws JSONException {
		logger.info("Starting connection....");
		MoneyTransfer moneyTransfer = new PagaPropertyValues()
				.getPropertyValues();
		PagaClient pagaClient = new PagaClient();
		// pagaClient.performCashIn(moneyTransfer);
		PagaResponse pagaResponse = pagaClient.performCashOut(moneyTransfer);
		System.out.println(pagaResponse.getDestinationpartnerbalanceafter());
		System.out.println(pagaResponse.getFinancialtransactionid());
		System.out.println(pagaResponse.getOrginatingpartnerbalanceafter());
		System.out.println(pagaResponse.getOrginatingpartnerfee());
		System.out.println(pagaResponse.getResponseCode());
		System.out.println(pagaResponse.getResponseDescription());
		// pagaClient.performWalletToBank(moneyTransfer);

	}

	public static String connectToPaga(String endPointUrl,
			String stringBuilder, JSONObject inputObject,
			MoneyTransfer moneyTransfer) throws JSONException {
		String output = "";
		System.out.println("stringBuilder: " + stringBuilder);
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

			// String hashedString =
			// "99b0196299352892423c5942530db7b7ad27bec9338d0145de3dd361e782570f279de3932815167fe96d5ab9b5406daa6736e9121a18d3ccca9b26c158e7301d";
			String hashedString = RestClient.hashSHA512(stringBuilder, 0, true)
					.toString();

			// 1587aca733d40bfc77b5e62a54d66fa0eb79d3ebd4482ef46793074e65eef64032c07530c0500155b7b4a4735f5427486adac0383a302905a57d644b77feaea5

			// inputObject.getString("referenceNumber")

			String input = "{\"referenceNumber\":" + "\""
					+ inputObject.getString("referenceNumber")
					+ "\",\"locale\":" + "\"" + ""
					+ "\",\"customerPhoneNumber\":" + "\""
					+ inputObject.getString("customerPhoneNumber")
					+ "\",\"amount\":" + inputObject.getString("amount")
					+ ", \"withdrawalCode\":" + "\""
					+ inputObject.getString("withdrawalCode") + "\"" + "}";

			// String input = "{\"referenceNumber\":" + "973908158735"
			// + ",\"locale\":" + "\"" + ""
			// + "\",\"customerPhoneNumber\":" + "\""
			// + inputObject.getString("customerPhoneNumber")
			// + "\",\"amount\":" + inputObject.getString("amount")
			// + ", \"withdrawalCode\":" + "\""
			// + inputObject.getString("withdrawalCode") + "\"" + "}";

			// String input = inputObject.toString();

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

		} catch (IOException exp) {
			exp.printStackTrace();
		}
		logger.info("Output from Server .... \n" + output);

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
		/*
		 * System.getProperties().remove("javax.net.ssl.trustStore");
		 * System.getProperties().remove("javax.net.ssl.trustStoreType");
		 * System.getProperties().remove("javax.net.ssl.trustStorePassword");
		 * System.getProperties().remove("https.protocols");
		 */
		/*
		 * System.setProperty("javax.net.ssl.trustStore", pKeyFilePath);
		 * System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		 * System.setProperty("javax.net.ssl.trustStorePassword", pKeyPassword);
		 * System.setProperty("jsse.enableSNIExtension", "false");
		 * System.setProperty("javax.net.debug", "ssl");
		 * System.setProperty("https.protocols", "SSLv3");
		 * System.setProperty("https.protocols", "TLSV");
		 */
		try {

			File pKeyFile = new File(pKeyFilePath);
			logger.info("-------------------------After locating the jks====="
					+ pKeyFilePath);
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance("SunX509");
			KeyStore keyStore = KeyStore.getInstance("JKS");
			logger.info("-------------------------After instantiating jks");
			InputStream keyInput = new FileInputStream(pKeyFile);
			logger.info("-------------------------After instantiating the File input stream");
			keyStore.load(keyInput, pKeyPassword.toCharArray());
			logger.info("-------------------------After loading the jks and password");
			keyInput.close();
			logger.info("-------------------------After closing the key input");
			keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());
			logger.info("-------------------------After initiating the key manager factory");
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(keyManagerFactory.getKeyManagers(), null,
					new SecureRandom());
			socketFactory = context.getSocketFactory();
			logger.info("-------------------------After getting socket factory");
		} catch (NoSuchAlgorithmException | KeyStoreException | IOException
				| UnrecoverableKeyException | KeyManagementException
				| java.security.cert.CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socketFactory;
	}
}
