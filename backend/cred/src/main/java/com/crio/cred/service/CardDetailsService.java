package com.crio.cred.service;

import com.crio.cred.dto.AddCardDTO;
import com.crio.cred.dto.CardDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Card details service.
 *
 * @author harikesh.pallantla
 */
public interface CardDetailsService {
    /**
     * Add card.
     *
     * @param addCardDTO the add card dto
     * @return the card details if added successfully.
     */
    Optional<CardDTO> addCard(AddCardDTO addCardDTO);

    /**
     * Checks if the card exists with the given card number or not.
     *
     * @param cardNumber the card number
     * @return the boolean
     */
    Boolean isCardPresent(String cardNumber);

    /**
     * Gets all cards.
     *
     * @return the all cards
     */
    List<CardDTO> getAllCards();

    /**
     * Gets card by number.
     *
     * @param cardId the card id
     * @return the card by number
     */
    Optional<CardDTO> getCardByNumber(UUID cardId);
}