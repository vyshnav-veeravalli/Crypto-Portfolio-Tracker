package com.cryptotracker.portfolio.repository;

import com.cryptotracker.portfolio.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepo extends JpaRepository<CryptoPrice,String> {

}
