package bg.fmi.cookingplanner.recipe.tab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.access.RecipeData;
import bg.fmi.cookingplanner.data.model.Image;
import bg.fmi.cookingplanner.recipe.RecipeFragment;
import bg.fmi.cookingplanner.util.AlertMessage;
import bg.fmi.cookingplanner.util.ResourcesUtils;
import it.sephiroth.android.library.widget.HListView;

public class RecipeSummaryFragment extends RecipeFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.recipe_summary, container,
                false);

        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.recipeSummaryVerticalLayout);

        TextView text = (TextView) view
                .findViewById(R.id.recipeSummaryTextView);
        text.setText(getRecipe().getName());

        ImageViewAdapter adapter = new ImageViewAdapter(this.getActivity(),
                R.layout.image_item, new ArrayList<Image>(getRecipe()
                        .getImages()));

        HListView listView = (HListView) view.findViewById(R.id.hListView1);
        listView.setAdapter(adapter);
        setRecipeDetails(inflater, layout);

        return view;
    }

    private class ImageViewAdapter extends ArrayAdapter<Image> {

        List<Image> mItems;
        LayoutInflater mInflater;
        int mResource;

        public ImageViewAdapter(Context context, int resourceId,
                ArrayList<Image> objects) {
            super(context, resourceId, objects);
            mInflater = LayoutInflater.from(context);
            mResource = resourceId;
            mItems = objects;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup recipeImageView = null;
            ViewHolder viewHolder = null;
            if (convertView == null) {
                recipeImageView = (ViewGroup) LayoutInflater.from(
                        parent.getContext()).inflate(mResource, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) recipeImageView
                        .findViewById(R.id.recipeSummaryImageView);
                recipeImageView.setTag(viewHolder);
            } else {
                recipeImageView = (ViewGroup) convertView;
                viewHolder = (ViewHolder) recipeImageView.getTag();
            }

            String drawableName = getItem(position).getName();
            Drawable drawable = ResourcesUtils.getDrawable(parent.getContext(), drawableName);
            viewHolder.imageView.setImageDrawable(drawable);

            return recipeImageView;
        }
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    private void setRecipeDetails(LayoutInflater inflater, final LinearLayout layout) {
        layout.addView(getRecipeDetailsView(inflater,
                " Servings:  " + recipe.getServings(), "servings"));
        layout.addView(getRecipeDetailsView(inflater, " MealType of meal:  "
                + recipe.getMealType().getName(), "dish"));
        layout.addView(getRecipeDetailsView(inflater, " Minutes to cook:  "
                + recipe.getTime(), "clock"));

        final View addToFavourites = getRecipeDetailsView(inflater, " Add to favourites",
                "star");

        final View removeFromFavourites = getRecipeDetailsView(inflater, " Remove from favourites",
                "star");

        addToFavourites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            int result = RecipeData.getInstance().addToFavourites(recipe);
            String message = "The recipe is succesfully added to favourites";
            if (result == 0) {
                message = "There is a problem with adding the recipe to favourites";
            } else {
                layout.removeView(addToFavourites);
                layout.addView(removeFromFavourites);
            }

            AlertMessage.show(getActivity(), message);
            }

        });

        removeFromFavourites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            int result = RecipeData.getInstance().removeFromFavourites(recipe);
            String message = "The recipe is succesfully removed from favourites";
            if (result == 0) {
                message = "There is a problem with removing the recipe from favourites";
            } else {
                layout.removeView(removeFromFavourites);
                layout.addView(addToFavourites);
            }

            AlertMessage.show(getActivity(), message);
            }

        });

        layout.addView(recipe.isFavourite() ? removeFromFavourites : addToFavourites);
    }

    private View getRecipeDetailsView(LayoutInflater inflater, String text,
            String imageName) {
        View view = inflater.inflate(R.layout.small_image_text_layout, null);

        TextView textView = (TextView) view
                .findViewById(R.id.recipeListTextView);
        textView.setText(text);

        ImageView imageView = (ImageView) view
                .findViewById(R.id.recipeListImageView);
        Drawable drawable = ResourcesUtils.getDrawable(getActivity(), imageName);
        imageView.setImageDrawable(drawable);

        return view;
    }
}
