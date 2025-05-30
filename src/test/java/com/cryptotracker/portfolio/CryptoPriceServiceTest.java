package com.cryptotracker.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CryptoPriceServiceTest {

    private final CryptoPriceService cryptoPriceService = new CryptoPriceService();

    @Test
    void testFetchAllPrices() {
        List<LivePriceDTO> prices = cryptoPriceService.fetchAllPrices();
        assertNotNull(prices);
        assertFalse(prices.isEmpty());
        assertTrue(prices.stream().anyMatch(p -> p.getCoinName().equalsIgnoreCase("bitcoin")));
    }

    @Test
    void testGetCurrentPriceBySymbol_Valid() {
        double price = cryptoPriceService.getcurrentPriceBySymbol("ETH");
        assertTrue(price > 0);
    }

    @Test
    void testGetCurrentPriceBySymbol_Invalid() {
        Exception ex = assertThrows(RuntimeException.class, () -> {
            cryptoPriceService.getcurrentPriceBySymbol("FAKE");
        });
        assertEquals("Invalid coin", ex.getMessage());
    }

    @Test
    void testGetCoinName_Success() {
        String name = cryptoPriceService.getCoinName("SOL");
        assertEquals("solana", name);
    }

    @Test
    void testGetCoinName_Failure() {
        Exception ex = assertThrows(RuntimeException.class, () -> {
            cryptoPriceService.getCoinName("XYZ");
        });
        assertEquals("Invalid coin Symbol", ex.getMessage());
    }
}
