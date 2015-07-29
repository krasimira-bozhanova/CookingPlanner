package bg.fmi.cookingplanner.recipe.tab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.tables.ContentData;
import bg.fmi.cookingplanner.model.Content;
import bg.fmi.cookingplanner.model.Content.ContentUnit;
import bg.fmi.cookingplanner.model.FoodType;
import bg.fmi.cookingplanner.model.Measurement;
import bg.fmi.cookingplanner.recipe.RecipeFragment;
import bg.fmi.cookingplanner.util.ResourcesUtils;

public class RecipeContentFragment extends RecipeFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.recipe_stages,
                container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.recipeLinearLayout);
        Content content = getRecipe().getContent();
        Map<FoodType,List<ContentUnit>> contentForTypes =
                ContentData.getInstance().getContentForTypes(content);

        for (FoodType type: contentForTypes.keySet()) {
            final LinearLayout typeLayout = (LinearLayout) inflater.inflate(
                    R.layout.food_type, null);

            TextView typeTextView = (TextView) typeLayout.findViewById(R.id.foodTypeTextView);
            typeTextView.setText(type.getName());

            String drawableName = type.getImageName();
            Drawable drawable = ResourcesUtils.getDrawable(getActivity(), drawableName);

            ImageView imageView = (ImageView) typeLayout.findViewById(R.id.foodTypeImageView);
            imageView.setImageDrawable(drawable);

            linearLayout.addView(typeLayout);

            for (Content.ContentUnit contentUnit: contentForTypes.get(type)) {
                StringBuilder builder = new StringBuilder();
                TextView textView = (TextView) inflater.inflate(R.layout.recipe_stage, null);

                double amount = contentUnit.getAmount();
                if (amount != -1) {
                    builder.append(Double.toString(amount) + " ");
                }

                Measurement measurement = contentUnit.getMeasurement();
                if (measurement != null) {
                    if (!measurement.getName().equals("number")) {
                        builder.append(measurement.getName() + " ");
                    }
                }
                builder.append(contentUnit.getIngredient().getName() + " ");
                if (contentUnit.getDescription() != null) {
                    builder.append("(" + contentUnit.getDescription() + ")");
                }
                textView.setText(builder.toString());
                linearLayout.addView(textView);
            }
        }
        return view;
    }
}
