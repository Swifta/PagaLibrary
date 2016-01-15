package com.ng.mats.psa.mt.paga.data;

public class PagaResponse {
	public String orginatingpartnerfee;
	public String orginatingpartnerbalanceafter;
	public String destinationpartnerbalanceafter;
	public String financialtransactionid;
	public String responseCode;
	public String responseDescription;
	public String trxid;
	public boolean completeStatus;

	public String biller;

	public String getBiller() {
		return biller;
	}

	public void setBiller(String biller) {
		this.biller = biller;
	}

	public String getOrginatingpartnerfee() {
		return orginatingpartnerfee;
	}

	public void setOrginatingpartnerfee(String orginatingpartnerfee) {
		this.orginatingpartnerfee = orginatingpartnerfee;
	}

	public String getOrginatingpartnerbalanceafter() {
		return orginatingpartnerbalanceafter;
	}

	public void setOrginatingpartnerbalanceafter(
			String orginatingpartnerbalanceafter) {
		this.orginatingpartnerbalanceafter = orginatingpartnerbalanceafter;
	}

	public String getDestinationpartnerbalanceafter() {
		return destinationpartnerbalanceafter;
	}

	public void setDestinationpartnerbalanceafter(
			String destinationpartnerbalanceafter) {
		this.destinationpartnerbalanceafter = destinationpartnerbalanceafter;
	}

	public String getFinancialtransactionid() {
		return financialtransactionid;
	}

	public void setFinancialtransactionid(String financialtransactionid) {
		this.financialtransactionid = financialtransactionid;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public boolean isCompleteStatus() {
		return completeStatus;
	}

	public void setCompleteStatus(boolean completeStatus) {
		this.completeStatus = completeStatus;
	}

	public String getTrxid() {
		return trxid;
	}

	public void setTrxid(String trxid) {
		this.trxid = trxid;
	}

}
