package com.ng.mats.psa.mt.paga.util;

public class MoneyTransfer {

	private String channelId;
	private String service;
	private String sourceMdn;
	private String sourcePin;
	private String txnName;
	// subscriber activattion
	private String otp;
	private String activationNewPin;
	private String activationConfirmPin;
	// change pin
	private String newPin;
	private String confirmPin;
	// login
	private String authenticationString;
	private String appType;
	// balance
	private String sourcePocketCode;
	// transfer enquiry
	private String destMdn;
	private String amount;
	private String destPocketCode;
	private String destBankAccount;
	// transfer
	private String transferId;
	private String confirmed;
	private String parentTxnId;
	// bank enquiry
	private String destBankCode;
	// cashout enquiry
	private String agentCode;
	private String secreteCode;
	// airtime
	private String companyId;
	// bill payment
	private String billerCode;
	private String billNo;
	// third party
	private String partnerCode;
	// wallet to bank
	private String naration;
	private String benOpCode;

	public MoneyTransfer() {

	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSourceMdn() {
		return sourceMdn;
	}

	public void setSourceMdn(String sourceMdn) {
		this.sourceMdn = sourceMdn;
	}

	public String getSourcePin() {
		return sourcePin;
	}

	public void setSourcePin(String sourcePin) {
		this.sourcePin = sourcePin;
	}

	public String getTxnName() {
		return txnName;
	}

	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getActivationNewPin() {
		return activationNewPin;
	}

	public void setActivationNewPin(String activationNewPin) {
		this.activationNewPin = activationNewPin;
	}

	public String getActivationConfirmPin() {
		return activationConfirmPin;
	}

	public void setActivationConfirmPin(String activationConfirmPin) {
		this.activationConfirmPin = activationConfirmPin;
	}

	public String getNewPin() {
		return newPin;
	}

	public void setNewPin(String newPin) {
		this.newPin = newPin;
	}

	public String getConfirmPin() {
		return confirmPin;
	}

	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}

	public String getAuthenticationString() {
		return authenticationString;
	}

	public void setAuthenticationString(String authenticationString) {
		this.authenticationString = authenticationString;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getSourcePocketCode() {
		return sourcePocketCode;
	}

	public void setSourcePocketCode(String sourcePocketCode) {
		this.sourcePocketCode = sourcePocketCode;
	}

	public String getDestMdn() {
		return destMdn;
	}

	public void setDestMdn(String destMdn) {
		this.destMdn = destMdn;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDestPocketCode() {
		return destPocketCode;
	}

	public void setDestPocketCode(String destPocketCode) {
		this.destPocketCode = destPocketCode;
	}

	public String getDestBankAccount() {
		return destBankAccount;
	}

	public void setDestBankAccount(String destBankAccount) {
		this.destBankAccount = destBankAccount;
	}

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getParentTxnId() {
		return parentTxnId;
	}

	public void setParentTxnId(String parentTxnId) {
		this.parentTxnId = parentTxnId;
	}

	public String getDestBankCode() {
		return destBankCode;
	}

	public void setDestBankCode(String destBankCode) {
		this.destBankCode = destBankCode;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getSecreteCode() {
		return secreteCode;
	}

	public void setSecreteCode(String secreteCode) {
		this.secreteCode = secreteCode;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getNaration() {
		return naration;
	}

	public void setNaration(String naration) {
		this.naration = naration;
	}

	public String getBenOpCode() {
		return benOpCode;
	}

	public void setBenOpCode(String benOpCode) {
		this.benOpCode = benOpCode;
	}

}
