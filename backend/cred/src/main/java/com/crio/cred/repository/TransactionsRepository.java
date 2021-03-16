package com.crio.cred.repository;

import com.crio.cred.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Transactions repository.
 *
 * @author harikesh.pallantla
 */
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}
