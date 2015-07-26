package bg.fmi.cookingplanner.data.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.models.Image;
import bg.fmi.cookingplanner.models.Model;

public class ImageData extends Data {

    private static ImageData instance;

    private ImageData() {

    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS "
                + getTableName() + "(_id integer primary key autoincrement, "
                + "name text not null,"
                + "recipe_id integer not null, "
                + "FOREIGN KEY(recipe_id) REFERENCES "
                + RecipeData.getInstance().getTableName()
                + "(_id) " + ");";
    }

    @Override
    public String getTableName() {
        return "IMAGES";
    }

    public static ImageData getInstance() {
        if (instance == null) {
            instance = new ImageData();
        }
        return instance;
    }

    public void createImages(List<Image> images, long recipeId) {
        for (Image image: images) {
            ContentValues values = new ContentValues();
            values.put("name", image.getName());
            values.put("recipe_id", recipeId);

            database.insert(getTableName(), null, values);
        }
    }

    public List<Image> getImagesWithRecipe(long recipeId) {
        Cursor cursor = database.rawQuery(
                "select * from " + getTableName() + " where recipe_id=" + recipeId, null);
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

    @Override
    public <T extends Model> Class<T> getModel() {
        return (Class<T>) Image.class;
    }
}
