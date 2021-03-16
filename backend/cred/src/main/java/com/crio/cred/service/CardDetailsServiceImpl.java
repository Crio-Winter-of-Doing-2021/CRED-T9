package com.crio.cred.service;

import com.crio.cred.dto.AddCardDTO;
import com.crio.cred.dto.AddCardStatementDTO;
import com.crio.cred.dto.CardDTO;
import com.crio.cred.dto.CardStatementDTO;
import com.crio.cred.dto.UserDTO;
import com.crio.cred.entity.CardDetails;
import com.crio.cred.entity.User;
import com.crio.cred.repository.CardDetailsRepository;
import com.crio.cred.security.JwtUtil;
import com.crio.cred.util.Utils;
import io.github.benas.randombeans.randomizers.range.BigDecimalRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.LocalDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.time.ZoneOffsetRandomizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Card details service.
 *
 * @author harikesh.pallantla
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CardDetailsServiceImpl implements CardDetailsService {
    private final CardDetailsRepository cardDetailsRepository;
    private final CardStatementService cardStatementService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public Optional<CardDTO> addCard(AddCardDTO addCardDTO) {
        logger.trace("Entered addCard");
        CardDetails cardDetails = modelMapper.map(addCardDTO, CardDetails.class);
        String emailId = jwtUtil.getEmailFromSecurity();
        Optional<UserDTO> userDTO = userService.getUserByEmailId(emailId);
        if (userDTO.isPresent()) {
            User user = modelMapper.map(userDTO.get(), User.class);
            cardDetails.setUser(user);
        }
        CardDetails savedCard = cardDetailsRepository.save(cardDetails);

        logger.trace("Adding initial card statement.");
        AddCardStatementDTO addCardStatementDTO = new AddCardStatementDTO();
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer =
                BigDecimalRangeRandomizer.aNewBigDecimalRangeRandomizer(Double.valueOf(1_00_000.0),
                        Double.valueOf(10_00_000), Integer.valueOf(2));
        addCardStatementDTO.setMaxAmount(bigDecimalRangeRandomizer.getRandomValue());
        bigDecimalRangeRandomizer =
                BigDecimalRangeRandomizer.aNewBigDecimalRangeRandomizer(Double.valueOf(1000),
                        Double.valueOf(1_00_000.0), Integer.valueOf(2));
        addCardStatementDTO.setMinDue(bigDecimalRangeRandomizer.getRandomValue());
        LocalDateTimeRangeRandomizer localDateTimeRangeRandomizer = LocalDateTimeRangeRandomizer
                .aNewLocalDateTimeRangeRandomizer(LocalDateTime.now(), LocalDateTime.now().plusMonths(10));
        ZoneOffsetRandomizer zoneOffsetRandomizer = ZoneOffsetRandomizer.aNewZoneOffsetRandomizer();
        OffsetDateTime randomFuture = OffsetDateTime.of(localDateTimeRangeRandomizer.getRandomValue(),
                zoneOffsetRandomizer.getRandomValue());
        addCardStatementDTO.setDueDate(randomFuture);
        addCardStatementDTO.setCardId(savedCard.getCardId());
        cardStatementService.addCardStatement(addCardStatementDTO);

        CardDTO savedCardDTO = modelMapper.map(savedCard, CardDTO.class);
        savedCardDTO.setDueDate(addCardStatementDTO.getDueDate());
        savedCardDTO.setTotalDue(addCardStatementDTO.getTotalDue());
        savedCardDTO.setMinDue(addCardStatementDTO.getMinDue());
        logger.trace("Exited addCard");
        return Optional.of(savedCardDTO);
    }

    @Override
    public Boolean isCardPresent(String cardNumber) {
        return cardDetailsRepository.findByCardNumber(cardNumber).isPresent();
    }

    @Override
    public List<CardDTO> getAllCards() {
        logger.trace("Entered getAllCards");
        List<CardDetails> cardDetailsList = cardDetailsRepository.findAll();
        List<CardDTO> cardDetails = Utils.mapList(modelMapper, cardDetailsList, CardDTO.class);
        for (CardDTO card : cardDetails) {
            CardStatementDTO outstandingStatement = cardStatementService.getOutstandingStatement(card.getCardId());
            card.setMinDue(outstandingStatement.getMinDue());
            card.setTotalDue(outstandingStatement.getTotalDue());
            card.setDueDate(outstandingStatement.getDueDate());
        }
        logger.trace("Exited getAllCards");
        return cardDetails;
    }

    @Override
    public Optional<CardDTO> getCardByNumber(UUID cardId) {
        logger.trace("Entered getCardByNumber");
        Optional<CardDetails> cardDetailsOptional = cardDetailsRepository.findById(cardId);
        if (cardDetailsOptional.isPresent()) {
            CardStatementDTO outstandingStatement = cardStatementService.getOutstandingStatement(cardId);
            CardDTO card = modelMapper.map(cardDetailsOptional.get(), CardDTO.class);
            card.setMinDue(outstandingStatement.getMinDue());
            card.setTotalDue(outstandingStatement.getTotalDue());
            card.setDueDate(outstandingStatement.getDueDate());
            logger.trace("Exited getCardByNumber");
            return Optional.of(card);
        }
        logger.trace("Exited getCardByNumber");
        return Optional.empty();
    }
}