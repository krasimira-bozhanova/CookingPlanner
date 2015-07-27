package bg.fmi.cookingplanner.search.view;

import android.app.Activity;
import android.view.View;

import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Ingredient;
import bg.fmi.cookingplanner.search.view.tags.TagListView;
import bg.fmi.cookingplanner.search.view.tags.TagView;


/**
 * Created by krasimira on 15-7-23.
 */
public class ExistingIngredients implements bg.fmi.cookingplanner.search.view.View {

    private final List<Ingredient> ingredients;
    private final TagListView view;
    private final Activity context;
    private bg.fmi.cookingplanner.search.view.View oppositeView;

    public ExistingIngredients(Activity context, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.context = context;
        this.view = (TagListView) context.findViewById(R.id.addedIngredientViewSearch);;

        for (final Ingredient ingredient : ingredients) {
            view.addView(createTag(ingredient));
        }
    }

    public void setOppositeView(bg.fmi.cookingplanner.search.view.View oppositeView) {
        this.oppositeView = oppositeView;
    }

    public void markIngredientAsExisting(Ingredient ingredient) {
        ingredients.add(ingredient);
        view.addView(createTag(ingredient));
    }

    public void markIngredientAsMissing(Ingredient ingredient) {
        ingredients.remove(ingredient);
        oppositeView.markIngredientAsMissing(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    private TagView createTag(final Ingredient ingredient) {
        final TagView tag = new TagView(context);
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
