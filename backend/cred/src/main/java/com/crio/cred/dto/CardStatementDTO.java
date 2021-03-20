package com.crio.cred.dto;

import com.crio.cred.entity.Transactions;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Card statement dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
public class CardStatementDTO {
    private UUID cardStatementId;
    private BigDecimal minDue;
    private BigDecimal totalDue;
    private BigDecimal maxAmount;
    private OffsetDateTime dueDate;
    private UUID cardId;
    private List<Transactions> transactions;
}
