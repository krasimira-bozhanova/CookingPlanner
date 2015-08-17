package bg.fmi.cookingplanner.results;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.model.Image;
import bg.fmi.cookingplanner.recipe.RecipeActivity;
import bg.fmi.cookingplanner.data.model.Recipe;
import bg.fmi.cookingplanner.util.ResourcesUtils;

public class ResultsListAdapter extends BaseAdapter {

    private List<Recipe> recipeList;

    public ResultsListAdapter(List<Recipe> recipes) {
        this.recipeList = recipes;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recipeList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView,
            final ViewGroup parent) {
        ViewGroup recipeListView = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            recipeListView = (ViewGroup) LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.list_recipe, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) recipeListView
                    .findViewById(R.id.recipeListImageView);
            viewHolder.textView = (TextView) recipeListView
                    .findViewById(R.id.recipeListTextView);
            recipeListView.setTag(viewHolder);
        } else {
            recipeListView = (ViewGroup) convertView;
            viewHolder = (ViewHolder) recipeListView.getTag();
        }

        final Recipe currentRecipe = (Recipe) getItem(position);
        final Context context = parent.getContext();
        List<Image> recipeImages = currentRecipe.getImages();

        if (recipeImages.size() > 0) {
            String drawableName = recipeImages.get(0).getName();
            Drawable drawable = ResourcesUtils.getDrawable(parent.getContext(), drawableName);
            viewHolder.imageView.setImageDrawable(drawable);
        }

        viewHolder.textView.setText(currentRecipe.getName());
        recipeListView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        RecipeActivity.class);
                Bundle argumentsForActivity = new Bundle();
                argumentsForActivity.putSerializable("recipe",
                        currentRecipe);
                intent.putExtras(argumentsForActivity);
                context.startActivity(intent);
            }
        });
        return recipeListView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

}
