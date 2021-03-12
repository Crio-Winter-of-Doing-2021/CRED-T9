package com.crio.cred.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardPaymentService {
    AMERICAN_EXPRESS("AMEX"),
    VISA("VISA"),
    MASTER_CARD("MTC"),
    MASTRO("MAS"),
    DISCOVER("DIS"),
    RUPAY("RUPAY");
    private final String code;
}
