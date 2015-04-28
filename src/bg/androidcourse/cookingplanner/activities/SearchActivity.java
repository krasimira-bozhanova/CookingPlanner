package bg.androidcourse.cookingplanner.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelClickedListener;
import antistatic.spinnerwheel.OnWheelScrollListener;
import antistatic.spinnerwheel.WheelVerticalView;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.adapters.EditTextViewAdapter;
import bg.androidcourse.cookingplanner.adapters.FoodTypesAdapter;
import bg.androidcourse.cookingplanner.adapters.NotAddedIngredientsAdapter;
import bg.androidcourse.cookingplanner.data.tables.FoodTypeData;
import bg.androidcourse.cookingplanner.data.tables.IngredientData;
import bg.androidcourse.cookingplanner.data.tables.RecipeData;
import bg.androidcourse.cookingplanner.models.FoodType;
import bg.androidcourse.cookingplanner.models.Ingredient;
import bg.androidcourse.cookingplanner.models.Recipe;
import bg.androidcourse.tagview.TagListView;
import bg.androidcourse.tagview.TagView;

public class SearchActivity extends FragmentActivity {
    Map<String, List<Ingredient>> allIngredients;
    List<Ingredient> addedIngredients;
    List<FoodType> allTypes;
    TagListView addedIngrediendsListView;
    AbstractWheel notAddedIngredientsView;
    EditTextViewAdapter adapter;
    AbstractWheel foodTypesView;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("added-ingredients",
                (Serializable) addedIngredients);
        outState.putSerializable("all-ingredients",
                (Serializable) allIngredients);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        allTypes = FoodTypeData.getInstance().getAllTypes();

        if (savedInstanceState == null) {
            allIngredients = IngredientData.getInstance()
                    .getIngredientsForType();
            addedIngredients = new ArrayList<Ingredient>();
        } else {
            allIngredients = (Map<String, List<Ingredient>>) savedInstanceState
                    .getSerializable("all-ingredients");
            addedIngredients = (List<Ingredient>) savedInstanceState
                    .getSerializable("added-ingredients");
        }

        Button button = (Button) findViewById(R.id.searchViewSearch);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipe();
            }
        });

        foodTypesView = (WheelVerticalView) findViewById(R.id.foodTypeViewSearch);
        notAddedIngredientsView = (WheelVerticalView) findViewById(R.id.notAddedIngredientViewSearch);
        addedIngrediendsListView = (TagListView) findViewById(R.id.addedIngredientViewSearch);

        foodTypesView.setVisibleItems(5);
        foodTypesView.setViewAdapter(new FoodTypesAdapter(this, allTypes));

        foodTypesView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingFinished(AbstractWheel wheel) {
                updateIngredients(notAddedIngredientsView);
            }

            @Override
            public void onScrollingStarted(AbstractWheel arg0) {

            }
        });

        foodTypesView.setCurrentItem(3);

        notAddedIngredientsView.setVisibleItems(5);
        notAddedIngredientsView
                .addClickingListener(new OnWheelClickedListener() {

                    @Override
                    public void onItemClicked(final AbstractWheel view,
                            int position) {
                        final String currentFoodType = allTypes.get(
                                foodTypesView.getCurrentItem()).getName();
                        final Ingredient addedIngredient = allIngredients.get(
                                currentFoodType).get(position);

                        manageIngredientAdding(addedIngredient);
                        updateIngredients(view);
                    }
                });

        for (final Ingredient ingredient : addedIngredients) {
            addedIngrediendsListView.addView(createTag(ingredient));
        }

        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.editTextSearch);
        textView.setThreshold(1);

        List<Ingredient> notAddedIngredients = new ArrayList<Ingredient>();
        for (String type : allIngredients.keySet()) {
            notAddedIngredients.addAll(allIngredients.get(type));
        }

        adapter = new EditTextViewAdapter(this, new ArrayList<Ingredient>(
                notAddedIngredients));
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Ingredient ingredient = adapter.mIngredientList.get(position);
                manageIngredientAdding(ingredient);
                textView.setText("");

                updateIngredients(notAddedIngredientsView);
            }
        });
        updateIngredients(notAddedIngredientsView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
        super.onBackPressed();
    }

    private void manageIngredientAdding(Ingredient ingredient) {
        allIngredients.get(ingredient.getType().getName()).remove(ingredient);
        adapter.remove(ingredient);
        addedIngredients.add(ingredient);
        addedIngrediendsListView.addView(createTag(ingredient));
    }

    private TagView createTag(final Ingredient ingredient) {
        final TagView tag = new TagView(this);
        tag.setText(ingredient.getName());
        tag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup parent = addedIngrediendsListView;
                parent.removeView(tag);
                addedIngredients.remove(ingredient);
                adapter.add(ingredient);
                allIngredients.get(ingredient.getType().getName()).add(
                        ingredient);
                updateIngredients(notAddedIngredientsView);
            }
        });
        return tag;
    }

    /**
     * Updates the ingredients spinnerwheel
     */
    private void updateIngredients(AbstractWheel view) {
        List<Ingredient> ingredientsForType = allIngredients.get(allTypes.get(
                foodTypesView.getCurrentItem()).getName());
        NotAddedIngredientsAdapter adapter = new NotAddedIngredientsAdapter(
                this, ingredientsForType);
        adapter.setTextSize(18);
        view.setViewAdapter(adapter);
        if (ingredientsForType.size() > 1)
            view.setCurrentItem(1);
    }

    private void searchRecipe() {
        List<Recipe> resultRecipes = RecipeData.getInstance()
                .getRecipesIdsWithIngredients(addedIngredients);

        if (resultRecipes.size() > 0) {
            Intent intent = new Intent(this, ResultsActivity.class);

            Bundle argumentsForActivity = new Bundle();
            argumentsForActivity.putSerializable("result-recipes",
                    (Serializable) resultRecipes);
            intent.putExtras(argumentsForActivity);
            startActivity(intent);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setTitle("CookingPlanner");
            alertDialogBuilder
                    .setMessage("There are no recipes with these ingredients")
                    .setCancelable(false)
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
