package in.novopay.wallet.service;
import in.novopay.wallet.Exceptions.NotFoundException;
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
import java.util.List;

@Service
public class WalletService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> addMoney(String userId, Float amount) {
        ResponseEntity responseEntity;
        User user = null;
        Transaction transaction = null;
        try {
            user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
            transaction = transact(TransactionType.CREDIT,user,amount);
            responseEntity = ResponseEntity.ok(transaction);
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.notFound().build();
        } catch (Exception e) {
            if (transaction == null)
                transaction = new Transaction(TransactionType.CREDIT, user, amount);
            transaction.setStatus(TransactionStatus.DECLINED);
            transactionRepository.save(transaction);
            responseEntity = ResponseEntity.badRequest().body(transaction);
        }
        return responseEntity;
    }

    @Transactional
    public ResponseEntity<?> transferMoney(String userId, String receiverId, Float amount) {
        ResponseEntity responseEntity;
        User user = null;
        Transaction transaction = null;
        try {
            user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException(userId));
            User receiver = userRepository.findById(receiverId).orElseThrow(()->new NotFoundException(receiverId));
            transaction = transact(TransactionType.DEBIT,user,-amount);
            transact(TransactionType.CREDIT,receiver,amount);
            responseEntity = ResponseEntity.ok(transaction);
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            if (transaction == null)
                transaction = new Transaction(TransactionType.DEBIT, user, amount);
            transaction.setStatus(TransactionStatus.DECLINED);
            transactionRepository.save(transaction);
            responseEntity = ResponseEntity.badRequest().body(transaction);
        }
        return responseEntity;
    }

    private Transaction transact(TransactionType transactionType, User user, float amount) throws Exception {
        Transaction transaction = new Transaction(transactionType, user, amount);
        transaction = transactionRepository.saveAndFlush(transaction);
        updateWallet(user, amount);
        transaction.setStatus(TransactionStatus.COMPLETE);
        return transaction;
    }

     void updateWallet(User user, float amount) throws Exception {
        float walletAmount = user.getWallet();
        walletAmount += amount;
        if(walletAmount<0)
            throw new Exception("Not enough balance"+user.getWallet());
        user.setWallet(walletAmount);
    }

    public ResponseEntity<?> getUserTransactions(String userId) {
        ResponseEntity responseEntity;
        try {
            User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(userId));
            List<Transaction> transactions = transactionRepository.findByUser(user);
            responseEntity = ResponseEntity.ok(transactions);
        } catch (NotFoundException e) {
            responseEntity = ResponseEntity.notFound().build();
        }

        return responseEntity;
    }
}
