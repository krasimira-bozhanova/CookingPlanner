package bg.fmi.cookingplanner.cover;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.DatabaseHelper;
import bg.fmi.cookingplanner.data.access.RecipeData;
import bg.fmi.cookingplanner.data.model.Recipe;
import bg.fmi.cookingplanner.results.ResultsListActivity;
import bg.fmi.cookingplanner.search.SearchActivity;

public class FrontPageActivity extends Activity {

    private DatabaseHelper dbHelper;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("key", MODE_PRIVATE);

        boolean isFirstRun = prefs.getBoolean("firstrun", true);
        dbHelper = new DatabaseHelper(getApplicationContext(), isFirstRun);

        setContentView(R.layout.activity_main);

        ImageButton searchButton = (ImageButton) findViewById(R.id.toSearch);
        searchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontPageActivity.this,
                        SearchActivity.class);
                startActivity(intent);
            }
        });

        ImageButton favourites = (ImageButton) findViewById(R.id.toFavourites);
        favourites.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Recipe> favouriteRecipes = RecipeData.getInstance().getFavouriteRecipes();
                ResultsListActivity.start(FrontPageActivity.this, favouriteRecipes,
                        "You have no favourite recipes yet");
            }
        });

//        ImageButton add = (ImageButton) findViewById(R.id.toAdd);
//        add.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(SearchIngredientsView arg0) {
//                Intent intent = new Intent(FrontPageActivity.this,
//                        AddActivity.class);
//                startActivity(intent);
//            }
//
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
