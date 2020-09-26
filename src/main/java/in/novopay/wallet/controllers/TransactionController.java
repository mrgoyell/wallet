package in.novopay.wallet.controllers;

import in.novopay.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/{transactionId}/status")
    public ResponseEntity<?> getTransactionStatus(@PathVariable String transactionId){
        return transactionService.getTransactionStatus(transactionId);
    }
}
