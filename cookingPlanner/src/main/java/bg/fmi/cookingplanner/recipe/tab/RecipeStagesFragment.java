package bg.fmi.cookingplanner.recipe.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Description;
import bg.fmi.cookingplanner.recipe.RecipeFragment;

public class RecipeStagesFragment extends RecipeFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.recipe_stages,
                container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.recipeLinearLayout);
        Description description = getRecipe().getDescription();

        for (int i = 0; i < description.getStages().length; i++) {
            String stage = description.getStages()[i];
            TextView textView = (TextView) inflater.inflate(R.layout.recipe_stage, null);
            textView.setText(Integer.toString(i + 1) + ". " + stage);
            linearLayout.addView(textView);
        }
        return view;
    }
}
