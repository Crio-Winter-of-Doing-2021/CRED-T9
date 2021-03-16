package com.crio.cred.repository;

import com.crio.cred.entity.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Card details repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails, UUID> {


    /**
     * Find by card number.
     *
     * @param cardNumber the card number
     * @return the card details if found.
     */
    Optional<CardDetails> findByCardNumber(String cardNumber);
}
