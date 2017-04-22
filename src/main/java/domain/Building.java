package domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Artem Pryzhkov
 *         21.04.17.
 */

@Data
@Entity
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue
    @Column(name = "building_id")
    private Long id;

    @Column(nullable = false)
    private String name;
}
