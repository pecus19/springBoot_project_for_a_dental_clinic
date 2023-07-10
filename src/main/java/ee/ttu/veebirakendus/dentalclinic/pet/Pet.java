package ee.ttu.veebirakendus.dentalclinic.pet;

import ee.ttu.veebirakendus.dentalclinic.department.Department;
import lombok.*;

import javax.persistence.*;

@Table(name = "pet")
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
}
