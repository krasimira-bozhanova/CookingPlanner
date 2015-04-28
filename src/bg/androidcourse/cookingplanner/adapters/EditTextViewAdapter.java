package bg.androidcourse.cookingplanner.adapters;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.models.Ingredient;


public class EditTextViewAdapter extends ArrayAdapter<Ingredient> implements
        Filterable {

    private final ArrayList<Ingredient> mFullIngredientList;
    public ArrayList<Ingredient> mIngredientList;

    public EditTextViewAdapter(Context context,
            ArrayList<Ingredient> allIngredients) {
        super(context, R.layout.list_ingredient, allIngredients);
        mFullIngredientList = allIngredients;
        mIngredientList = new ArrayList<Ingredient>(mFullIngredientList);
    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return mIngredientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup ingredientTextView = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            ingredientTextView = (ViewGroup) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_ingredient, null);

            viewHolder = new ViewHolder();

            viewHolder.textView = (TextView) ingredientTextView
                    .findViewById(R.id.listIngredientTextView);
            ingredientTextView.setTag(viewHolder);
        } else {
            ingredientTextView = (ViewGroup) convertView;
            viewHolder = (ViewHolder) ingredientTextView.getTag();
        }

        viewHolder.textView.setText(getItem(position).getName());
        viewHolder.textView.setGravity(Gravity.CENTER);
        return ingredientTextView;
    }

    @Override
    public Filter getFilter() {
        return new IngredientFilter();
    }

    @SuppressLint("DefaultLocale")
    private class IngredientFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null && !constraint.equals("")) {
                final ArrayList<Ingredient> filteredIngredients = new ArrayList<Ingredient>(
                        mFullIngredientList.size());
                for (Ingredient ingredient : mFullIngredientList) {
                    if (ingredient.getName().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        filteredIngredients.add(ingredient);
                    }
                }
                final FilterResults results = new FilterResults();
                results.values = filteredIngredients;
                results.count = filteredIngredients.size();
                return results;
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                FilterResults results) {
            if (results != null) {
                List<Ingredient> resultList = (ArrayList<Ingredient>) results.values;
                if (resultList != null && resultList.size() > 0) {
                    mIngredientList = (ArrayList<Ingredient>) results.values;
                    notifyDataSetChanged();
                }
            }
        }

    }
}
