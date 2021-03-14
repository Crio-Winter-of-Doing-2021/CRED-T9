package com.crio.cred.service;

import com.crio.cred.dto.CardDTO;
import com.crio.cred.entity.Card;
import com.crio.cred.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<CardDTO> addCard(CardDTO cardDTO) {
        logger.trace("Entered addCard");
        Card card = modelMapper.map(cardDTO, Card.class);
        Card savedCard = cardRepository.save(card);
        CardDTO savedCardDTO = modelMapper.map(savedCard, CardDTO.class);
        logger.trace("Exited addCard");
        return Optional.of(savedCardDTO);
    }

    @Override
    public Boolean cardExists(Long cardNumber) {
        return cardRepository.findByCardNumber(cardNumber).isPresent();
    }
}
