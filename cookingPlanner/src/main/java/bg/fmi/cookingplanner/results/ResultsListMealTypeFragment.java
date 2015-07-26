package bg.fmi.cookingplanner.results;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.models.MealType;
import bg.fmi.cookingplanner.models.Recipe;

public class ResultsListMealTypeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.result_recipes,
                container, false);
        @SuppressWarnings("unchecked")
        List<Recipe> allRecipes = (List<Recipe>)getActivity().getIntent().
                getExtras().getSerializable("result-recipes");

        if (allRecipes != null) {
            MealType mealType = (MealType) getArguments().getSerializable("mealType");
            List<Recipe> filteredRecipes;
            if (mealType == null) {
                filteredRecipes = new ArrayList<Recipe>(allRecipes);
            } else {
                filteredRecipes = new ArrayList<Recipe>();
                for (Recipe recipe: allRecipes) {
                    if (recipe.getMealType().getId() == mealType.getId()) {
                        filteredRecipes.add(recipe);
                    }
                }
            }

            ListView listView = (ListView) view.findViewById(R.id.resultsListView);
            listView.setAdapter(new ResultsListAdapter(filteredRecipes));
        }
        return view;
    }
}