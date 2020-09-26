package in.novopay.wallet.repository;

import in.novopay.wallet.beans.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,String> {

}
