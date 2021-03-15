package com.crio.cred.service;

import com.crio.cred.dto.CardDTO;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<CardDTO> addCard(CardDTO cardDTO);
    Boolean cardExists(Long cardNumber);
    List<CardDTO> getCards();
}
