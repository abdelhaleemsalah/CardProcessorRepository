package com.cardprocessor.cardprocessorbatchproject.domain;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class MerchantFieldSetMapper implements FieldSetMapper<MerchantTransaction> {

    @Override
    public MerchantTransaction mapFieldSet(FieldSet fieldSet) throws BindException {

        return new MerchantTransaction(fieldSet.readString("transactionId"), fieldSet.readString("amount"),
                fieldSet.readString("currencyISO"), fieldSet.readDate("transactionDate", "yyyy-MM-dd HH:mm:ss"),
                fieldSet.readLong("customerId"));
    }
}