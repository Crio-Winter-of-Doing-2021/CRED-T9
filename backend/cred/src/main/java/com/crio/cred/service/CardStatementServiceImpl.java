package com.crio.cred.service;

import com.crio.cred.dto.AddCardStatementDTO;
import com.crio.cred.dto.CardStatementDTO;
import com.crio.cred.entity.CardDetails;
import com.crio.cred.entity.CardStatement;
import com.crio.cred.repository.CardDetailsRepository;
import com.crio.cred.repository.CardStatementRepository;
import com.crio.cred.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Card statement service.
 *
 * @author harikesh.pallantla
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CardStatementServiceImpl implements CardStatementService {

    private final CardStatementRepository cardStatementRepository;
    private final ModelMapper modelMapper;
    private final CardDetailsRepository cardDetailsRepository;

    @Override
    public Optional<CardStatementDTO> addCardStatement(AddCardStatementDTO addCardStatementDTO) {
        logger.trace("Entered addCardStatement");
        CardStatement cardStatement = modelMapper.map(addCardStatementDTO, CardStatement.class);
        CardStatement savedCardStatement = cardStatementRepository.save(cardStatement);
        CardStatementDTO cardStatementDTO = modelMapper.map(savedCardStatement, CardStatementDTO.class);
        logger.trace("Exited addCardStatement");
        return Optional.of(cardStatementDTO);
    }

    @Override
    public List<CardStatementDTO> getCardStatement() {
        return Utils.mapList(modelMapper, cardStatementRepository.findAll(), CardStatementDTO.class);
    }

    @Override
    public CardStatementDTO getOutstandingStatement(UUID cardId) {
        logger.trace("Entered getOutstandingStatement");
        Optional<CardDetails> cardDetails = cardDetailsRepository.findById(cardId);
        cardDetails.orElseThrow(() -> new IllegalArgumentException("Card Id not found."));
        CardStatement outstandingStatement =
                cardStatementRepository.findCardStatementBySettleDateIsNullAndCardId(cardDetails.get());
        logger.trace("Exited getOutstandingStatement");
        return modelMapper.map(outstandingStatement, CardStatementDTO.class);
    }
}
