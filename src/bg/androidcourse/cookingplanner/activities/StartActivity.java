package bg.androidcourse.cookingplanner.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.data.DatabaseHelper;
import bg.androidcourse.cookingplanner.data.tables.RecipeData;
import bg.androidcourse.cookingplanner.models.Recipe;

public class StartActivity extends Activity {

    DatabaseHelper dbHelper;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean initialRun = false;
        prefs = getSharedPreferences("key", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
            initialRun = true;
        }
        dbHelper = new DatabaseHelper(getApplicationContext(), initialRun);

        setContentView(R.layout.activity_main);

        ImageButton searchButton = (ImageButton) findViewById(R.id.toSearch);
        searchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,
                        SearchActivity.class);
                startActivity(intent);
            }
        });

        ImageButton favourites = (ImageButton) findViewById(R.id.toFavourites);
        favourites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,
                        ResultsActivity.class);
                List<Recipe> resultRecipes = new ArrayList<Recipe>();

                for (Recipe recipe : RecipeData.getInstance().getAllRecipes()) {
                    if (recipe.isFavourite()) {
                        resultRecipes.add(recipe);
                    }
                }

                if (resultRecipes.size() > 0) {
                    Bundle argumentsForActivity = new Bundle();
                    argumentsForActivity.putSerializable("result-recipes",
                            (Serializable) resultRecipes);
                    intent.putExtras(argumentsForActivity);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            StartActivity.this);
                    alertDialogBuilder.setTitle("CookingPlanner");
                    alertDialogBuilder
                            .setMessage("You have no favourite recipes")
                            .setCancelable(false)
                            .setNeutralButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        ImageButton add = (ImageButton) findViewById(R.id.toAdd);
        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(StartActivity.this,
                        AddActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
