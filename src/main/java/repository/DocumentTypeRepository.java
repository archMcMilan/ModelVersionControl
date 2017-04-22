package repository;

import domain.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Artem Pryzhkov
 *         21.04.17.
 */
public interface DocumentTypeRepository extends JpaRepository<DocumentType,Long>{
    List<DocumentType> findByTypeIgnoreCaseContaining(String type);
}
