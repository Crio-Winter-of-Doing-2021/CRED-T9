package com.crio.cred.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CardDTO {

    @NotNull(message = "Card number is required.")
    private Long cardNumber;

    @NotNull(message = "cvv number is required.")
    private Integer cvv;

    @NotBlank(message = "Card payment service is required.")
    private String cardPaymentService;

    @NotBlank(message = "Expiry is required.")
    private String expiry;

    @NotBlank(message = "Name on card is required.")
    private String nameOnCard;
}
