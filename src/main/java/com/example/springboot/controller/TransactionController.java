package com.example.springboot.controller;

import com.example.springboot.model.Transaction;
import com.example.springboot.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transaction")
    public ResponseEntity getMsg() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/transaction")
    public ResponseEntity create(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(service.save(transaction)).getBody();
    }

}
