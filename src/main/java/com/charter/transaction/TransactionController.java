package com.charter.transaction;

import com.charter.TransactionRequest;
import com.charter.TransactionResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction/v1")
public class TransactionController {

    private final TransactionAdapter transactionAdapter;

    public TransactionController(TransactionAdapter transactionAdapter) {
        this.transactionAdapter = transactionAdapter;
    }

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        return transactionAdapter.createTransaction(request);
    }

    @PutMapping("/{uuid}")
    public TransactionResponse updateTransaction(@PathVariable("uuid") String uuid, @RequestBody TransactionRequest request) {
        return transactionAdapter.updateTransaction(uuid, request);
    }

}
