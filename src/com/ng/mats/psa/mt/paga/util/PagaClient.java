package com.ng.mats.psa.mt.paga.util;

import java.util.logging.Logger;

public class PagaClient {
	private static final Logger logger = Logger
			.getLogger(PagaClient.class.getName());
	public static void main(String args[]){
		logger.info("First paga code");
		MoneyTransfer moneyTransfer = new PagaPropertyValues().getPropertyValues();
	}

}
