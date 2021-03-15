package com.crio.cred.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card extends BaseEntity{

    @Id
    @GeneratedValue
    private Long cardId;

    @Column(nullable = false)
    private Long cardNumber;

    @Column(nullable = false)
    private String cardPaymentService;

    @Column(nullable = false)
    private String expiry;

    @Column(nullable = false)
    private String nameOnCard;
}
