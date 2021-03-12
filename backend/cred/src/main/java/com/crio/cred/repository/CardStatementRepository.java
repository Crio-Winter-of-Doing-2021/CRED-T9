package com.crio.cred.repository;

import com.crio.cred.entity.CardStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardStatementRepository extends JpaRepository<CardStatement, UUID> {
}
