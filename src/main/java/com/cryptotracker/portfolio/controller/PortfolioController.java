package com.cryptotracker.portfolio.controller;

import com.cryptotracker.portfolio.DTO.AddHoldingDTO;
import com.cryptotracker.portfolio.DTO.DeleteHoldingDTO;
import com.cryptotracker.portfolio.DTO.UpdateHoldingDTO;
import com.cryptotracker.portfolio.entity.CryptoHolding;
import com.cryptotracker.portfolio.Exception.QuantityZeroBelow;
import com.cryptotracker.portfolio.repository.CryptoRepo;
import com.cryptotracker.portfolio.service.CryptoPriceService;
import com.cryptotracker.portfolio.service.CryptoService;
import com.cryptotracker.portfolio.service.SessionManager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    public CryptoService cryptoService;

    @Autowired
    public CryptoRepo cryptoRepo;

    @Autowired
    public CryptoPriceService cryptoPriceService;

    @Autowired
    public SessionManager sessionManager;

    private String getLoggedInEmail(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("User not logged in");
        }
        return email;
    }

    @PostMapping("/add")
    public CryptoHolding add(@RequestBody AddHoldingDTO dto, HttpSession session){
        String email = getLoggedInEmail(session);
        String symbol = dto.getSymbol().toUpperCase();
        double quantity = dto.getQuantity();
        double buyPrice = cryptoPriceService.getcurrentPriceBySymbol(symbol);
        String coinName = cryptoPriceService.getCoinName(symbol);

        if(quantity<=0){
            throw new QuantityZeroBelow("Quantity should be above Zero");
        }

        CryptoHolding holding = new CryptoHolding();
        holding.setEmail(email);
        holding.setCoinName(coinName);
        holding.setBuyPrice(buyPrice);
        holding.setSymbol(symbol);
        holding.setQuantityHeld(quantity);
        holding.setBuydate(LocalDate.now());
        return cryptoService.addHolding(holding);
    }


    @GetMapping("/my")
    public List<Map<String, Object>> getMyHolding(HttpSession session){
        String email = getLoggedInEmail(session);

        if (email == null) {
            throw new RuntimeException("User not logged in");
        }

        List<CryptoHolding> holdings = cryptoService.getHoldings(email);

        List<Map<String, Object>> response = new ArrayList<>();
        for(CryptoHolding h : holdings){
            double currentPrice = cryptoPriceService.getcurrentPriceBySymbol(h.getSymbol());
            double currentValue = h.getQuantityHeld()*currentPrice;
            double pnl = currentValue - (h.getQuantityHeld()*h.getBuyPrice());

            Map<String, Object> entry = new HashMap<>();
            entry.put("coin", h.getCoinName());
            entry.put("symbol", h.getSymbol());
            entry.put("quantityHeld", h.getQuantityHeld());
            entry.put("buyPrice", h.getBuyPrice());
            entry.put("currentPrice", currentPrice);
            entry.put("currentValue", currentValue);
            entry.put("pnl", pnl);
            response.add(entry);
        }
        return response;
    }

    @PutMapping("/update")
    public CryptoHolding update(@RequestBody UpdateHoldingDTO dto, HttpSession session){
        String email = getLoggedInEmail(session);
        String symbol = dto.getSymbol().toUpperCase();
        double quantity = dto.getQuantity();
        CryptoHolding holding = cryptoService.findByEmailAndSymbol(email, symbol);

        holding.setEmail(email);
        holding.setQuantityHeld(quantity);
        return cryptoService.addHolding(holding);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody DeleteHoldingDTO dto, HttpSession session){
        String email = getLoggedInEmail(session);

        cryptoService.deleteByEmailAndSymbol(email, dto.getSymbol().toUpperCase());
        return "Deleted Successfully";
    }
}
