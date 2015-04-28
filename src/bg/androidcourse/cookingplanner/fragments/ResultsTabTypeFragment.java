package bg.androidcourse.cookingplanner.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.adapters.RecipeListAdapter;
import bg.androidcourse.cookingplanner.models.Recipe;
import bg.androidcourse.cookingplanner.models.Type;

public class ResultsTabTypeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.result_recipes,
                container, false);
        @SuppressWarnings("unchecked")
        List<Recipe> allRecipes = (List<Recipe>)getActivity().getIntent().
                getExtras().getSerializable("result-recipes");

        if (allRecipes != null) {
            Type type = (Type) getArguments().getSerializable("type");
            List<Recipe> filteredRecipes;
            if (type == null) {
                filteredRecipes = new ArrayList<Recipe>(allRecipes);
            } else {
                filteredRecipes = new ArrayList<Recipe>();
                for (Recipe recipe: allRecipes) {
                    if (recipe.getType().getId() == type.getId()) {
                        filteredRecipes.add(recipe);
                    }
                }
            }

            ListView listView = (ListView) view.findViewById(R.id.resultsListView);
            listView.setAdapter(new RecipeListAdapter(filteredRecipes));
        }
        return view;
    }
}
