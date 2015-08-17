package bg.fmi.cookingplanner.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.data.model.Image;
import bg.fmi.cookingplanner.data.model.Model;

public class ImageData extends Data {

    private static ImageData instance;
    private static final String TABLE_NAME = "IMAGES";

    private ImageData() {

    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "(_id integer primary key autoincrement, "
                + "name text not null,"
                + "recipe_id integer not null, "
                + "FOREIGN KEY(recipe_id) REFERENCES "
                + RecipeData.getInstance().getTableName()
                + "(_id) " + ");";
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ImageData getInstance() {
        if (instance == null) {
            instance = new ImageData();
        }
        return instance;
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        return (Class<T>) Image.class;
    }

    public void createImages(List<Image> images, long recipeId) {
        for (Image image: images) {
            ContentValues values = new ContentValues();
            values.put("name", image.getName());
            values.put("recipe_id", recipeId);

            database.insert(TABLE_NAME, null, values);
        }
    }

    public List<Image> getImagesWithRecipe(long recipeId) {
        Cursor cursor = database.rawQuery(
                "select * from " + TABLE_NAME + " where recipe_id=" + recipeId, null);
        cursor.moveToFirst();
        List<Image> images = new ArrayList<Image>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            images.add(new Image(id, name));
            cursor.moveToNext();
        }
        cursor.close();
        return images;
    }

}
