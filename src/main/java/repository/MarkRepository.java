package repository;

import domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Artem Pryzhkov
 *         21.04.17.
 */
public interface MarkRepository extends JpaRepository<Mark,Long>{
    List<Mark> findAllByNameIgnoreCaseContaining(String name);
}
