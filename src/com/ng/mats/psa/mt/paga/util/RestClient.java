package com.ng.mats.psa.mt.paga.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.ng.mats.psa.mt.paga.data.MoneyTransfer;
import com.ng.mats.psa.mt.paga.data.PagaPropertyValues;
import com.ng.mats.psa.mt.paga.data.PagaResponse;

//import java.util.logging.Logger;

public class RestClient {
	// http://localhost:8080/RESTfulExample/json/product/get
	// private static final Logger logger = Logger.getLogger(RestClient.class
	// .getName());
	private static final Log logger = LogFactory.getLog(RestClient.class);

	public static void main(String[] args) throws JSONException {
		logger.info("Starting connection....");
		MoneyTransfer moneyTransfer = new PagaPropertyValues()
				.getPropertyValues();
		PagaClient pagaClient = new PagaClient();
		// pagaClient.performCashOut(moneyTransfer);
		// pagaClient.payMerchant(moneyTransfer);
		// pagaClient.performCashIn(moneyTransfer);
		PagaResponse pagaResponse = pagaClient.purchaseAirtime(moneyTransfer);
		// PagaResponse pagaResponse = pagaClient.getMerchant(moneyTransfer);
		// PagaResponse pagaResponse = pagaClient
		// .getMerchantServices(moneyTransfer);
		System.out.println(pagaResponse.getDestinationpartnerbalanceafter());
		System.out.println(pagaResponse.getFinancialtransactionid());
		System.out.println("its me>>>>" + pagaResponse.getTrxid());
		System.out.println(pagaResponse.getOrginatingpartnerbalanceafter());
		System.out.println(pagaResponse.getOrginatingpartnerfee());
		System.out.println(pagaResponse.getResponseCode());
		System.out.println(pagaResponse.getResponseDescription());
		System.out.println(pagaResponse.getTrxid());

		// pagaClient.performWalletToBank(moneyTransfer);

	}

