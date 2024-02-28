package com.example.springboot.service;

import com.example.springboot.model.Transaction;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {
    private final String URI = "http://localhost:8081/api/transactions";
    public String getMessage() {
        RestTemplate restTemplate = new RestTemplate();
        String msg = restTemplate.getForObject(URI + "/test",String.class);
        return msg;
    }

    public ResponseEntity<Transaction> save(Transaction transaction) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction> transaction1 = restTemplate.postForEntity(URI,transaction, Transaction.class);
        return transaction1;
    }

    public List<Transaction> getAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(URI+"/test", HttpMethod.GET,entity,List.class).getBody();
    }
}
