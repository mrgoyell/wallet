package in.novopay.wallet.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.novopay.wallet.enums.TransactionStatus;
import in.novopay.wallet.enums.TransactionType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 50)
    String id;

    @Column(nullable = false)
    TransactionType type;

    @Column(nullable = false)
    TransactionStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    User user;  //main entity

    @CreationTimestamp
    Date creationTimestamp;

    @UpdateTimestamp
    Date updationTimestamp;

    public Transaction(TransactionType type, User user) {
        this.type = type;
        this.user = user;
        this.status = TransactionStatus.INCOMPLETE;
    }
}
