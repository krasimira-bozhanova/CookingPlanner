package bg.androidcourse.cookingplanner.activities;

import android.os.Bundle;
import bg.androidcourse.cookingplanner.fragments.AddRecipeTabContentFragment;
import bg.androidcourse.cookingplanner.fragments.AddRecipeTabStagesFragment;
import bg.androidcourse.cookingplanner.fragments.AddRecipeTabSummaryFragment;

public class AddActivity extends RecipeTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setFragments() {
        this.fragmentTabSummary = new AddRecipeTabSummaryFragment();
        this.fragmentTabContent = new AddRecipeTabContentFragment();
        this.fragmentTabStages = new AddRecipeTabStagesFragment();
    }
}

