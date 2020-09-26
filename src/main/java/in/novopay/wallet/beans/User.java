package in.novopay.wallet.beans;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @NotEmpty
    @Size(min = 8, max = 16)
    @Column(unique = true, length = 16)
    String phone;

    @NotEmpty
    @Size(min = 3, max = 240)
    @Column(unique = true, length = 240)
    String email;

    String password;
    float wallet;
}