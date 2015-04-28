package bg.androidcourse.cookingplanner.activities;

import android.os.Bundle;
import bg.androidcourse.cookingplanner.fragments.RecipeTabContentFragment;
import bg.androidcourse.cookingplanner.fragments.RecipeTabStagesFragment;
import bg.androidcourse.cookingplanner.fragments.RecipeTabSummaryFragment;

public class RecipeActivity extends RecipeTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setFragments() {
        this.fragmentTabSummary = new RecipeTabSummaryFragment();
        this.fragmentTabContent = new RecipeTabContentFragment();
        this.fragmentTabStages = new RecipeTabStagesFragment();
    }
}