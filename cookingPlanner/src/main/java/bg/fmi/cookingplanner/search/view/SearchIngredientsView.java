package bg.fmi.cookingplanner.search.view;

import java.util.List;

import bg.fmi.cookingplanner.model.Ingredient;

/**
 * Created by krasimira on 15-7-24.
 */
public abstract class SearchIngredientsView {

    public abstract void markIngredientAsExisting(Ingredient ingredient);
    public abstract void markIngredientAsMissing(Ingredient ingredient);
    public abstract List<Ingredient> getIngredients();
}
