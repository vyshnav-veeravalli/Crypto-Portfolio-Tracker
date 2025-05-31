package com.cryptotracker.portfolio.service;

import java.util.*;
import com.cryptotracker.portfolio.entity.CryptoHolding;
import com.cryptotracker.portfolio.repository.CryptoRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoService {
    @Autowired
    CryptoRepo cryptoRepo;

    public CryptoHolding addHolding(CryptoHolding holding){
        return cryptoRepo.save(holding);
    }

    public List<CryptoHolding> getHoldings(String email){
        return cryptoRepo.findByEmail(email);
    }

    public CryptoHolding updateHolding(int id, CryptoHolding newData){
        CryptoHolding holding = cryptoRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found"));

        holding.setQuantityHeld(newData.getQuantityHeld());
        holding.setBuyPrice(newData.getBuyPrice());
        holding.setBuydate(newData.getBuydate());
        return cryptoRepo.save(holding);
    }

    @Transactional
    public void deleteByEmailAndSymbol(String email, String symbol) {
        cryptoRepo.deleteByEmailAndSymbol(email, symbol);
    }

    public CryptoHolding findByEmailAndSymbol(String email, String symbol) {
        return cryptoRepo.findByEmailAndSymbol(email, symbol.toUpperCase());
    }


}
