package tr.edu.mcbu.gutenberg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.mcbu.gutenberg.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsUserByUsername(String username);

    User findUserByUsername(String username);

}
