package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Artem Pryzhkov
 *         15.04.17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType {

    @Id
    @GeneratedValue
    @Column(name = "document_type_id")
    private Long id;

    @Column(nullable = false)
    private String type;
}
