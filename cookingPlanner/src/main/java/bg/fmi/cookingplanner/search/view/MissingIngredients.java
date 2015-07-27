package bg.fmi.cookingplanner.search.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelClickedListener;
import antistatic.spinnerwheel.OnWheelScrollListener;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.search.adapter.EditTextViewAdapter;
import bg.fmi.cookingplanner.search.adapter.FoodTypesAdapter;
import bg.fmi.cookingplanner.search.adapter.MissingIngredientsAdapter;
import bg.fmi.cookingplanner.model.FoodType;
import bg.fmi.cookingplanner.model.Ingredient;

/**
 * Created by krasimira on 15-7-23.
 */
public class MissingIngredients implements bg.fmi.cookingplanner.search.view.View {
    Map<FoodType, MissingIngredientsAdapter> ingredientsAdaptersForType =
            new HashMap<FoodType, MissingIngredientsAdapter>();
    private final AbstractWheel ingredientsView;
    private final Activity context;
    private final AbstractWheel typesView;
    private final AutoCompleteTextView textView;
    private final EditTextViewAdapter editTextViewAdapter;
    private final FoodTypesAdapter foodTypesAdapter;
    private bg.fmi.cookingplanner.search.view.View oppositeView;

    public MissingIngredients(Activity context, final List<Ingredient> ingredients) {
        this.context = context;
        this.oppositeView = oppositeView;
        this.ingredientsView = (AbstractWheel) context.findViewById(R.id.missingIngredientsViewSearch);
        this.typesView = (AbstractWheel) context.findViewById(R.id.foodTypeViewSearch);

        for (Ingredient ingredient: ingredients) {
            FoodType foodType = ingredient.getType();
            if (!ingredientsAdaptersForType.containsKey(foodType)) {
                MissingIngredientsAdapter adapter =
                        new MissingIngredientsAdapter(context, new ArrayList<Ingredient>());
                adapter.setTextSize(18);
                ingredientsAdaptersForType.put(foodType, adapter);
            }
            ingredientsAdaptersForType.get(foodType).add(ingredient);
        }

        typesView.setVisibleItems(5);
        typesView.setCurrentItem(3);
        foodTypesAdapter = new FoodTypesAdapter(context, new ArrayList(ingredientsAdaptersForType.keySet()));
        typesView.setViewAdapter(foodTypesAdapter);
        typesView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {
                updateIngredients();
            }

            @Override
            public void onScrollingStarted(AbstractWheel arg0) {

            }
        });

        this.ingredientsView.setVisibleItems(5);
        this.ingredientsView.addClickingListener(new OnWheelClickedListener() {

            @Override
            public void onItemClicked(final AbstractWheel view,
                                      int position) {
                final FoodType currentFoodType = getCurrentType();
                final Ingredient toBeAddedIngredient = ingredientsAdaptersForType.get(
                        currentFoodType).getItem(position);

                markIngredientAsExisting(toBeAddedIngredient);
            }
        });

        textView = (AutoCompleteTextView) context.findViewById(R.id.editTextSearch);
        textView.setThreshold(1);

        editTextViewAdapter = new EditTextViewAdapter(context, ingredients);
        textView.setAdapter(editTextViewAdapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Ingredient toBeAddedIngredient = editTextViewAdapter.getItem(position);
                markIngredientAsExisting(toBeAddedIngredient);
                textView.setText("");
            }
        });

        updateIngredients();
    }

    public void setOppositeView(bg.fmi.cookingplanner.search.view.View oppositeView) {
        this.oppositeView = oppositeView;
    }

    public List<Ingredient> getIngredients() {
        return this.editTextViewAdapter.getIngredients();
    }

    public void markIngredientAsMissing(Ingredient ingredient) {
        editTextViewAdapter.add(ingredient);
        ingredientsAdaptersForType.get(ingredient.getType()).add(ingredient);
        updateIngredients();
    }

    public void markIngredientAsExisting(Ingredient ingredient) {
        ingredientsAdaptersForType.get(ingredient.getType()).remove(ingredient);
        editTextViewAdapter.remove(ingredient);
        updateIngredients();
        oppositeView.markIngredientAsExisting(ingredient);
    }

    /**
     * Updates the ingredients spinnerwheel
     */
    private void updateIngredients() {
        final FoodType currentFoodType = getCurrentType();
        MissingIngredientsAdapter adapter = ingredientsAdaptersForType.get(currentFoodType);

        ingredientsView.setViewAdapter(adapter);
        if (adapter.getItemsCount() > 1)
            ingredientsView.setCurrentItem(1);
    }

    private FoodType getCurrentType() {
        return foodTypesAdapter.getItem(typesView.getCurrentItem());
    }


}
