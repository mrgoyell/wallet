package in.novopay.wallet.service;

import in.novopay.wallet.Exceptions.UserNotFoundException;
import in.novopay.wallet.beans.Transaction;
import in.novopay.wallet.beans.User;
import in.novopay.wallet.enums.TransactionStatus;
import in.novopay.wallet.enums.TransactionType;
import in.novopay.wallet.repository.TransactionRepository;
import in.novopay.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class WalletService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> addMoney(String userId, Float amount) {
        ResponseEntity responseEntity;
        Transaction transaction = null;
        User user = null;
        try {
            user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            //create and save initial state of transaction
            transaction = new Transaction(TransactionType.CREDIT, user);
            transaction = transactionRepository.save(transaction);
            float walletAmount = user.getWallet();
            walletAmount += amount;
            user.setWallet(walletAmount);
            transaction.setStatus(TransactionStatus.COMPLETE);
            responseEntity = ResponseEntity.ok(transaction);
        } catch (UserNotFoundException e) {
            responseEntity = ResponseEntity.notFound().build();
        } catch (Exception e) {
            if (transaction == null)
                transaction = new Transaction(TransactionType.CREDIT, user);
            transaction.setStatus(TransactionStatus.DECLINED);
            transactionRepository.save(transaction);
            responseEntity = ResponseEntity.badRequest().body(transaction);
        }
        return responseEntity;
    }

}
