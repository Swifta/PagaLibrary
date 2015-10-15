package com.ng.mats.psa.mt.paga.data;

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

		moneyTransfer.setAppId(prop.getProperty("Appid_" + parameterType));
		moneyTransfer
				.setPassword(prop.getProperty("Password_" + parameterType));
		moneyTransfer
				.setUsername(prop.getProperty("Username_" + parameterType));
		moneyTransfer.setAppUrl(prop.getProperty("Url_" + parameterType));
		moneyTransfer.setAppUsername(prop.getProperty("Apiusername_"
				+ parameterType));
		moneyTransfer.setSenderPhone(prop.getProperty("Sendernumber_"
				+ parameterType));
		moneyTransfer.setRecieverPhone(prop.getProperty("Receivernumber_"
				+ parameterType));
		moneyTransfer.setAmount(Long.valueOf(prop.getProperty("Amount_"
				+ parameterType)));
		moneyTransfer.setReferenceNumberSize(prop
				.getProperty("Referencenumbersize_" + parameterType));

		moneyTransfer.setLocale(prop.getProperty("locale_" + parameterType));
		moneyTransfer.setSenderName(prop.getProperty("senderName_"
				+ parameterType));
		moneyTransfer.setMessage(prop.getProperty("message_" + parameterType));
		moneyTransfer.setIdentificationType(prop
				.getProperty("identificationType_" + parameterType));
		moneyTransfer.setIdentificationNumber(prop
				.getProperty("identificationNumber_" + parameterType));
		moneyTransfer.setTrustStoreLocation(prop
				.getProperty("TrustStoreLocation_" + parameterType));
		moneyTransfer.setTrustStorePassword(prop
				.getProperty("TrustStorePassword_" + parameterType));
		moneyTransfer
				.setBankCode(prop.getProperty("Bankcode_" + parameterType));
		moneyTransfer.setAccountNumber(prop.getProperty("Accountnumber_"
				+ parameterType));
		moneyTransfer.setAccountName(prop.getProperty("Accountname_"
				+ parameterType));
		moneyTransfer.setWithdrawalCode(prop.getProperty("Withdrawalcode_"
				+ parameterType));
		moneyTransfer.setAccountPhoneNumber(prop
				.getProperty("AccountPhoneNumber_" + parameterType));

		return moneyTransfer;
	}

}
