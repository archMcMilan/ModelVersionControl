package domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Artem Pryzhkov
 *         21.04.17.
 */

@Data
@Entity
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue
    @Column(name = "mark_id")
    private Long id;

    @Column(nullable = false)
    private String name;
}
