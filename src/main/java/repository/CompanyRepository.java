package repository;

import domain.Company;
import domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Artem Pryzhkov
 *         17.04.17.
 */
public interface CompanyRepository extends JpaRepository<Company,Long>{
    List<Company> findAllByNameIgnoreCaseContaining(String name);
    Company findCompanyByName(String name);
    List<Company> findAllByRole(Role role);
}
