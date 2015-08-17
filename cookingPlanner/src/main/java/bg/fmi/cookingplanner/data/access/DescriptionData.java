package bg.fmi.cookingplanner.data.access;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.data.model.Description;
import bg.fmi.cookingplanner.data.model.Model;

public class DescriptionData extends Data {

    private static DescriptionData instance;
    private static final String TABLE_NAME = "DESCRIPTIONS";

    private DescriptionData() {

    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "(_id integer primary key autoincrement, "
                + "stage text not null,"
                + "recipe_id integer not null, "
                + "FOREIGN KEY(recipe_id) REFERENCES "
                + RecipeData.getInstance().getTableName()
                + "(_id) " + ");";
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        // TODO Auto-generated method stub
        return (Class<T>) Description.class;
    }

    public static DescriptionData getInstance() {
        if (instance == null) {
            instance = new DescriptionData();
        }
        return instance;
    }

    public void createDescription(Description description, long recipeId) {
        for (String stage: description.getStages()) {
            ContentValues values = new ContentValues();
            values.put("stage", stage);
            values.put("recipe_id", recipeId);
            database.insert(TABLE_NAME, null, values);
        }
    }

    public Description getDescriptionWithRecipe(long recipeId) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME
                + " where recipe_id="
                + recipeId
                + " order by _id asc", null);
        cursor.moveToFirst();
        String[] stages = new String[cursor.getCount()];
        int index = 0;
        while(!cursor.isAfterLast()) {
            String stage = cursor.getString(cursor.getColumnIndex("stage"));
            stages[index++] = stage;
            cursor.moveToNext();
        }
        cursor.close();
        return new Description(stages);
    }



}
