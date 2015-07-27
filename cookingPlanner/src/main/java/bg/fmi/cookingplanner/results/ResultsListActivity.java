package bg.fmi.cookingplanner.results;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.tables.MealTypeData;
import bg.fmi.cookingplanner.model.MealType;
import bg.fmi.cookingplanner.model.Recipe;
import bg.fmi.cookingplanner.util.AlertMessage;
import bg.fmi.cookingplanner.util.FragmentTabListener;

public class ResultsListActivity extends FragmentActivity {

    Map<ActionBar.Tab, MealType> tabToType = new LinkedHashMap<ActionBar.Tab, MealType>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tabToType.put(
                actionBar.newTab().setText("All"),
                MealType.getNeutralMealType());
        tabToType.put(
                actionBar.newTab().setText("Appetizer"),
                MealTypeData.getInstance().getTypeWithName("appetizer"));
        tabToType.put(
                actionBar.newTab().setText("Main course"),
                MealTypeData.getInstance().getTypeWithName("main course"));
        tabToType.put(
                actionBar.newTab().setText("Dessert"),
                MealTypeData.getInstance().getTypeWithName("dessert"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        for (ActionBar.Tab tab: tabToType.keySet()) {
            ResultsListMealTypeFragment fragmentTabType = new ResultsListMealTypeFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("type", tabToType.get(tab));
            fragmentTabType.setArguments(arguments);
            tab.setTabListener(new FragmentTabListener(fragmentTabType, fragmentManager));
            actionBar.addTab(tab);
        }
    }

    public static void start(Context context, List<Recipe> recipes, String errorMessage) {
        Intent intent = new Intent(context, ResultsListActivity.class);

        if (recipes.size() > 0) {
            Bundle argumentsForActivity = new Bundle();
            argumentsForActivity.putSerializable("result-recipes",
                    (Serializable) recipes);
            intent.putExtras(argumentsForActivity);
            context.startActivity(intent);
        } else {
            AlertMessage.show(context, errorMessage);
        }
    }
}
