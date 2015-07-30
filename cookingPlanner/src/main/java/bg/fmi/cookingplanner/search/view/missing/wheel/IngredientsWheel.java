package bg.fmi.cookingplanner.search.view.missing.wheel;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelClickedListener;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.FoodType;
import bg.fmi.cookingplanner.model.Ingredient;

/**
 * Created by krasimira on 15-7-27.
 */
public class IngredientsWheel {

    private final AbstractWheel ingredientsView;
    private final Map<FoodType, IngredientsAdapter> ingredientsAdaptersForType =
            new HashMap<FoodType, IngredientsAdapter>();

    public IngredientsWheel(Activity context, OnWheelClickedListener clickedListener, final List<Ingredient> ingredients) {

        for (Ingredient ingredient: ingredients) {
            FoodType foodType = ingredient.getType();
            if (!ingredientsAdaptersForType.containsKey(foodType)) {
                IngredientsAdapter adapter =
                        new IngredientsAdapter(context, new ArrayList<Ingredient>());
                ingredientsAdaptersForType.put(foodType, adapter);
            }
            ingredientsAdaptersForType.get(foodType).add(ingredient);
        }

        this.ingredientsView = (AbstractWheel) context.findViewById(R.id.missingIngredientsViewSearch);
        this.ingredientsView.setVisibleItems(5);
        this.ingredientsView.addClickingListener(clickedListener);
    }

    public Ingredient getIngredient(FoodType foodType, int position) {
        return ingredientsAdaptersForType.get(foodType).getItem(position);
    }

    public void markIngredientAsExisting(Ingredient ingredient) {
        FoodType type = ingredient.getType();
        IngredientsAdapter adapter = ingredientsAdaptersForType.get(type);
        adapter.remove(ingredient);

        if (ingredientsView.getViewAdapter() == adapter) {
            updateIngredients(type);
        }
    }

    public void markIngredientAsMissing(Ingredient ingredient) {
        ingredientsAdaptersForType.get(ingredient.getType()).add(ingredient);
        //updateIngredients(ingredient.getType());
    }

    public void updateIngredients(FoodType currentFoodType) {
        IngredientsAdapter adapter = ingredientsAdaptersForType.get(currentFoodType);
        ingredientsView.setViewAdapter(adapter);
        if (adapter.getItemsCount() > 1)
            ingredientsView.setCurrentItem(1);
    }
}
