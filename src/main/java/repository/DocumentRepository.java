package repository;

import domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Artem Pryzhkov
 *         17.04.17.
 */
public interface DocumentRepository extends JpaRepository<Document,Long>{
    List<Document> findAllByProjectId(Long projectId);
    List<Document> findAllByDeveloper(Company developer);
    List<Document> findAllByExecutor(Company executor);

    /**
     * head filters
     * @param projectId
     * @return
     */
    List<Document> findAllByMarkAndProjectIdIn(List<Mark> mark, Long projectId);
    List<Document> findAllByBuildingAndProjectIdIn(List<Building> building, Long projectId);
    List<Document> findAllByDocumentTypeAndProjectIdIn(List<DocumentType> type, Long ProjectId);
    List<Document> findAllByCodeAndProjectId(Long code, Long projectId);
    List<Document> findAllByNameIgnoreCaseContainingAndProjectId(String name, Long ProjectId);
    List<Document> findAllByExecutorAndProjectIdIn(List<Company> executors, Long ProjectId);
    List<Document> findAllByDeveloperAndProjectIdIn(List<Company> developers, Long ProjectId);

    /**
     * developer filters
     * @param developer
     * @return
     */
    List<Document> findAllByMarkAndDeveloperIn(List<Mark> mark, Company developer);
    List<Document> findAllByBuildingAndDeveloperIn(List<Building> building, Company developer);
    List<Document> findAllByDocumentTypeAndDeveloperIn(List<DocumentType> type, Company developer);
    List<Document> findAllByCodeAndDeveloper(Long code, Company developer);
    List<Document> findAllByNameIgnoreCaseContainingAndDeveloper(String name, Company developer);
    List<Document> findAllByExecutorAndDeveloperIn(List<Company> executors, Company developer);

    /**
     * Executor filters
     * @param executor
     * @return
     */
    List<Document> findAllByMarkAndExecutorIn(List<Mark> mark, Company executor);
    List<Document> findAllByBuildingAndExecutorIn(List<Building> building, Company executor);
    List<Document> findAllByDocumentTypeAndExecutorIn(List<DocumentType> type, Company executor);
    List<Document> findAllByCodeAndExecutor(Long code, Company executor);
    List<Document> findAllByNameIgnoreCaseContainingAndExecutor(String name, Company executor);
    List<Document> findAllByExecutorAndExecutorIn(List<Company> executors, Company executor);
}
