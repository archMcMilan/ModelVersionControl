package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Artem Pryzhkov
 *         17.04.17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    @Column(nullable = false)
    private String name;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<User> users;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


}
