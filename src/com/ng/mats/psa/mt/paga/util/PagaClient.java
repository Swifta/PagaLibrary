package com.ng.mats.psa.mt.paga.util;

import java.util.logging.Logger;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.ng.mats.psa.mt.paga.data.MoneyTransfer;
import com.ng.mats.psa.mt.paga.data.PagaResponse;

public class PagaClient {
	private static final Logger logger = Logger.getLogger(PagaClient.class
			.getName());
	RestClient restClient = new RestClient();

	public PagaResponse performCashIn(MoneyTransfer moneyTransfer) {
		String endPointUrl = "moneyTransfer";
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
			inputObject.put("locale", moneyTransfer.getLocale());
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

	public PagaResponse performCashOut(MoneyTransfer moneyTransfer) {
		String endPointUrl = "dispenseCash";
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
			inputObject.put("locale", moneyTransfer.getLocale());
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

	public PagaResponse performWalletToBank(MoneyTransfer moneyTransfer) {
		String endPointUrl = "moneyTransferToBank";
		PagaResponse pagaResponse = new PagaResponse();
		String referenceNumber = restClient.generateReferencenNumber(Integer
				.valueOf(moneyTransfer.getReferenceNumberSize()));

		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append(referenceNumber);
		sBuilder.append(moneyTransfer.getBankCode());
		sBuilder.append(moneyTransfer.getAccountNumber());
		sBuilder.append(moneyTransfer.getAmount());
		sBuilder.append(moneyTransfer.getSenderPhone());
		sBuilder.append(moneyTransfer.getRecieverPhone());
		sBuilder.append(moneyTransfer.getAppId());

		JSONObject inputObject = new JSONObject();

		try {
			inputObject.put("referenceNumber", referenceNumber);
			inputObject.put("locale", moneyTransfer.getLocale());
			inputObject.put("bankCode", moneyTransfer.getBankCode());
			inputObject.put("accountNumber", moneyTransfer.getAccountNumber());
			inputObject.put("amount", moneyTransfer.getAmount());

			inputObject
					.put("senderPhoneNumber", moneyTransfer.getSenderPhone());
			inputObject.put("senderName", moneyTransfer.getSenderName());
			inputObject.put("receiverPhoneNumber",
					moneyTransfer.getRecieverPhone());
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
}
