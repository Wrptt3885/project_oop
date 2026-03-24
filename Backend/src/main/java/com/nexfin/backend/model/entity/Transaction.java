package com.nexfin.backend.model.entity;

import com.nexfin.backend.model.enums.TransactionStatus;
import com.nexfin.backend.model.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_wallet_id", nullable = false)
    private Wallet sourceWallet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "target_wallet_id", nullable = false)
    private Wallet targetWallet;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(nullable = false)
    private String reference;

    private OffsetDateTime createdAt;

    protected Transaction() {
    }

    public Transaction(
            UUID id,
            Wallet sourceWallet,
            Wallet targetWallet,
            BigDecimal amount,
            TransactionType type,
            TransactionStatus status,
            OffsetDateTime createdAt,
            String reference) {
        this.id = id;
        this.sourceWallet = sourceWallet;
        this.targetWallet = targetWallet;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.reference = reference;
    }

    public UUID getId() {
        return id;
    }

    public Wallet getSourceWallet() {
        return sourceWallet;
    }

    public Wallet getTargetWallet() {
        return targetWallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
