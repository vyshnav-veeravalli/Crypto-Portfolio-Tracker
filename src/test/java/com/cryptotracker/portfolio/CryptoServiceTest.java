package com.cryptotracker.portfolio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CryptoServiceTest {

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private CryptoRepo cryptoRepo;

    @BeforeEach
    void setup() {
        cryptoRepo.deleteAll();
    }

    @Test
    @Order(1)
    void testAddHolding() {
        CryptoHolding holding = new CryptoHolding(0, "test@example.com", "bitcoin", "BTC", 2.5, 50000.0, LocalDate.now());
        CryptoHolding saved = cryptoService.addHolding(holding);
        assertNotNull(saved.getId());
        assertEquals("BTC", saved.getSymbol());
    }

    @Test
    @Order(2)
    void testGetHoldings() {
        cryptoService.addHolding(new CryptoHolding(0, "john@example.com","ethereum", "ETH", 1.0, 2000.0, LocalDate.now()));
        List<CryptoHolding> holdings = cryptoService.getHoldings("john@example.com");
        assertFalse(holdings.isEmpty());
    }

    @Test
    @Order(3)
    void testUpdateHolding() {
        CryptoHolding original = cryptoService.addHolding(new CryptoHolding(0,"user@example.com","dogecoin", "DOGE", 10.0, 5.0, LocalDate.now()));
        original.setQuantityHeld(20.0);
        original.setBuyPrice(6.0);
        CryptoHolding updated = cryptoService.updateHolding(original.getId(), original);
        assertEquals(20.0, updated.getQuantityHeld());
        assertEquals(6.0, updated.getBuyPrice());
    }

    @Test
    @Order(4)
    void testDeleteByEmailAndSymbol() {
        cryptoService.addHolding(new CryptoHolding(0,"del@example.com", "Cardano", "ADA", 4.0, 10.0, LocalDate.now()));
        cryptoService.deleteByEmailAndSymbol("del@example.com", "ADA");
        CryptoHolding holding = cryptoService.findByEmailAndSymbol("del@example.com", "ADA");
        assertNull(holding);
    }

    @Test
    @Order(5)
    void testFindByEmailAndSymbol() {
        cryptoService.addHolding(new CryptoHolding(0, "check@example.com", "solona", "SOL", 3.0, 1500.0, LocalDate.now()));
        CryptoHolding holding = cryptoService.findByEmailAndSymbol("check@example.com", "SOL");
        assertNotNull(holding);
        assertEquals("SOL", holding.getSymbol());
    }
}
