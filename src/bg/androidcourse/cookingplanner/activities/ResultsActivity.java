package bg.androidcourse.cookingplanner.activities;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.data.tables.TypeData;
import bg.androidcourse.cookingplanner.fragments.ResultsTabTypeFragment;
import bg.androidcourse.cookingplanner.models.Type;

public class ResultsActivity extends FragmentActivity {

    Map<ActionBar.Tab, Type> tabToType = new LinkedHashMap<ActionBar.Tab, Type>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_recipes);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tabToType.put(
                actionBar.newTab().setText("All"),
                null);
        tabToType.put(
                actionBar.newTab().setText("Appetizer"),
                TypeData.getInstance().getTypeWithName("appetizer"));
        tabToType.put(
                actionBar.newTab().setText("Main course"),
                TypeData.getInstance().getTypeWithName("main course"));
        tabToType.put(
                actionBar.newTab().setText("Dessert"),
                TypeData.getInstance().getTypeWithName("dessert"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        for (ActionBar.Tab tab: tabToType.keySet()) {
            ResultsTabTypeFragment fragmentTabType = new ResultsTabTypeFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("type", tabToType.get(tab));
            fragmentTabType.setArguments(arguments);
            tab.setTabListener(new ResultsTabListener(fragmentTabType, fragmentManager));
            actionBar.addTab(tab);
        }
    }


    private class ResultsTabListener implements ActionBar.TabListener {

    Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;

    ResultsTabListener(Fragment fragment,
            android.support.v4.app.FragmentManager fragmentManager) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        fragmentManager.beginTransaction().replace(
                R.id.result_recipes_fragment_container, fragment).commit();
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        fragmentManager.beginTransaction().remove(fragment).commit();
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
}
}
