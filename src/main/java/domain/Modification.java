package domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Artem Pryzhkov
 *         15.04.17.
 */
@Data
@Entity
@Table(name = "modification")
public class Modification {

    @Id
    @GeneratedValue
    @Column(name = "modification_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    private String permit;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false)
    private String changeDate;

    @Enumerated(EnumType.STRING)
    private ModificationStatus modificationStatus;
}
