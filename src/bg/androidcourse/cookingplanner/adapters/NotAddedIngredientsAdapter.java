package bg.androidcourse.cookingplanner.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.models.Ingredient;

public class NotAddedIngredientsAdapter extends AbstractWheelTextAdapter {
    // Countries names
    private List<Ingredient> ingredients;

    /**
     * Constructor
     */
    public NotAddedIngredientsAdapter(Context context,
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

    @Override
    protected CharSequence getItemText(int index) {
        return ingredients.get(index).getName();
    }

    private class ViewHolder {
        TextView textView;
    }
}
