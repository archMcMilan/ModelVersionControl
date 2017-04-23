package repository;

import domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Artem Pryzhkov
 *         21.04.17.
 */
public interface BuildingRepository extends JpaRepository<Building,Long> {
    List<Building> findAllByNameIgnoreCaseContaining(String name);
    Building findByName(String name);
//    @Query("FROM Document where project.id=?")
//    List<Building> findAllByProjectId(Long projectId);
}
