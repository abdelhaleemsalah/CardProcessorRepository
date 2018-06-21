package com.cardprocessor.cardprocessorbatchproject.domain;

import java.util.Date;
    public class MerchantTransaction {
        private final String transactionId;
        private final String amount;
        private final String currencyISO;
        private final Date transactionDate;
        private final long customerId;


        public MerchantTransaction(String transactionId, String amount, String currencyISO, Date transactionDate, long customerId) {

            this.transactionId = transactionId;
            this.amount = amount;
            this.currencyISO = currencyISO;
            this.transactionDate = transactionDate;
            this.customerId = customerId;
        }

        @Override
        public String toString() {
            return "MerchantTransaction{" +
                    "transactionId=" + transactionId +
                    ", amount='" + amount + '\'' +
                    ", currencyISO='" + currencyISO + '\'' +
                    ", transactionDate='" + transactionDate + '\'' +
                    ", customerId='" + customerId + '\'' +
                    '}';
        }
    }