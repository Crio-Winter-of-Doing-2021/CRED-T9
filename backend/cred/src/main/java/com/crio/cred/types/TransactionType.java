package com.crio.cred.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    DEBIT("D"),
    CREDIT("C");

    private final String code;
}
