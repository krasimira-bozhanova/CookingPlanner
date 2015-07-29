package bg.fmi.cookingplanner.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by krasimira on 15-7-29.
 */
public class ResourcesUtils {

    public static Drawable getDrawable(Context context, String drawableName) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(drawableName, "drawable",
                context.getPackageName());
        return res.getDrawable(resourceId);
    }
}
