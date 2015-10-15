package com.ng.mats.psa.mt.paga.util;

import java.util.logging.Logger;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.ng.mats.psa.mt.paga.data.MoneyTransfer;
import com.ng.mats.psa.mt.paga.data.PagaResponse;

public class PagaClient {
	private static final Logger logger = Logger.getLogger(PagaClient.class
			.getName());
	RestClient restClient = new RestClient();

	public PagaResponse performInterCustomerTransfer(MoneyTransfer moneyTransfer)
			throws JSONException {
		String endPointUrl = "secured/moneyTransfer";
		PagaResponse pagaResponse = new PagaResponse();
		String referenceNumber = restClient.generateReferencenNumber(Integer
				.valueOf(moneyTransfer.getReferenceNumberSize()));
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(referenceNumber);
		sBuilder.append(moneyTransfer.getSenderPhone()); // customer (phone
		sBuilder.append(moneyTransfer.getRecieverPhone()); // customer first
															// name
		sBuilder.append(moneyTransfer.getAmount()); // customer
		sBuilder.append(moneyTransfer.getAppId());
		JSONObject inputObject = new JSONObject();

		try {
			inputObject.put("referenceNumber", referenceNumber);
			inputObject.put("locale", "");
			inputObject
					.put("senderPhoneNumber", moneyTransfer.getSenderPhone());
			inputObject.put("senderName", moneyTransfer.getSenderName());
			inputObject.put("receiverPhoneNumber",
					moneyTransfer.getRecieverPhone());
			inputObject.put("amount", moneyTransfer.getAmount());
			inputObject.put("message", moneyTransfer.getMessage());
			inputObject.put("identificationType",
					moneyTransfer.getIdentificationType());
			inputObject.put("identificationNumber",
					moneyTransfer.getIdentificationNumber());
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String output = restClient.connectToPaga(endPointUrl,
				sBuilder.toString(), inputObject, moneyTransfer);
		try {
			JSONObject outputObject = new JSONObject(output);

			pagaResponse
					.setResponseCode(outputObject.getString("responseCode"));

			pagaResponse.setResponseDescription(outputObject
					.getString("message"));
			if (pagaResponse.getResponseCode().equals("0")) {
				pagaResponse.setCompleteStatus(true);
			} else {
				pagaResponse.setCompleteStatus(false);
			}
			pagaResponse.setDestinationpartnerbalanceafter("");
			pagaResponse.setFinancialtransactionid(referenceNumber);
			pagaResponse.setOrginatingpartnerbalanceafter(outputObject
					.getString("availableBalance"));
			pagaResponse.setOrginatingpartnerfee(outputObject
					.getString("agentCommission"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagaResponse;
	}

	public PagaResponse performCashIn(MoneyTransfer moneyTransfer)
			throws JSONException {
		String endPointUrl = "secured/depositCash";
		PagaResponse pagaResponse = new PagaResponse();
		String referenceNumber = restClient.generateReferencenNumber(Integer
				.valueOf(moneyTransfer.getReferenceNumberSize()));
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(referenceNumber);
		sBuilder.append(moneyTransfer.getRecieverPhone()); // customer (phone
		sBuilder.append(moneyTransfer.getAmount()); // amount
		sBuilder.append(moneyTransfer.getAppId());

		JSONObject inputObject = new JSONObject();

		try {
			inputObject.put("referenceNumber", referenceNumber);
			inputObject.put("locale", moneyTransfer.getLocale());
			inputObject.put("customerPhoneNumber",
					moneyTransfer.getRecieverPhone());
			inputObject.put("amount", moneyTransfer.getAmount());
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String output = restClient.connectToPaga(endPointUrl,
				sBuilder.toString(), inputObject, moneyTransfer);
		try {
			JSONObject outputObject = new JSONObject(output);

			pagaResponse
					.setResponseCode(outputObject.getString("responseCode"));

			pagaResponse.setResponseDescription(outputObject
					.getString("message"));
			if (pagaResponse.getResponseCode().equals("0")) {
				pagaResponse.setCompleteStatus(true);
			} else {
				pagaResponse.setCompleteStatus(false);
			}
			pagaResponse.setDestinationpartnerbalanceafter("");
			pagaResponse.setFinancialtransactionid(referenceNumber);
			pagaResponse.setOrginatingpartnerbalanceafter(outputObject
					.getString("availableBalance"));
			pagaResponse.setOrginatingpartnerfee(outputObject
					.getString("agentCommission"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagaResponse;

	}

	public PagaResponse performCashOut(MoneyTransfer moneyTransfer)
			throws JSONException {
		String endPointUrl = "secured/dispenseCash";
		PagaResponse pagaResponse = new PagaResponse();
		String referenceNumber = restClient.generateReferencenNumber(Integer
				.valueOf(moneyTransfer.getReferenceNumberSize()));
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(referenceNumber);
		sBuilder.append(moneyTransfer.getRecieverPhone()); // customer (phone
		sBuilder.append(moneyTransfer.getAmount()); // amount
		sBuilder.append(moneyTransfer.getWithdrawalCode());
		sBuilder.append(moneyTransfer.getAppId());

		JSONObject inputObject = new JSONObject();

		try {
			inputObject.put("referenceNumber", referenceNumber);
			// inputObject.put("locale", moneyTransfer.getLocale());
			inputObject.put("locale", "");
			inputObject.put("customerPhoneNumber",
					moneyTransfer.getRecieverPhone());
			inputObject.put("amount", moneyTransfer.getAmount());

			inputObject
					.put("withdrawalCode", moneyTransfer.getWithdrawalCode());
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String output = restClient.connectToPaga(endPointUrl,
				sBuilder.toString(), inputObject, moneyTransfer);
		System.out.println(output);
		System.out.println(sBuilder.toString());
		try {
			JSONObject outputObject = new JSONObject(output);

			pagaResponse
					.setResponseCode(outputObject.getString("responseCode"));

			pagaResponse.setResponseDescription(outputObject
					.getString("message"));
			if (pagaResponse.getResponseCode().equals("0")) {
				pagaResponse.setCompleteStatus(true);
			} else {
				pagaResponse.setCompleteStatus(false);
			}
			pagaResponse.setDestinationpartnerbalanceafter("");
			pagaResponse.setFinancialtransactionid(referenceNumber);
			pagaResponse.setOrginatingpartnerbalanceafter(outputObject
					.getString("availableBalance"));
			pagaResponse.setOrginatingpartnerfee(outputObject
					.getString("agentCommission"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagaResponse;
	}

	public PagaResponse performWalletToBank(MoneyTransfer moneyTransfer)
			throws JSONException {
		String endPointUrl = "secured/moneyTransferToBank";
		PagaResponse pagaResponse = new PagaResponse();
		String referenceNumber = restClient.generateReferencenNumber(Integer
				.valueOf(moneyTransfer.getReferenceNumberSize()));
		moneyTransfer.setBankCode(retrieveSettlementBank(moneyTransfer));

		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append(referenceNumber);
		sBuilder.append(moneyTransfer.getBankCode());
		sBuilder.append(moneyTransfer.getAccountNumber());
		sBuilder.append(moneyTransfer.getAmount());
		sBuilder.append(moneyTransfer.getSenderPhone());
		sBuilder.append(moneyTransfer.getAccountPhoneNumber());
		sBuilder.append(moneyTransfer.getAppId());

		JSONObject inputObject = new JSONObject();
		try {
			inputObject.put("referenceNumber", referenceNumber);
			inputObject.put("locale", moneyTransfer.getLocale());
			inputObject.put("bankCode", moneyTransfer.getBankCode());
			inputObject.put("accountNumber", moneyTransfer.getAccountNumber());
			inputObject.put("amount", moneyTransfer.getAmount());

			inputObject.put("senderPhone", moneyTransfer.getSenderPhone());
			inputObject.put("senderName", moneyTransfer.getSenderName());
			inputObject.put("recipientPhone",
					moneyTransfer.getAccountPhoneNumber());
			inputObject.put("remark", moneyTransfer.getMessage());

		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String output = restClient.connectToPaga(endPointUrl,
				sBuilder.toString(), inputObject, moneyTransfer);
		try {
			JSONObject outputObject = new JSONObject(output);

			pagaResponse
					.setResponseCode(outputObject.getString("responseCode"));

			pagaResponse.setResponseDescription(outputObject
					.getString("message"));
			if (pagaResponse.getResponseCode().equals("0")) {
				pagaResponse.setCompleteStatus(true);
			} else {
				pagaResponse.setCompleteStatus(false);
			}
			pagaResponse.setDestinationpartnerbalanceafter("");
			pagaResponse.setFinancialtransactionid(referenceNumber);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagaResponse;
	}

	public String retrieveSettlementBank(MoneyTransfer moneyTransfer)
			throws JSONException {
		String endPointUrl = "unsecured/getBanks";
		String bankCode = "xxxx";
		PagaResponse pagaResponse = new PagaResponse();
		String referenceNumber = restClient.generateReferencenNumber(Integer
				.valueOf(moneyTransfer.getReferenceNumberSize()));

		StringBuilder sBuilder = new StringBuilder();

		JSONObject inputObject = new JSONObject();

		try {
			inputObject.put("referenceNumber", referenceNumber);
			inputObject.put("locale", moneyTransfer.getLocale());

		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String output = restClient.connectToPaga(endPointUrl,
				sBuilder.toString(), inputObject, moneyTransfer);
		try {
			JSONObject outputObject = new JSONObject(output), bankContents;
			// INFO:
			// {"responseCode":0,"message":"Success","referenceNumber":null,"banks":[{"bankCode":"NIBSS","bankName":"Nigeria Inter-Bank Settlement System","publicId":"8CD0D574-9D7E-4421-B43D-733B9FF566DE","bankId":32},{"bankCode":"AB","bankName":"Access Bank","publicId":"40090E2F-7446-4217-9345-7BBAB7043C4C","bankId":1},{"bankCode":"FCMB","bankName":"First City Monument Bank","publicId":"757E1F82-C5C1-4883-B891-C888293F2F00","bankId":2},{"bankCode":"UBA","bankName":"United Bank for Africa","publicId":"C5A55AC4-86F8-4EAA-A979-56B47989BD0F","bankId":3},{"bankCode":"FBN","bankName":"First Bank","publicId":"8B9CCA8B-F092-4704-82FD-B82D2B9A1993","bankId":4},{"bankCode":"DB","bankName":"Diamond Bank","publicId":"D0EC1001-3D99-4EF8-A203-1EB129DC00A1","bankId":5},{"bankCode":"GTB","bankName":"GT Bank","publicId":"3E94C4BC-6F9A-442F-8F1A-8214478D5D86","bankId":6},{"bankCode":"ZB","bankName":"Zenith Bank","publicId":"6B8787D1-1EA9-49EC-9673-BDCA6354BF9D","bankId":7},{"bankCode":"ECO","bankName":"Ecobank Nigeria","publicId":"4E78C667-CEB5-4813-A941-1E7916934FAD","bankId":9},{"bankCode":"FIDB","bankName":"Fidelity Bank","publicId":"D4A98615-56E6-4724-92BC-3FA0A0D2B85C","bankId":11},{"bankCode":"STRL","bankName":"Sterling Bank","publicId":"F175FCD4-16FE-4A89-A86D-82C0783ABFDD","bankId":14},{"bankCode":"SKYE","bankName":"Skye Bank","publicId":"7605D2D1-A4BD-42AC-A3D4-7B3EDAD3A42C","bankId":18},{"bankCode":"STNB","bankName":"Stanbic IBTC Bank","publicId":"1648430E-DB0A-498C-AD4B-0F5560B1106E","bankId":19},{"bankCode":"SCB","bankName":"Standard Chartered Bank of Nigeria","publicId":"DCAE53D8-D943-4DD6-82F8-160F187EEA76","bankId":20},{"bankCode":"UBN","bankName":"Union Bank of Nigeria","publicId":"A15762D4-29B4-4635-BEEC-A89D95DEC8A7","bankId":21},{"bankCode":"WMB","bankName":"Wema Bank","publicId":"49B81CAC-E075-4D65-8AE0-8F921402D88C","bankId":22},{"bankCode":"UTY","bankName":"Unity Bank","publicId":"0DD51F22-0AFC-43B2-B871-45E7D6444C71","bankId":23},{"bankCode":"EARTH","bankName":"Eartholeum","publicId":"23DE207B-A86D-4955-8B7A-6CD944FA33C9","bankId":33},{"bankCode":"CHAMS","bankName":"Chams","publicId":"76472141-848D-4147-ADEB-108136EFF1D2","bankId":34},{"bankCode":"PAYCOM","bankName":"Paycom","publicId":"30AE3B3E-F20D-431F-959B-DDC9CEEE79A5","bankId":35},{"bankCode":"ETZT","bankName":"E-Tranzact","publicId":"1BFC3E39-8462-44F3-A52A-05A39000AD4C","bankId":36},{"bankCode":"FTSMFI","bankName":"Fortis Microfinance","publicId":"4B76FC5C-48F7-4745-811B-DAB86872DDA7","bankId":37},{"bankCode":"CPTI","bankName":"Corporetti","publicId":"2873C6CD-01A3-48FF-9EA7-16ED437CDD77","bankId":38},{"bankCode":"PKWY","bankName":"Parkway","publicId":"7DDF773E-22EA-4628-B8CB-E91EF7F6649B","bankId":39},{"bankCode":"MNTZ","bankName":"Monetize","publicId":"87A03628-2BE6-46B9-AEAE-34EAEB263752","bankId":40},{"bankCode":"MKUDI","bankName":"M-Kudi","publicId":"2345A4B7-160A-44E3-91DD-A80F076471E1","bankId":41},{"bankCode":"FET","bankName":"Fet","publicId":"DFFF5A26-0E96-4A13-B56F-6A3D84B7F6B2","bankId":42},{"bankCode":"STBCMM","bankName":"Stanbic Mobile Money","publicId":"3730F928-7CD6-4E67-907B-B95CCABD319F","bankId":43},{"bankCode":"ECOMM","bankName":"Ecobank Nigeria Mobile Money","publicId":"035C6ED5-A387-40A2-8002-D1A070462F41","bankId":44},{"bankCode":"FBNMM","bankName":"FBN M-money","publicId":"09E8AC7E-703B-42A1-BB64-F9514FCB4919","bankId":45},{"bankCode":"GTBMM","bankName":"GT Mobile Money","publicId":"0EA83FCC-0233-4CB8-972A-04F060F804C2","bankId":46},{"bankCode":"KBL","bankName":"Keystone Bank Limited","publicId":"A1882172-7C26-45A2-B329-5AB38EAE61C6","bankId":24},{"bankCode":"CB","bankName":"Citibank","publicId":"6390A475-2260-4F25-AE09-E915346D79CC","bankId":25},{"bankCode":"EB","bankName":"Enterprise Bank","publicId":"5784F5BE-7C9F-4E23-AC30-FB3B5D452C4B","bankId":26},{"bankCode":"MB","bankName":"Mainstreet Bank","publicId":"FBC15F39-CB11-43CA-8CAC-0EF011CF5C3C","bankId":27},{"bankCode":"FIB","bankName":"Savannah Bank","publicId":"9113FAB7-F59C-4559-AA81-06056C681889","bankId":28},{"bankCode":"CELLNT","bankName":"Cellulant","publicId":"988CB604-126E-438F-86FD-E9C503D4A62F","bankId":29},{"bankCode":"UMO","bankName":"U-Mo","publicId":"E154AAEB-75E1-45B1-B4EF-696DB96BF019","bankId":30},{"bankCode":"ASOSL","bankName":"Aso Savings and Loans","publicId":"2FAA5549-F43B-498B-9CC2-677E45CFDB84","bankId":31},{"bankCode":"HTGB","bankName":"Heritage Bank","publicId":"E2C7BA4F-AA49-4B65-92A1-CCBA2B2D5FDE","bankId":47},{"bankCode":"VTN","bankName":"VTNetwork","publicId":"41ad5a3b-ec32-440c-b878-ca63f01e80da","bankId":48},{"bankCode":"ACMO","bankName":"Access Mobile","publicId":"f79330c9-66c6-44c7-9b80-ddc8427e7bbf","bankId":49},{"bankCode":"FBNM","bankName":"FBN Mobile","publicId":"a7a37b06-9b47-4cd4-9817-45b595bfd757","bankId":50},{"bankCode":"HDNM","bankName":"HEDONMARK Mobile","publicId":"56d6c6cd-0425-4536-ad4e-752f584d6c19","bankId":51}]}
			JSONArray jsonArray = outputObject.getJSONArray("banks");

			for (int i = 0; i < jsonArray.length(); i++) {
				bankContents = new JSONObject();
				bankContents = jsonArray.getJSONObject(i);
				if (bankContents != null) {
					if (bankContents
							.getString("bankName")
							.toLowerCase()
							.contains(moneyTransfer.getBankCode().toLowerCase())) {
						bankCode = bankContents.getString("bankCode");
						logger.info("MATCH FOUND --------------------------------------------------->>"
								+ bankCode);
						break;
					} else {
						logger.info("Bank Not matched......"
								+ bankContents.getString("bankCode"));
					}
				} else {
					logger.info("No object is located on this index-------- "
							+ i);
				}

			}

			/*
			 * pagaResponse
			 * .setResponseCode(outputObject.getString("responseCode"));
			 * 
			 * pagaResponse.setResponseDescription(outputObject
			 * .getString("message")); if
			 * (pagaResponse.getResponseCode().equals("0")) {
			 * pagaResponse.setCompleteStatus(true); } else {
			 * pagaResponse.setCompleteStatus(false); }
			 * pagaResponse.setDestinationpartnerbalanceafter("");
			 * pagaResponse.setFinancialtransactionid(referenceNumber);
			 * pagaResponse.setOrginatingpartnerbalanceafter(outputObject
			 * .getString("availableBalance"));
			 * pagaResponse.setOrginatingpartnerfee(outputObject
			 * .getString("agentCommission"));
			 */
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bankCode;
	}
}
