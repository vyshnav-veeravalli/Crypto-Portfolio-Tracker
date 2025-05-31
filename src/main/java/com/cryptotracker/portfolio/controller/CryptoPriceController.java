package com.cryptotracker.portfolio.controller;

import com.cryptotracker.portfolio.DTO.LivePriceDTO;
import com.cryptotracker.portfolio.service.CryptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prices")
@CrossOrigin
public class CryptoPriceController {

    @Autowired
    private CryptoPriceService cryptoPriceServ;

    @GetMapping
    public List<LivePriceDTO> getLivePrices() {
        return cryptoPriceServ.fetchAllPrices();
    }
}
