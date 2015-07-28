package bg.fmi.cookingplanner.search;

import android.app.Activity;

import bg.fmi.cookingplanner.model.Ingredient;
import bg.fmi.cookingplanner.search.view.SearchIngredientsView;

/**
 * Created by krasimira on 15-7-28.
 */
public abstract class SearchBinderActivity extends Activity {

    public abstract void markIngredientAsMissing(Ingredient ingredient);
    public abstract void markIngredientAsExisting(Ingredient ingredient);
}
