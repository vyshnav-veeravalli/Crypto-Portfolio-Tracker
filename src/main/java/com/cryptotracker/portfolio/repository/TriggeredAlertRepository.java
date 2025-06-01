package com.cryptotracker.portfolio.repository;

import com.cryptotracker.portfolio.entity.Alert;
import com.cryptotracker.portfolio.entity.TriggeredAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TriggeredAlertRepository extends JpaRepository<TriggeredAlert, Integer> {
    List<TriggeredAlert> findAllByEmail(String email);
}
