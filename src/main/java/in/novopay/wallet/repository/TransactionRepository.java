package in.novopay.wallet.repository;

import in.novopay.wallet.beans.Transaction;
import in.novopay.wallet.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,String> {

    List<Transaction> findByUser(User user);
}
