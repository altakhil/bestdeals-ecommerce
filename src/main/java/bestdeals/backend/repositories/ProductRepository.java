package bestdeals.backend.repositories;

import bestdeals.backend.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"reviews"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"reviews"})
    Optional<Product> findById(Long id);
}
