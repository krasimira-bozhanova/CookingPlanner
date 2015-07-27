package bg.fmi.cookingplanner.search.view.missing.wheel;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Ingredient;

public class IngredientsAdapter extends AbstractWheelTextAdapter {
    // Countries names
    private List<Ingredient> ingredients;

    /**
     * Constructor
     */
    public IngredientsAdapter(Context context,
                              List<Ingredient> ingredients) {
        super(context, R.layout.list_ingredient, NO_RESOURCE);
        this.ingredients = ingredients;
    }

    @Override
    public View getItem(int position, View cachedView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ViewGroup ingredientsView = null;
        if (cachedView == null) {
            ingredientsView = (ViewGroup) LayoutInflater.from(
                    parent.getContext())
                    .inflate(R.layout.list_ingredient, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) ingredientsView
                    .findViewById(R.id.listIngredientTextView);
            ingredientsView.setTag(viewHolder);
        } else {
            ingredientsView = (ViewGroup) cachedView;
            viewHolder = (ViewHolder) ingredientsView.getTag();
        }
        viewHolder.textView.setText(getItemText(position));
        return ingredientsView;
    }

    @Override
    public int getItemsCount() {
        return ingredients.size();
    }

    public Ingredient getItem(int index) {
        return ingredients.get(index);
    }

    public void add(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void remove(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    @Override
    protected CharSequence getItemText(int index) {
        return ingredients.get(index).getName();
    }

    private class ViewHolder {
        TextView textView;
    }
}
