package bg.fmi.cookingplanner.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.results.ResultsListActivity;
import bg.fmi.cookingplanner.data.tables.IngredientData;
import bg.fmi.cookingplanner.data.tables.RecipeData;
import bg.fmi.cookingplanner.model.Ingredient;
import bg.fmi.cookingplanner.model.Recipe;
import bg.fmi.cookingplanner.util.Utils;
import bg.fmi.cookingplanner.search.view.ExistingIngredients;
import bg.fmi.cookingplanner.search.view.MissingIngredients;

public class SearchActivity extends FragmentActivity {

    private bg.fmi.cookingplanner.search.view.View existingIngredientsView;
    private bg.fmi.cookingplanner.search.view.View missingIngredientsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        List<Ingredient> currentExistingIngredients = null;
        List<Ingredient> currentMissingIngredients = null;

        if (savedInstanceState == null) {
            currentMissingIngredients = IngredientData.getInstance()
                    .getAllIngredients();
            currentExistingIngredients = new ArrayList<Ingredient>();
        } else {
            currentMissingIngredients = (List<Ingredient>) savedInstanceState
                    .getSerializable("all-ingredients");
            currentExistingIngredients = (List<Ingredient>) savedInstanceState
                    .getSerializable("added-ingredients");
        }

        existingIngredientsView = new ExistingIngredients(this, currentExistingIngredients);
        missingIngredientsView = new MissingIngredients(this, currentMissingIngredients);
        existingIngredientsView.setOppositeView(missingIngredientsView);
        missingIngredientsView.setOppositeView(existingIngredientsView);

        Button button = (Button) findViewById(R.id.searchViewSearch);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipe();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("added-ingredients",
                (Serializable) existingIngredientsView.getIngredients());
        outState.putSerializable("all-ingredients",
                (Serializable) missingIngredientsView.getIngredients());
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

    private void searchRecipe() {
        List<Recipe> resultRecipes = RecipeData.getInstance()
                .getRecipesIdsWithIngredients(existingIngredientsView.getIngredients());

        if (resultRecipes.size() > 0) {
            Intent intent = new Intent(this, ResultsListActivity.class);

            Bundle argumentsForActivity = new Bundle();
            argumentsForActivity.putSerializable("result-recipes",
                    (Serializable) resultRecipes);
            intent.putExtras(argumentsForActivity);
            startActivity(intent);
        } else {
            Utils.showMessage(SearchActivity.this, "There are no recipes with these ingredients");
        }
    }
}
