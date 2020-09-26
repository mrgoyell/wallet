package in.novopay.wallet.service;

import in.novopay.wallet.Exceptions.NotFoundException;
import in.novopay.wallet.beans.Transaction;
import in.novopay.wallet.beans.User;
import in.novopay.wallet.enums.TransactionStatus;
import in.novopay.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletService walletService;


    public ResponseEntity<?> getTransactionStatus(String transactionId) {
        try {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
            Map<String, Object> response = new HashMap<>();
            response.put("status", transaction.getStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<?> transactionReversal(String transactionId) {
        try {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new NotFoundException(transactionId));
            User user = transaction.getUser();
            walletService.updateWallet(user, -transaction.getAmount());
            transaction.setStatus(TransactionStatus.COMPLETED_AND_REVERSED);
            return ResponseEntity.ok(transaction);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    public ResponseEntity<?> computeCharges(String transactionId) {
        try {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new NotFoundException(transactionId));
            Float charges = Float.valueOf(String.format("%.2f", transaction.getAmount() * 0.002 + transaction.getAmount() * 0.0005));
            transaction.setCharges(charges);
            return ResponseEntity.ok(transaction);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
