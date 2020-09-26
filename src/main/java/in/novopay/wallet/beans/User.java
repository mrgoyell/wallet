package in.novopay.wallet.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 50)
    String id;

    String name;

    @NotEmpty
    @Size(min = 8, max = 16)
    @Column(unique = true, length = 16)
    String phone;

    @NotEmpty
    @Size(min = 3, max = 240)
    @Column(unique = true, length = 240)
    String email;

    @JsonIgnore
    String password;
    float wallet;

    @CreationTimestamp
    Date creationTimestamp;

    @UpdateTimestamp
    Date updationTimestamp;
}