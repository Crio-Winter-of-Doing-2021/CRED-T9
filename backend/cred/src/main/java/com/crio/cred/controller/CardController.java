package com.crio.cred.controller;

import com.crio.cred.dto.CardDTO;
import com.crio.cred.dto.SignUpDTO;
import com.crio.cred.dto.UserDTO;
import com.crio.cred.model.ErrorDetails;
import com.crio.cred.service.CardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@Validated
@Slf4j
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardController {
    private final CardService cardService;

    /**
     * Add a new card.
     *
     * @param cardDTO the details of the card
     * @return the response entity
     */
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Card added successfully.", response = CardDTO.class),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Card already added", response = ErrorDetails.class)
    })
    @ApiOperation(value = "Adds a new card with the given details.", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCard(@Valid @RequestBody CardDTO cardDTO) {
        logger.trace("Entered add card");

        if (cardService.cardExists(cardDTO.getCardNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDetails(HttpStatus.CONFLICT, "Card already exists."));
        Optional<CardDTO> cardResponseDTO = cardService.addCard(cardDTO);
        if (cardResponseDTO.isPresent()) {
            CardDTO card = cardResponseDTO.get();
            logger.trace("Exited add card");
            return ResponseEntity.created(URI.create("/add-card")).body(card);
        }
        logger.trace("Exited add card");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
