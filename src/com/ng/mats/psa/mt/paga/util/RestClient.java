package com.ng.mats.psa.mt.paga.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;

public class RestClient {
	// http://localhost:8080/RESTfulExample/json/product/get
	private static final Logger logger = Logger.getLogger(RestClient.class
			.getName());

	public static void main(String[] args) {
		logger.info("Starting connection....");
		try {
			URL url = new URL(
					"http://www.mypaga.com/paga- webservices/agent-rest/secured/registerCustomer");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("principal",
					"AA6EEB78-BD92-4D0B-A87D-E3DB46C53B70");
			// agent api key user name
			conn.setRequestProperty("credentials", "password");
			// agent api key password param value number) birth identification
			// number
			logger.info("after setting connection parameters");
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("09012939283"); // referenceNumber
			sBuilder.append("08023434343"); // customer (phone
			sBuilder.append("amare"); // customer first name
										// sBuilder.append("worku"); //customer
										// last name
										// sBuilder.append("27-01-1982");
										// //customer date of
			sBuilder.append("343534534535"); // customer
												// sBuilder.append("0123");
												// //agent api-key
			conn.setRequestProperty("hash",
					RestClient.hashSHA512(sBuilder.toString(), 1, true)
							.toString());
			// input should be the data formatted as a JSON.
			String input = "{\"customerPhoneNumber\":\"08023434343\", \"firstName\":\"amare\",\"lastName\":\"worku\", \"referenceNumber\":\"09012939283\", \"gender\": \"Male\", \"dateOfBirth\": \"27-01-1982\",\"identificationType\":\"DRIVERS_LICENCE\", \"identificationNumber\":\"343534534535\", \"address\":{\"addressTypeId\":\"09012939283\", \"cityId\": 1, \"countryId\": 1, \"countyId\": 1},}";
			logger.info("The input parameters..." + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.close();
			if (conn.getResponseCode() != 201 && conn.getResponseCode() != 200) {
				logger.info("Failed : HTTP error code" + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code"
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String output;
			logger.info("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				logger.info(output);
			}
			conn.disconnect();

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
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
}
