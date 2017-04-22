package repository;

import domain.Modification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Artem Pryzhkov
 *         17.04.17.
 */
public interface ModificationRepository extends JpaRepository<Modification,Long>{
}
