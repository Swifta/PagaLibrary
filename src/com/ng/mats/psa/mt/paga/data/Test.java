package com.ng.mats.psa.mt.paga.data;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Test {

	private Digest messageDigest;

	@SuppressWarnings("unchecked")
	public static void testOrdered() {
		@SuppressWarnings("rawtypes")
		Map obj = new LinkedHashMap();
		obj.put("a", "foo1");
		obj.put("b", new Integer(100));
		obj.put("c", new Double(1000.21));
		obj.put("d", new Boolean(true));
		obj.put("e", "foo2");
		obj.put("f", "foo3");
		obj.put("g", "foo4");
		obj.put("h", "foo5");
		obj.put("x", null);

		org.json.JSONObject json = new org.json.JSONObject(obj);
		// logger.info("Ordered Json : %s", json.toString())
		// System.out.println(json.toString());
		// System.out.println(obj);

		// String expectedJsonString =
		// """{"a":"foo1","b":100,"c":1000.21,"d":true,"e":"foo2","f":"foo3","g":"foo4","h":"foo5"}"""
		//
		// // string representation of json objects are same
		// assertEquals(expectedJsonString, json.toString())
		// // json objects are equal
		// JSONAssert.assertEquals(JSONSerializer.toJSON(expectedJsonString),
		// json)
	}

	public Test() {
		Security.addProvider(new BouncyCastleProvider());
		messageDigest = new SHA512Digest();

	}

	public static void main(String args[]) throws Exception {

		LinkedHashMap<String, Object> jsonOrderedMap = new LinkedHashMap<String, Object>();

		jsonOrderedMap.put("2", "blue");
		jsonOrderedMap.put("1", "red");
		jsonOrderedMap.put("3", "green");

		String reference = "6543211";
		String locale = "08177777722";
		String customerPhoneNumber = "";
		String amount = "500";
		String withdrawalCode = "90605";

		String input = "{\"referenceNumber\":" + "\"" + reference
				+ "\",\"locale\":" + "\"" + locale
				+ "\",\"customerPhoneNumber\":" + "\"" + customerPhoneNumber
				+ "\",\"amount\":" + amount + ", \"withdrawalCode\":" + "\""
				+ withdrawalCode + "\"" + "}";

		// JSONObject orderedJson = new JSONObject(jsonOrderedMap);

		// JSONArray jsonArray = new JSONArray(Arrays.asList(orderedJson));

		// System.out.println("Ordered JSON Fianl CSV :: " +
		// jsonArray.toString());
		// System.out.println("Ordered JSON Fianl CSV :: "
		// + orderedJson.toString());

		// System.out.println(input);
		// Test.testOrdered();

		// System.out
		// .println(Test
		// .makeSHA1Hash("37004900367308177777722100028033sfe5bf404-4085-4d8f-ac00-ff8cc67ea718"));

		// System.out
		// .println(hashText("37004900367308177777722100028033sfe5bf404-4085-4d8f-ac00-ff8cc67ea718"));

		Test test2 = new Test();
		test2.hex("37004900367308177777722100028033fe5bf404-4085-4d8f-ac00-ff8cc67ea718");

		String text = (Test
				.hashSHA512(
						"37004900367308177777722100028033fe5bf404-4085-4d8f-ac00-ff8cc67ea718",
						0, true)).toString();
		System.out.println(text);

		test2.stringbuild();

		// System.out
		// .println(test2
		// .digest("37004900367308177777722100028033sfe5bf404-4085-4d8f-ac00-ff8cc67ea718"));

	}

	// JSONArray ja = new JSONArray();
	// ja.put("true");
	// ja.put(1, 50L);
	// ja.put(2, 150.00);
	// System.out.println(ja);

	// ?@Test

	public byte[] digest(String message) {
		byte[] retValue = new byte[messageDigest.getDigestSize()];
		messageDigest.update(message.getBytes(), 0, message.length());
		messageDigest.doFinal(retValue, 0);
		return retValue;
	}

	public void hex(String me) throws NoSuchAlgorithmException,
			NoSuchProviderException {
		MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");
		byte[] digesta = mda.digest(me.getBytes());
		System.out.println(Hex.encodeHex(digesta));
	}

	public static String makeSHA1Hash(String input)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.reset();
		byte[] buffer = input.getBytes("UTF-8");
		md.update(buffer);
		byte[] digest = md.digest();

		String hexStr = "";
		for (int i = 0; i < digest.length; i++) {
			hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16)
					.substring(1);
		}
		return hexStr;
	}

	public static String convertByteToHex(byte data[]) {
		StringBuffer hexData = new StringBuffer();
		for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
			hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100,
					16).substring(1));

		return hexData.toString();
	}

	public static String hashText(String textToHash) throws Exception {
		final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
		sha512.update(textToHash.getBytes());

		return convertByteToHex(sha512.digest());
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

	public void stringbuild() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("973908158735");
		sBuilder.append("08177777722"); // customer (phone
		sBuilder.append("1000"); // amount
		sBuilder.append("95174");
		sBuilder.append("sfe5bf404-4085-4d8f-ac00-ff8cc67ea718");
		System.out.println(sBuilder);
	}

	// 80362644838208177777722100023376sfe5bf404-4085-4d8f-ac00-ff8cc67ea718
	// 97390815873508177777722100023376sfe5bf404-4085-4d8f-ac00-ff8cc67ea718

}
