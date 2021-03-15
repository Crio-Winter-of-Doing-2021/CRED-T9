package com.crio.cred.repository;

import com.crio.cred.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Find by card number.
     *
     * @param cardNumber the card number
     * @return the card details if found
     */
    Optional<Card> findByCardNumber(Long cardNumber);
}
