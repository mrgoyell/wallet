package in.novopay.wallet.controllers;

import in.novopay.wallet.beans.Transaction;
import in.novopay.wallet.service.TransactionService;
import in.novopay.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<?> getUserTransactions(@PathVariable String userId){
        return walletService.getUserTransactions(userId);
    }

    @PutMapping("/{userId}/add")
    public ResponseEntity<?> addMoney(@PathVariable String userId, @RequestParam Float amount){
        return walletService.addMoney(userId,amount);
    }

    @PutMapping("/{userId}/transfer")
    public ResponseEntity<?> transferMoney(@PathVariable String userId, @RequestParam String receiverId, Float amount){
        return walletService.transferMoney(userId,receiverId,amount);
    }

}
