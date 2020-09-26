package in.novopay.wallet.beans;

import in.novopay.wallet.enums.TransactionType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 50)
    String id;

    @Column(nullable = false)
    TransactionType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    User primary;  //main entity

    @ManyToOne
    User secondary; //only applicable in case of transfers

    @CreationTimestamp
    Date creationTimestamp;

    @UpdateTimestamp
    Date updationTimestamp;
}
