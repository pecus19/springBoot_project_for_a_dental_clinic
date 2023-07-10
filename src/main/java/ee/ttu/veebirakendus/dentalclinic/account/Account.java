package ee.ttu.veebirakendus.dentalclinic.account;

import lombok.*;

import javax.persistence.*;


@Table(name = "account")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String hashPassword;
}
