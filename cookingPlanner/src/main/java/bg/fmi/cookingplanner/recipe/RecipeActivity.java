package bg.fmi.cookingplanner.recipe;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.recipe.tab.RecipeContentFragment;
import bg.fmi.cookingplanner.recipe.tab.RecipeStagesFragment;
import bg.fmi.cookingplanner.recipe.tab.RecipeSummaryFragment;
import bg.fmi.cookingplanner.util.FragmentTabListener;

public class RecipeActivity extends FragmentActivity {

    protected Fragment summaryTab;
    protected Fragment contentTab;
    protected Fragment stagesTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        this.summaryTab = new RecipeSummaryFragment();
        this.contentTab = new RecipeContentFragment();
        this.stagesTab = new RecipeStagesFragment();

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Map<String, Fragment> titleForFragment = new LinkedHashMap<String, Fragment>();
        titleForFragment.put("Summary", new RecipeSummaryFragment());
        titleForFragment.put("You'll need", new RecipeContentFragment());
        titleForFragment.put("Stages", new RecipeStagesFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (String tabText : titleForFragment.keySet()) {
            ActionBar.Tab tab = actionBar.newTab().setText(tabText);
            tab.setTabListener(new FragmentTabListener(titleForFragment
                    .get(tabText), fragmentManager));
            actionBar.addTab(tab);
        }
    }
}
