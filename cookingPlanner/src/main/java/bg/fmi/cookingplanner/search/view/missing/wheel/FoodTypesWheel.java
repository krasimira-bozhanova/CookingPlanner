package bg.fmi.cookingplanner.search.view.missing.wheel;

import android.app.Activity;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelScrollListener;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.tables.FoodTypeData;
import bg.fmi.cookingplanner.model.FoodType;

/**
 * Created by krasimira on 15-7-27.
 */
public class FoodTypesWheel {

    private final FoodTypesAdapter foodTypesAdapter;
    private final AbstractWheel typesView;

    public FoodTypesWheel(Activity context, OnWheelScrollListener scrollListener) {
        this.typesView = (AbstractWheel) context.findViewById(R.id.foodTypeViewSearch);
        typesView.setVisibleItems(5);
        typesView.setCurrentItem(3);
        foodTypesAdapter = new FoodTypesAdapter(context, FoodTypeData.getInstance().getAllTypes());
        typesView.setViewAdapter(foodTypesAdapter);
        typesView.addScrollingListener(scrollListener);
    }

    public FoodType getCurrentType() {
        return foodTypesAdapter.getItem(typesView.getCurrentItem());
    }
}
