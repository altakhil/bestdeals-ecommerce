package bestdeals.backend.repositories;

import bestdeals.backend.entities.Role;
import bestdeals.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
	Optional<User> findByRole(Role role);
}
