package bg.fmi.cookingplanner.search.view.existing;

import android.app.Activity;
import android.view.View;

import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Ingredient;
import bg.fmi.cookingplanner.search.SearchBinderActivity;
import bg.fmi.cookingplanner.search.view.SearchIngredientsView;
import bg.fmi.cookingplanner.search.view.existing.tags.TagListView;
import bg.fmi.cookingplanner.search.view.existing.tags.TagView;


/**
 * Created by krasimira on 15-7-23.
 */
public class ExistingIngredientsView extends SearchIngredientsView {

    private final List<Ingredient> ingredients;
    private final TagListView view;
    private final SearchBinderActivity binder;

    public ExistingIngredientsView(SearchBinderActivity context, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.binder = context;
        this.view = (TagListView) binder.findViewById(R.id.addedIngredientViewSearch);

        for (final Ingredient ingredient : ingredients) {
            view.addView(createTag(ingredient));
        }
    }

    public void markIngredientAsExisting(Ingredient ingredient) {
        ingredients.add(ingredient);
        view.addView(createTag(ingredient));
    }

    public void markIngredientAsMissing(Ingredient ingredient) {
        ingredients.remove(ingredient);
        binder.markIngredientAsMissing(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    private TagView createTag(final Ingredient ingredient) {
        final TagView tag = new TagView(binder);
        tag.setText(ingredient.getName());
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.removeView(tag);
                markIngredientAsMissing(ingredient);
            }
        });
        return tag;
    }
}
