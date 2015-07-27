package bg.fmi.cookingplanner.search.view.missing;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Ingredient;
import bg.fmi.cookingplanner.search.adapter.EditTextViewAdapter;

/**
 * Created by krasimira on 15-7-27.
 */
public class EditTextView {

    private final AutoCompleteTextView textView;
    private final EditTextViewAdapter editTextViewAdapter;

    public EditTextView(Activity context, AdapterView.OnItemClickListener clickListener, final List<Ingredient> ingredients) {
        textView = (AutoCompleteTextView) context.findViewById(R.id.editTextSearch);
        textView.setThreshold(1);

        editTextViewAdapter = new EditTextViewAdapter(context, ingredients);
        textView.setAdapter(editTextViewAdapter);

        textView.setOnItemClickListener(clickListener);
    }

    public Ingredient getIngredient(int position) {
        return editTextViewAdapter.getItem(position);
    }

    public void markIngredientAsExisting(Ingredient ingredient) {
        editTextViewAdapter.remove(ingredient);
        textView.setText("");
    }

    public void markIngredientAsMissing(Ingredient ingredient) {
        editTextViewAdapter.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return this.editTextViewAdapter.getIngredients();
    }
}
