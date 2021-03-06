package bg.fmi.cookingplanner.search.view.missing;

import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelClickedListener;
import antistatic.spinnerwheel.OnWheelScrollListener;
import bg.fmi.cookingplanner.data.model.FoodType;
import bg.fmi.cookingplanner.data.model.Ingredient;
import bg.fmi.cookingplanner.search.SearchBinderActivity;
import bg.fmi.cookingplanner.search.view.SearchIngredientsView;
import bg.fmi.cookingplanner.search.view.missing.wheel.FoodTypesWheel;
import bg.fmi.cookingplanner.search.view.missing.wheel.IngredientsWheel;

/**
 * Created by krasimira on 15-7-23.
 */
public class MissingIngredientsView extends SearchIngredientsView
        implements OnWheelClickedListener, OnWheelScrollListener, AdapterView.OnItemClickListener {

    private final FoodTypesWheel foodTypesWheel;
    private final IngredientsWheel ingredientsWheel;
    private final EditTextView editTextView;
    private final SearchBinderActivity binder;

    public MissingIngredientsView(SearchBinderActivity context, final List<Ingredient> ingredients) {
        this.binder = context;
        foodTypesWheel = new FoodTypesWheel(context, this);
        ingredientsWheel = new IngredientsWheel(context, this, ingredients);
        editTextView = new EditTextView(context, this, ingredients);

        ingredientsWheel.updateIngredients(foodTypesWheel.getCurrentType());
    }

    public List<Ingredient> getIngredients() {
        return this.editTextView.getIngredients();
    }

    public void markIngredientAsMissing(Ingredient ingredient) {
        editTextView.markIngredientAsMissing(ingredient);
        ingredientsWheel.markIngredientAsMissing(ingredient);
    }

    public void markIngredientAsExisting(Ingredient ingredient) {
        ingredientsWheel.markIngredientAsExisting(ingredient);
        editTextView.markIngredientAsExisting(ingredient);
        binder.markIngredientAsExisting(ingredient);
    }

    @Override
    public void onItemClicked(AbstractWheel wheel, int position) {
        final FoodType currentFoodType = foodTypesWheel.getCurrentType();
        final Ingredient toBeAddedIngredient = ingredientsWheel.getIngredient(currentFoodType, position);
        markIngredientAsExisting(toBeAddedIngredient);
    }

    @Override
    public void onScrollingStarted(AbstractWheel wheel) {

    }

    @Override
    public void onScrollingFinished(AbstractWheel wheel) {
        this.ingredientsWheel.updateIngredients(foodTypesWheel.getCurrentType());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Ingredient toBeAddedIngredient = editTextView.getIngredient(position);
        markIngredientAsExisting(toBeAddedIngredient);
    }

}
