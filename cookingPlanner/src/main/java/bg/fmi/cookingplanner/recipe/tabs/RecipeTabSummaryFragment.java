package bg.fmi.cookingplanner.recipe.tabs;

import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.tables.RecipeData;
import bg.fmi.cookingplanner.models.Image;
import bg.fmi.cookingplanner.models.Recipe;

public class RecipeTabSummaryFragment extends Fragment {

    private Recipe recipe;

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

    public Recipe getRecipe() {
        if (this.recipe == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            this.recipe = (Recipe) extras.getSerializable("recipe");
        }
        return this.recipe;
    }

    class ImageViewAdapter extends ArrayAdapter<Image> {

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

            Resources res = parent.getContext().getResources();
            String mDrawableName = getItem(position).getName();

            int resourceId = res.getIdentifier(mDrawableName, "drawable",
                    parent.getContext().getPackageName());

            Drawable drawable = res.getDrawable(resourceId);
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
                int result = RecipeData.getInstance().addToFavourites(
                        recipe.getId());
                String message = "The recipe is succesfully added to favourites";
                if (result == 0) {
                    message = "There is a problem with adding the recipe to favourites";
                } else {
                    ViewGroup parent = layout;
                    parent.removeView(addToFavourites);
                    parent.addView(removeFromFavourites);
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setTitle("CookingPlanner");
                alertDialogBuilder
                        .setMessage(message)
                        .setCancelable(false)
                        .setNeutralButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });

        removeFromFavourites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int result = RecipeData.getInstance().removeFromFavourites(recipe.getId());
                String message = "The recipe is succesfully removed from favourites";
                if (result == 0) {
                    message = "There is a problem with removing the recipe from favourites";
                } else {
                        ViewGroup parent = layout;
                        parent.removeView(removeFromFavourites);
                        parent.addView(addToFavourites);
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setTitle("CookingPlanner");
                alertDialogBuilder
                        .setMessage(message)
                        .setCancelable(false)
                        .setNeutralButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });


        layout.addView(recipe.isFavourite() ? removeFromFavourites : addToFavourites);

    }

    private View getRecipeDetailsView(LayoutInflater inflater, String text,
            String imageName) {
        View view = inflater.inflate(R.layout.small_image_text_layout, null);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.recipeListImageView);
        TextView textView = (TextView) view
                .findViewById(R.id.recipeListTextView);

        textView.setText(text);

        String mDrawableName = imageName;

        int resourceId = getResources().getIdentifier(mDrawableName,
                "drawable", getActivity().getPackageName());

        Drawable drawable = getResources().getDrawable(resourceId);
        imageView.setImageDrawable(drawable);
        return view;
    }
}
