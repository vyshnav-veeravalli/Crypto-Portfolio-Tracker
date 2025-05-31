package com.cryptotracker.portfolio.repository;

import com.cryptotracker.portfolio.entity.CryptoHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoRepo extends JpaRepository<CryptoHolding, Integer> {
    List<CryptoHolding> findByEmail(String email);
    CryptoHolding findByEmailAndSymbol(String email, String symbol);
    void deleteByEmailAndSymbol(String email, String symbol);

}


