package com.crio.cred.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Transaction type.
 *
 * @author harikesh.pallantla
 */
@Getter
@AllArgsConstructor
public enum TransactionType {
    /**
     * Debit transaction type.
     */
    DEBIT("D"),
    /**
     * Credit transaction type.
     */
    CREDIT("C");

    private final String code;
}
