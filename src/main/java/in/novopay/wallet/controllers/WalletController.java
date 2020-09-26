package in.novopay.wallet.controllers;

import in.novopay.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PutMapping("/{userId}/add")
    public ResponseEntity<?> addMoney(@PathVariable String userId, @RequestParam Float amount){
        return walletService.addMoney(userId,amount);
    }
}
