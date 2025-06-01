package com.cryptotracker.portfolio.service;

import com.cryptotracker.portfolio.DTO.LivePriceDTO;
import com.cryptotracker.portfolio.Exception.InvalidCryptoException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CryptoPriceService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final Map<String, String> coinMap = Map.of(
            "BTC", "bitcoin",
            "ETH", "ethereum",
            "SOL", "solana",
            "ADA", "cardano",
            "USDC", "usd-coin",
            "USDT", "tether",
            "DOGE", "dogecoin",
            "TRX", "tron",
            "SUI", "sui",
            "HYP", "hyperliquid"
    );

    public List<LivePriceDTO> fetchAllPrices(){
        String ids = String.join(",", coinMap.values());
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + ids + "&vs_currencies=inr";

        try{
            ResponseEntity<Map<String, Map<String, Double>>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<>(){}
            );

            List<LivePriceDTO> dtoList = new ArrayList<>();
            if(response.getStatusCode().is2xxSuccessful() && response.getBody()!=null){
                Map<String, Map<String, Double>> prices = response.getBody();
                for (Map.Entry<String, String> entry : coinMap.entrySet()) {
                    String symbol = entry.getKey();
                    String coinName = entry.getValue();
                    Map<String, Double> pricesInfo = prices.get(coinName);

                    if (pricesInfo != null && pricesInfo.containsKey("inr")) {
                        dtoList.add(new LivePriceDTO(
                                coinName,
                                symbol,
                                pricesInfo.get("inr")
                        ));
                    }
                }
            }
            return dtoList;
        }
        catch (Exception e){
            throw new RuntimeException("Error fetching prices: " + e.getMessage());
        }
    }


    public double getcurrentPriceBySymbol(String symbol){
        String id = coinMap.get(symbol.toUpperCase());
        if(id==null){
            throw new RuntimeException("Invalid coin");
        }
        if (symbol == null || !coinMap.containsKey(symbol)) {
            throw new InvalidCryptoException(" Invalid Crypto coin ");

        }
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + id + "&vs_currencies=inr";

        ResponseEntity<Map<String, Map<String, Double>>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<>(){});

        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody().get(id).get("inr");
        }
        else{
            throw new RuntimeException("Failed to fetch the price");
        }
    }

    public String getCoinName(String symbol){
        String id = coinMap.get(symbol.toUpperCase());
        if(symbol==null || !coinMap.containsKey(symbol)){
            throw new InvalidCryptoException("Invalid coin Symbol");
        }
        return id;
    }

    public double getPrice(String btc) {
        return getcurrentPriceBySymbol(btc);


    }
}
