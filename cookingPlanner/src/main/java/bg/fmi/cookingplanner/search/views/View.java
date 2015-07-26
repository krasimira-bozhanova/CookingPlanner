package bg.fmi.cookingplanner.search.views;

import java.util.List;

import bg.fmi.cookingplanner.models.Ingredient;

/**
 * Created by krasimira on 15-7-24.
 */
public interface View {

    void markIngredientAsExisting(Ingredient ingredient);
    void markIngredientAsMissing(Ingredient ingredient);
    List<Ingredient> getIngredients();
    void setOppositeView(View oppositeView);
}
