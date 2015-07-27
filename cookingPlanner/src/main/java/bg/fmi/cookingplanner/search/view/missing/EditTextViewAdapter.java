package bg.fmi.cookingplanner.search.view.missing;

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

import java.util.ArrayList;
import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Ingredient;


public class EditTextViewAdapter extends ArrayAdapter<Ingredient> implements
        Filterable {

    private final List<Ingredient> allIngredients;
    private List<Ingredient> filteredIngredients;

    public EditTextViewAdapter(Context context,
            List<Ingredient> allIngredients) {
        super(context, R.layout.list_ingredient, allIngredients);
        this.allIngredients = allIngredients;
        filteredIngredients = new ArrayList<Ingredient>(this.allIngredients);
    }

    @Override
    public int getCount() {
        return filteredIngredients.size();
    }

    @Override
    public Ingredient getItem(int position) {
        return filteredIngredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView textView;
    }

    public List<Ingredient> getIngredients() {
        return this.allIngredients;
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
                        allIngredients.size());
                for (Ingredient ingredient : allIngredients) {
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
                    filteredIngredients = (List<Ingredient>) results.values;
                    notifyDataSetChanged();
                }
            }
        }

    }
}
