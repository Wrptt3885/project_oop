package com.nexfin.backend.model.entity;

import com.nexfin.backend.model.enums.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    protected Wallet() {
    }

    public Wallet(UUID id, User user, BigDecimal balance, Currency currency) {
        this.id = id;
        this.user = user;
        this.balance = balance;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }
}
