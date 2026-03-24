package com.nexfin.backend.repository;

import com.nexfin.backend.model.entity.Transaction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllBySourceWalletUserIdOrTargetWalletUserIdOrderByCreatedAtDesc(UUID sourceUserId, UUID targetUserId);
}
