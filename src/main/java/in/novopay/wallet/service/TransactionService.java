package in.novopay.wallet.service;

import in.novopay.wallet.beans.Transaction;
import in.novopay.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;


    public ResponseEntity<?> getTransactionStatus(String transactionId) {
        try {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
            Map<String, Object> response = new HashMap<>();
            response.put("status", transaction.getStatus());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
