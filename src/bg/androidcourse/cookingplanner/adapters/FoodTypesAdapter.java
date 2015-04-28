package bg.androidcourse.cookingplanner.adapters;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.models.FoodType;


public class FoodTypesAdapter extends AbstractWheelTextAdapter {
    // Countries names
    private List<FoodType> foodTypes;

    /**
     * Constructor
     */
    public FoodTypesAdapter(Context context, List<FoodType> foodTypes) {
        super(context, R.layout.small_image_text_layout, NO_RESOURCE);
        this.foodTypes = foodTypes;
    }

    @Override
    public View getItem(int position, View cachedView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ViewGroup foodTypesView = null;
        if (cachedView == null) {
            foodTypesView = (ViewGroup) LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.small_image_text_layout,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) foodTypesView
                    .findViewById(R.id.recipeListImageView);
            viewHolder.textView = (TextView) foodTypesView
                    .findViewById(R.id.recipeListTextView);
            foodTypesView.setTag(viewHolder);
        } else {
            foodTypesView = (ViewGroup) cachedView;
            viewHolder = (ViewHolder) foodTypesView.getTag();
        }

        viewHolder.textView.setText(getItemText(position));

        Resources res = parent.getContext().getResources();
        String mDrawableName = foodTypes.get(position).getImageName();

        int resourceId = res.getIdentifier(mDrawableName, "drawable",
                parent.getContext().getPackageName());

        Drawable drawable = res.getDrawable(resourceId);
        viewHolder.imageView.setImageDrawable(drawable);

        return foodTypesView;
    }

    @Override
    public int getItemsCount() {
        return foodTypes.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        return foodTypes.get(index).getName();
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
