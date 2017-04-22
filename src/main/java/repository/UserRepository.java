package repository;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Artem on 15.04.17.
 */
public interface UserRepository extends JpaRepository<User,Long>{
    User findByLogin(String login);
}
