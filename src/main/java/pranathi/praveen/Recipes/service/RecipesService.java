package pranathi.praveen.Recipes.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import pranathi.praveen.Recipes.model.Recipes;
import pranathi.praveen.Recipes.repository.RecipesRepository;

import java.util.*;

@Service
public class RecipesService {

    @Autowired
    private RecipesRepository recipesRepository;

    public Recipes saveRecipes(Recipes recipe) {
        return recipesRepository.save(recipe);
    }

    public List<Recipes> allRecipes() {

        return recipesRepository.findAll();
    }

    private Map<Long, List<Integer>> ratingsMap = new HashMap<>();

    @Transactional
    public void rateRecipe(Long recipeId, int rating) {
        List<Integer> ratings = ratingsMap.getOrDefault(recipeId, new ArrayList<>());
        ratings.add(rating);
        ratingsMap.put(recipeId, ratings);
        double averageRating = calculateAverageRating(ratings);
        updateAverageRatingInDatabase(recipeId, averageRating);
    }

    private double calculateAverageRating(List<Integer> ratings) {
        int count = ratings.size();
        System.out.println("Number of ratings: " + count);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return (double) sum / count;
    }

    private void updateAverageRatingInDatabase(Long recipeId, double averageRating) {
        Recipes recipe = recipesRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));
        recipe.setAverageRating(averageRating);
        recipesRepository.save(recipe);
    }

    public Recipes getRecipeById(Long id) {
        Optional<Recipes> optionalRecipe = recipesRepository.findById(id);
        return optionalRecipe.orElse(null);
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        Optional<Recipes> optionalRecipe = recipesRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipes recipe = optionalRecipe.get();
            recipesRepository.delete(recipe);
        } else {
            throw new IllegalArgumentException("Recipe not found with ID: " + recipeId);
        }
    }
}