	public static String connectToPagaa(String endPointUrl,
			String stringBuilder, JSONObject inputObject,
			MoneyTransfer moneyTransfer) throws JSONException {
		String output = "";
		System.out.println("stringBuilder: " + stringBuilder);
		HttpsURLConnection conn = null;
		try {
			String appUrl = moneyTransfer.getAppUrl().concat(endPointUrl);
			URL url = new URL(
			// "https://yfs4jh264ds.mypaga.com/paga-web/customer/"
					appUrl);

			conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("principal", moneyTransfer.getAppUsername());
			// agent api key user name
			conn.setRequestProperty("credentials", moneyTransfer.getPassword());
			conn.setSSLSocketFactory(getFactory(
					moneyTransfer.getTrustStoreLocation(),
					moneyTransfer.getTrustStorePassword()));
			conn.setUseCaches(false);
			conn.setConnectTimeout(30000);
			conn.setDoOutput(true);
			// conn.setDoInput(true);
			// agent api key password param value number) birth identification
			// number
			logger.info("after setting connection parameters");

			// String hashedString =
			// "99b0196299352892423c5942530db7b7ad27bec9338d0145de3dd361e782570f279de3932815167fe96d5ab9b5406daa6736e9121a18d3ccca9b26c158e7301d";
			String hashedString = RestClient.hashSHA512(stringBuilder, 0, true)
					.toString();

			// 1587aca733d40bfc77b5e62a54d66fa0eb79d3ebd4482ef46793074e65eef64032c07530c0500155b7b4a4735f5427486adac0383a302905a57d644b77feaea5

			// inputObject.getString("referenceNumber")

			String input = "";

			if (inputObject.isNull("identificationType")
					&& moneyTransfer.getWithdrawalCode() == null) {

				input = "{\"referenceNumber\":" + "\""
						+ inputObject.getString("referenceNumber")
						+ "\",\"locale\":" + "\"" + ""
						+ "\",\"customerPhoneNumber\":" + "\""
						+ inputObject.getString("customerPhoneNumber")
						+ "\",\"amount\":" + inputObject.getString("amount")
						+ "}";
			}

			if (inputObject.isNull("billerPublicId")
					&& inputObject.isNull("identificationType")
					&& moneyTransfer.getWithdrawalCode() != null) {

				input = "{\"referenceNumber\":" + "\""
						+ inputObject.getString("referenceNumber")
						+ "\",\"locale\":" + "\"" + ""
						+ "\",\"customerPhoneNumber\":" + "\""
						+ inputObject.getString("customerPhoneNumber")
						+ "\",\"amount\":" + inputObject.getString("amount")
						+ ", \"withdrawalCode\":" + "\""
						+ inputObject.getString("withdrawalCode") + "\"" + "}";

			}

			if (inputObject.isNull("billerPublicId")
					&& inputObject.has("identificationType")) {

				input = "{\"referenceNumber\":" + "\""
						+ inputObject.getString("referenceNumber")
						+ "\",\"locale\":" + "\"" + ""
						+ "\",\"receiverPhoneNumber\":" + "\""
						+ inputObject.getString("customerPhoneNumber")
						+ "\",\"amount\":" + inputObject.getString("amount")
						+ ", \"quantity\":" + 1 + ", \"message\":" + "\""
						+ inputObject.getString("message") + "\""
						+ ", \"identificationType\":" + "\""
						+ inputObject.getString("identificationType") + "\""
						+ ", \"identificationNumber\":" + "\""
						+ inputObject.getString("identificationNumber") + "\""
						+ ", \"operatorPublicId\":" + 3 + "}";

			}

			if (inputObject.has("billerPublicId")
					&& inputObject.has("serviceName")) {

				input = "{\"referenceNumber\":" + "\""
						+ inputObject.getString("referenceNumber")
						+ "\",\"locale\":" + "\"" + ""
						+ "\",\"billerPublicId\":" + "\""
						+ inputObject.getString("billerPublicId")
						+ "\",\"customerCodeAtBiller\":" + "\""
						+ inputObject.getString("customerCodeAtBiller")
						+ "\", \"customerPhoneNumber\":" + "\""
						+ inputObject.getString("customerPhoneNumber") + "\""
						+ ",\"amount\":" + inputObject.getString("amount")
						+ ", \"customerFirstName\":" + "\""
						+ inputObject.getString("customerFirstName") + "\""
						+ ", \"customerLastName\":" + "\""
						+ inputObject.getString("customerLastName") + "\""
						+ ", \"serviceName\":" + "\""
						+ inputObject.getString("serviceName") + "\"" + "}";
				// + ", \"serviceName\":" + "\"" + "" + "\" }";

			}
			if (inputObject.has("billerPublicId")
					&& inputObject.isNull("customerCodeAtBiller")) {

				input = "{\"billerPublicId\":" + "\""
						+ inputObject.getString("billerPublicId") + "\" }";
				// + ", \"serviceName\":" + "\"" + "" + "\" }";

			}

			// String input = "{\"referenceNumber\":" + "\""
			// + inputObject.getString("referenceNumber")
			// + "\",\"locale\":" + "\"" + ""
			// + "\",\"customerPhoneNumber\":" + "\""
			// + inputObject.getString("customerPhoneNumber")
			// + "\",\"amount\":" + inputObject.getString("amount")
			// + ", \"withdrawalCode\":" + "\""
			// + inputObject.getString("withdrawalCode") + "\"" + "}";

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

			// DataOutputStream wr = new
			// DataOutputStream(conn.getOutputStream());
			// wr.writeBytes(input);
			// wr.flush();
			// wr.close();
			//
			// InputStream is = conn.getInputStream();
			// BufferedReader rd = new BufferedReader(new
			// InputStreamReader(is));
			// String line;
			// StringBuffer response = new StringBuffer();
			// while ((line = rd.readLine()) != null) {
			// response.append(line);
			// response.append('\r');
			// }
			// rd.close();
			// logger.info("output from server>>>>>>>> " + response);
			// return response.toString();
			//
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// return null;
			//
			// } finally {
			//
			// if (conn != null) {
			// conn.disconnect();
			// }
			// }

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

				logger.info("outpput from server:::::" + output);
				break;
			}
			conn.disconnect();

		} catch (IOException exp) {
			exp.printStackTrace();
		}
		logger.info("Output from Server .... \n" + output);

