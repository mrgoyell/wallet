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

    @PutMapping("/{transactionId}/reversal")
    public ResponseEntity<?> transactionReversal(@PathVariable String transactionId){
        return transactionService.transactionReversal(transactionId);
    }

    @GetMapping("/{transactionId}/computeCharges")
    public ResponseEntity<?> computeCharges(@PathVariable String transactionId){
        return transactionService.computeCharges(transactionId);
    }
}
