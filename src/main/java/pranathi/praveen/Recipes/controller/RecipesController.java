package pranathi.praveen.Recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pranathi.praveen.Recipes.model.Recipes;
import pranathi.praveen.Recipes.service.RecipesService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @PostMapping("/add")
    public String add(@RequestBody Recipes recipe)
    {
        recipesService.saveRecipes(recipe);
        return "New Recipe has been added";
    }

    @GetMapping
    public ResponseEntity<List<Recipes>> getAllRecipes() {
        return new ResponseEntity<List<Recipes>>(recipesService.allRecipes(), HttpStatus.OK);
    }

    @PostMapping("/{recipeId}/{rating}")
    public ResponseEntity<String> rateRecipe(@PathVariable Long recipeId, @PathVariable int rating) {
        recipesService.rateRecipe(recipeId, rating);
        return ResponseEntity.ok("Rating added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipes> getRecipeById(@PathVariable Long id) {
        Recipes recipe = recipesService.getRecipeById(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long recipeId) {
        recipesService.deleteRecipe(recipeId);
        return ResponseEntity.ok("Recipe deleted successfully");
    }

}
