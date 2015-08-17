package bg.fmi.cookingplanner.recipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import bg.fmi.cookingplanner.data.model.Recipe;

/**
 * Created by krasimira on 15-7-29.
 */
public abstract class RecipeFragment extends Fragment {

    protected Recipe recipe;

    protected Recipe getRecipe() {

        if (this.recipe == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            this.recipe = (Recipe) extras.getSerializable("recipe");
        }
        return this.recipe;

    }
}
