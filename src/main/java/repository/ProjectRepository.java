package repository;

import domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Artem on 15.04.17.
 */
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Project findProjectByHeadId(Long headId);
}
