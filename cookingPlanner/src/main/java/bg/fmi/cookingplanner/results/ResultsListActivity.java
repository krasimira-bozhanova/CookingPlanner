package bg.fmi.cookingplanner.results;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.tables.MealTypeData;
import bg.fmi.cookingplanner.models.MealType;
import bg.fmi.cookingplanner.utils.FragmentTabListener;

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
                null);
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
}
