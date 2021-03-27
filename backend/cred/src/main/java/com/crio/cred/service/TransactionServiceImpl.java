package com.crio.cred.service;

import com.crio.cred.dto.*;
import com.crio.cred.entity.CardStatement;
import com.crio.cred.entity.Category;
import com.crio.cred.entity.Transactions;
import com.crio.cred.entity.Vendor;
import com.crio.cred.exception.LimitExceededException;
import com.crio.cred.repository.TransactionsRepository;
import com.crio.cred.types.TransactionType;
import com.crio.cred.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

    private final TransactionsRepository transactionsRepository;
    private final ModelMapper modelMapper;
    private final CardStatementService cardStatementService;
    private final VendorService vendorService;
    private final CategoryService categoryService;

    /**
     * Add the transaction.
     *
     * @param addTransactionDTO the add transaction dto
     * @return the transaction dto
     */
    @Override
    @Transactional
    public TransactionDTO addTransaction(UUID cardId, AddTransactionDTO addTransactionDTO) throws LimitExceededException {
        logger.trace("Entered addTransaction");
        CardStatementDTO statementDTO =
                cardStatementService.getOutstandingStatement(cardId);
        CardStatement cardStatement = modelMapper.map(statementDTO, CardStatement.class);
        BigDecimal totalDue = statementDTO.getTotalDue();
        totalDue = totalDue.add(addTransactionDTO.getAmount());
        statementDTO.setTotalDue(totalDue);
        if (totalDue.compareTo(statementDTO.getMaxAmount()) > 0) {
            throw new LimitExceededException("Maximum limit of the credit card is exceeded.");
        }

        cardStatementService.updateCardStatement(statementDTO);

        Transactions transaction = modelMapper.map(addTransactionDTO, Transactions.class);
        transaction.setCardStatementId(cardStatement);

        try {
            Currency currency = Currency.getInstance(addTransactionDTO.getCurrency());
            transaction.setCurrency(currency);
        } catch (IllegalArgumentException | NullPointerException exception) {
            logger.error("Invalid currency code.");
            throw new IllegalArgumentException("Invalid Currency Code.");
        }

        logger.trace("Getting/ adding vendor");
        Vendor vendor = vendorService.getOrAddVendor(addTransactionDTO.getVendor().toLowerCase());
        transaction.setVendor(vendor);

        logger.trace("Getting/ adding category");
        Category category = categoryService.getOrAddCategory(addTransactionDTO.getCategory().toLowerCase());
        transaction.setCategory(category);

        Transactions savedTransaction = transactionsRepository.save(transaction);
        logger.trace("Exited addTransaction");
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }

    /**
     * Add payment transaction.
     *
     * @param cardId                the card id
     * @param paymentTransactionDTO the payment transaction dto
     * @return the transaction dto
     */
    @Override
    @Transactional
    public TransactionDTO addPayment(UUID cardId, PaymentTransactionDTO paymentTransactionDTO) {
        logger.trace("Entered addPayment");
        CardStatementDTO statementDTO =
                cardStatementService.getOutstandingStatement(cardId);
        CardStatement cardStatement = modelMapper.map(statementDTO, CardStatement.class);
        BigDecimal totalDue = statementDTO.getTotalDue();
        totalDue = totalDue.subtract(paymentTransactionDTO.getAmount());
        statementDTO.setTotalDue(totalDue);
        statementDTO.setSettleDate(OffsetDateTime.now());
        cardStatementService.updateCardStatement(statementDTO);

        Transactions transaction = modelMapper.map(paymentTransactionDTO, Transactions.class);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setTransactionDate(OffsetDateTime.now());
        transaction.setCardStatementId(cardStatement);

        try {
            Currency currency = Currency.getInstance(paymentTransactionDTO.getCurrency());
            transaction.setCurrency(currency);
        } catch (IllegalArgumentException | NullPointerException exception) {
            logger.error("Invalid currency code.");
            throw new IllegalArgumentException("Invalid Currency Code.");
        }

        Transactions savedTransaction = transactionsRepository.save(transaction);
        TransactionDTO transactionDTO = modelMapper.map(savedTransaction, TransactionDTO.class);

        AddCardStatementDTO addCardStatementDTO = modelMapper.map(statementDTO, AddCardStatementDTO.class);
        OffsetDateTime newDueDate = addCardStatementDTO.getDueDate().plusMonths(1);
        addCardStatementDTO.setDueDate(newDueDate);
        cardStatementService.addCardStatement(addCardStatementDTO);

        logger.trace("Exited addPayment");
        return transactionDTO;
    }

    /**
     * Add transaction statement list.
     *
     * @param cardId       the card id
     * @param month        the month
     * @param year         the year
     * @param transactions the transactions
     * @return the list
     */
    @Override
    public List<TransactionDTO> addTransactionStatement(UUID cardId, int month, int year,
                                                        List<AddTransactionDTO> transactions) throws LimitExceededException {
        logger.trace("Entered addTransactionStatement");
        logger.debug("Transactions count: " + transactions.size());
        List<TransactionDTO> addedTransactions = new ArrayList<>();
        for (AddTransactionDTO transactionDTO : transactions) {
            TransactionDTO addedTransaction = addTransaction(cardId, transactionDTO);
            addedTransactions.add(addedTransaction);
        }
        logger.trace("Exited addTransactionStatement");
        return addedTransactions;
    }

    /**
     * Gets transaction statement.
     *
     * @param cardId   the credit card id
     * @param month    the month
     * @param year     the year
     * @param pageable the pageable
     * @return the transaction statement
     */
    @Override
    public Page<TransactionDTO> getTransactionStatement(UUID cardId, int month, int year,
                                                        Pageable pageable) {
        CardStatementDTO statementDTO =
                cardStatementService.getOutstandingStatement(cardId);
        CardStatement cardStatement = modelMapper.map(statementDTO, CardStatement.class);
        LocalDate localDate = LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.firstDayOfMonth());
        OffsetDateTime start = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        OffsetDateTime end = OffsetDateTime.of(localDate, LocalTime.MIN, ZoneOffset.UTC);
        Page<Transactions> all = transactionsRepository.findAllByCardStatementIdAndTransactionDateBetween(cardStatement, start, end, pageable);
        return Utils.mapEntityPageIntoDtoPage(modelMapper, all, TransactionDTO.class);
    }
}
