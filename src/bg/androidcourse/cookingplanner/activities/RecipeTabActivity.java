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
import bg.androidcourse.cookingplanner.fragments.RecipeTabContentFragment;
import bg.androidcourse.cookingplanner.fragments.RecipeTabStagesFragment;
import bg.androidcourse.cookingplanner.fragments.RecipeTabSummaryFragment;

public abstract class RecipeTabActivity extends FragmentActivity {

    ActionBar.Tab tabSummary, tabContent, tabStages;
    protected Fragment fragmentTabSummary;
    protected Fragment fragmentTabContent;
    protected Fragment fragmentTabStages;

    protected abstract void setFragments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setFragments();

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Map<String, Fragment> textToFragment = new LinkedHashMap<String, Fragment>();
        textToFragment.put("Summary", new RecipeTabSummaryFragment());
        textToFragment.put("You'll need", new RecipeTabContentFragment());
        textToFragment.put("Stages", new RecipeTabStagesFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (String tabText : textToFragment.keySet()) {
            ActionBar.Tab tab = actionBar.newTab().setText(tabText);
            tab.setTabListener(new RecipeTabListener(textToFragment
                    .get(tabText), fragmentManager));
            actionBar.addTab(tab);
        }
    }

    private class RecipeTabListener implements ActionBar.TabListener {

        Fragment fragment;
        android.support.v4.app.FragmentManager fragmentManager;

        RecipeTabListener(Fragment fragment,
                android.support.v4.app.FragmentManager fragmentManager) {
            this.fragment = fragment;
            this.fragmentManager = fragmentManager;
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
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
