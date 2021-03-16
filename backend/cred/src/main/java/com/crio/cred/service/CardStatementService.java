package com.crio.cred.service;

import com.crio.cred.dto.AddCardStatementDTO;
import com.crio.cred.dto.CardStatementDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Card statement service.
 *
 * @author harikesh.pallantla
 */
public interface CardStatementService {
    /**
     * Add card statement optional.
     *
     * @param addCardStatementDTO the add card statement dto
     * @return the optional
     */
    Optional<CardStatementDTO> addCardStatement(AddCardStatementDTO addCardStatementDTO);

    /**
     * Gets card statement.
     *
     * @return the card statement
     */
    List<CardStatementDTO> getCardStatement();

    /**
     * Gets outstanding statement.
     *
     * @param cardId the card id
     * @return the outstanding statement
     */
    CardStatementDTO getOutstandingStatement(UUID cardId);
}