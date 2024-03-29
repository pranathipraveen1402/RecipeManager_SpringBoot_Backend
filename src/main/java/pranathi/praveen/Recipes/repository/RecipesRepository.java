package pranathi.praveen.Recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pranathi.praveen.Recipes.model.Recipes;

import java.util.Optional;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Long> {

    int countRatingsById(Long id);
    Optional<Recipes> findById(Long id);

}