package com.cryptotracker.portfolio.repository;

import com.cryptotracker.portfolio.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
    List<Alert> findBySymbolIgnoreCase(String symbol);

}
