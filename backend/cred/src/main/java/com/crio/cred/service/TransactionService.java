package com.crio.cred.service;

import com.crio.cred.dto.AddTransactionDTO;
import com.crio.cred.dto.PaymentTransactionDTO;
import com.crio.cred.dto.TransactionDTO;
import com.crio.cred.exception.LimitExceededException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * The interface Transaction service.
 *
 * @author harikesh.pallantla
 */
public interface TransactionService {
    /**
     * Add the transaction.
     *
     * @param addTransactionDTO the add transaction dto
     * @return the transaction dto
     */
    TransactionDTO addTransaction(UUID cardId, AddTransactionDTO addTransactionDTO) throws LimitExceededException;

    /**
     * Add payment transaction.
     *
     * @param cardId                the card id
     * @param paymentTransactionDTO the payment transaction dto
     * @return the transaction dto
     */
    TransactionDTO addPayment(UUID cardId, PaymentTransactionDTO paymentTransactionDTO);

    /**
     * Add transaction statement list.
     *
     * @param cardId       the card id
     * @param month        the month
     * @param year         the year
     * @param transactions the transactions
     * @return the list
     */
    List<TransactionDTO> addTransactionStatement(UUID cardId, int month, int year,
                                                 List<AddTransactionDTO> transactions) throws LimitExceededException;

    /**
     * Gets transaction statement.
     *
     * @param cardId   the credit card id
     * @param month    the month
     * @param year     the year
     * @param pageable the pageable
     * @return the transaction statement
     */
    Page<TransactionDTO> getTransactionStatement(UUID cardId, int month, int year, Pageable pageable);
}