		return output;
	}

	public static String connectTopaga(String stringBuilder, String url,
			JSONObject inputObject, MoneyTransfer moneyTransfer)
			throws Exception {
		HttpURLConnection urlConnection = null;
		String resultData;
		URL endpoint = null;

		StringReader data = null;
		String contentType = "application/json";
		try {

			// agent api key password param value number) birth identification
			// number
			logger.info("after setting connection parameters");

			// String hashedString =
			// "99b0196299352892423c5942530db7b7ad27bec9338d0145de3dd361e782570f279de3932815167fe96d5ab9b5406daa6736e9121a18d3ccca9b26c158e7301d";
			String hashedString = RestClient.hashSHA512(stringBuilder, 0, true)
					.toString();

			// 1587aca733d40bfc77b5e62a54d66fa0eb79d3ebd4482ef46793074e65eef64032c07530c0500155b7b4a4735f5427486adac0383a302905a57d644b77feaea5

			// inputObject.getString("referenceNumber")

			String input = "";

			if (moneyTransfer.getWithdrawalCode() == null) {

				input = "{\"referenceNumber\":" + "\""
						+ inputObject.getString("referenceNumber")
						+ "\",\"locale\":" + "\"" + ""
						+ "\",\"customerPhoneNumber\":" + "\""
						+ inputObject.getString("customerPhoneNumber")
						+ "\",\"amount\":" + inputObject.getString("amount")
						+ "}";
			} else {

				input = "{\"referenceNumber\":" + "\""
						+ inputObject.getString("referenceNumber")
						+ "\",\"locale\":" + "\"" + ""
						+ "\",\"customerPhoneNumber\":" + "\""
						+ inputObject.getString("customerPhoneNumber")
						+ "\",\"amount\":" + inputObject.getString("amount")
						+ ", \"withdrawalCode\":" + "\""
						+ inputObject.getString("withdrawalCode") + "\"" + "}";

			}
			String appUrl = moneyTransfer.getAppUrl().concat(url);
			data = new StringReader(input);
			endpoint = new URL(appUrl);
			urlConnection = (HttpURLConnection) endpoint.openConnection();
			try {
				// urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				// urlConnection.setUseCaches(false);
				// urlConnection.setRequestProperty("connection", "close");
				urlConnection.setRequestProperty("hash", hashedString);
				urlConnection.setRequestProperty("principal",
						moneyTransfer.getAppUsername());
				// agent api key user name
				urlConnection.setRequestProperty("credentials",
						moneyTransfer.getPassword());
				((HttpsURLConnection) urlConnection)
						.setSSLSocketFactory(getFactory(
								moneyTransfer.getTrustStoreLocation(),
								moneyTransfer.getTrustStorePassword()));
				// urlConnection.setUseCaches(false);
				urlConnection.setRequestMethod("POST");
			} catch (ProtocolException e) {
				throw new Exception(
						"Shouldn't happen: HttpURLConnection doesn't support POST??",
						e);
			}
			// urlConnection.setDoOutput(true);
			// urlConnection.setDoInput(true);
			// urlConnection.setUseCaches(false);
			// urlConnection.setAllowUserInteraction(false);
			urlConnection.setRequestProperty("Content-type", contentType);
			OutputStream out = urlConnection.getOutputStream();
			try {
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				pipe(data, writer);
				// writer.flush();
				writer.close();
			} catch (IOException e) {
				throw new Exception("IOException while posting data", e);
			} finally {
				if (out != null) {
					out.close();
				}
			}
			InputStream in = urlConnection.getInputStream();
			resultData = getStringFromInputStream(in);
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			throw new Exception("Connection error (is server running at "
					+ endpoint + " ?): " + e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		System.out.println(resultData);
		return resultData;
	}

	/**
	 * Reads the response from inputstream
	 * 
	 * @param is
	 *            InputStream
	 * @return String
	 */
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is,
					Charset.defaultCharset()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Pipes everything from the reader to the writer via a buffer
	 * 
	 * @param reader
	 *            Reader
	 * @param writer
	 *            Writer
	 * @throws java.io.IOException
	 *             If piping fails
	 */
	private static void pipe(Reader reader, Writer writer) throws IOException {
		char[] buf = new char[1024];
		int read;
		while ((read = reader.read(buf)) >= 0) {
			writer.write(buf, 0, read);
		}
		writer.flush();
	}

	public static String sendPost(String stringBuilder, String url,
			JSONObject inputObject, MoneyTransfer moneyTransfer)
			throws Exception {
		// String output = "";
		logger.info("stringBuilder: " + stringBuilder);
		// String url = "https://selfsolve.apple.com/wcResults.do";
		String appUrl = moneyTransfer.getAppUrl().concat(url);
		URL obj = new URL(appUrl);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		String contentType = "application/json";

		String hashedString = RestClient.hashSHA512(stringBuilder, 0, true)
				.toString();

		// add reuqest header
		con.setRequestMethod("POST");
		// con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("hash", hashedString);
		con.setRequestProperty("principal", moneyTransfer.getAppUsername());
		// agent api key user name
		con.setRequestProperty("credentials", moneyTransfer.getPassword());
		((HttpsURLConnection) con).setSSLSocketFactory(getFactory(
				moneyTransfer.getTrustStoreLocation(),
				moneyTransfer.getTrustStorePassword()));
		// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-type", contentType);

		// String urlParameters =
		// "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);

		String input = "";

		if (moneyTransfer.getWithdrawalCode() == null) {

			input = "{\"referenceNumber\":" + "\""
					+ inputObject.getString("referenceNumber")
					+ "\",\"locale\":" + "\"" + ""
					+ "\",\"customerPhoneNumber\":" + "\""
					+ inputObject.getString("customerPhoneNumber")
					+ "\",\"amount\":" + inputObject.getString("amount") + "}";
		} else {

			input = "{\"referenceNumber\":" + "\""
					+ inputObject.getString("referenceNumber")
					+ "\",\"locale\":" + "\"" + ""
					+ "\",\"customerPhoneNumber\":" + "\""
					+ inputObject.getString("customerPhoneNumber")
					+ "\",\"amount\":" + inputObject.getString("amount")
					+ ", \"withdrawalCode\":" + "\""
					+ inputObject.getString("withdrawalCode") + "\"" + "}";

		}

		logger.info("The hashed string ------>" + hashedString);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(input);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		logger.info("\nSending 'POST' request to URL : " + appUrl);
		// System.out.println("\nSending 'POST' request to URL : " + url);
		logger.info("Post parameters : " + input);
		logger.info("Response Code : " + responseCode);
		// System.out.println("Post parameters : " + input);
		// System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		return response.toString();

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
