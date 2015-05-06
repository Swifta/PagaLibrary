package com.ng.mats.psa.mt.paga.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PagaPropertyValues {
	private static final Logger logger = Logger
			.getLogger(PagaPropertyValues.class.getName());

	public MoneyTransfer getPropertyValues() {
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		Properties prop = new Properties();
		String propFileName = "com/ng/mats/psa/mt/paga/util/config.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);
		try {
			if (inputStream != null) {

				prop.load(inputStream);

			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get the property value and print it out
		String parameterType = prop.getProperty("settings-type");
		logger.info("THE CONFIGURATION BEING USED AT THIS POINT IS ==========================="
				+ parameterType);

		moneyTransfer.setSourcePocketCode(prop.getProperty("Remarks_"
				+ parameterType));
		// for registered customers
		// moneyTransfer.setDestMdn(prop.getProperty("RegisteredCustomerNumber_"
		// + parameterType));
		// for unregistered customers
		// moneyTransfer.setDestMdn(prop.getProperty("UnregisteredCustomerNumber_"
		// + parameterType));
		// for wallet to bank transfer
		// moneyTransfer.setDestMdn(prop.getProperty("BankAccountNumber_" +
		// parameterType));

		System.out.println("The Channel ID as a test parameter====="
				+ prop.getProperty("ChannelID_" + parameterType));
		moneyTransfer.setChannelId(prop.getProperty("ChannelID_"
				+ parameterType));
		moneyTransfer.setSourceMdn(prop.getProperty("AgentNumber_"
				+ parameterType));
		moneyTransfer.setSourcePin(prop
				.getProperty("AgentPIN_" + parameterType));
		moneyTransfer.setConfirmed(prop.getProperty("Confirmed_"
				+ parameterType));
		moneyTransfer.setAgentCode(prop.getProperty("AgentCode_"
				+ parameterType));
		moneyTransfer.setDestPocketCode(prop
				.getProperty("DestinationPocketCodeWallet_" + parameterType));
		moneyTransfer.setAmount(prop.getProperty("Amount_" + parameterType));
		moneyTransfer.setCompanyId(prop.getProperty("CompanyId_"
				+ parameterType));
		moneyTransfer.setBillerCode(prop.getProperty("BillerCode_"
				+ parameterType));
		moneyTransfer.setBillNo(prop.getProperty("BillNo_" + parameterType));
		moneyTransfer.setPartnerCode(prop.getProperty("PartnerCode_"
				+ parameterType));
		moneyTransfer.setSecreteCode(prop.getProperty("SecreteCode_"
				+ parameterType));
		moneyTransfer.setTransferId(prop.getProperty("TransferId_"
				+ parameterType));
		moneyTransfer
				.setNaration(prop.getProperty("Naration_" + parameterType));
		moneyTransfer.setBenOpCode(prop.getProperty("BenOpCode_"
				+ parameterType));

		return moneyTransfer;
	}

}
