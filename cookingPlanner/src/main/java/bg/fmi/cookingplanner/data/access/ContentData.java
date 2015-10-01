package bg.fmi.cookingplanner.data.access;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.fmi.cookingplanner.data.model.Content;
import bg.fmi.cookingplanner.data.model.Content.ContentUnit;
import bg.fmi.cookingplanner.data.model.FoodType;
import bg.fmi.cookingplanner.data.model.Ingredient;
import bg.fmi.cookingplanner.data.model.Measurement;
import bg.fmi.cookingplanner.data.model.Model;

public class ContentData extends Data<Content> {

    private static ContentData instance;
    private static final String TABLE_NAME = "CONTENTS";


    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(_id integer primary key autoincrement, "
                + "recipe_id integer not null, "
                + "ingredient_id integer not null, " + "amount integer, "
                + "measurement_id integer, " + "description text, "
                + "FOREIGN KEY(recipe_id) REFERENCES "
                + RecipeData.getInstance().getTableName() + "(_id), "
                + "FOREIGN KEY(measurement_id) REFERENCES "
                + MeasurementData.getInstance().getTableName() + "(_id), "
                + "FOREIGN KEY(ingredient_id) REFERENCES "
                + IngredientData.getInstance().getTableName() + "(_id)" + ");";
    }

    @Override
    public String getTableName() {
        return "CONTENTS";
    }

    public static ContentData getInstance() {
        if (instance == null) {
            instance = new ContentData();
        }
        return instance;
    }

    @Override
    public Class<Content> getModel() {
        return Content.class;
    }

    public Content getContentWithRecipe(long recipeId) {
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME
                + " where recipe_id=" + recipeId, null);
        cursor.moveToFirst();
        List<Content.ContentUnit> contentUnits = new ArrayList<Content.ContentUnit>();

        Content content = new Content();
        // get all the ingredients and the measurements for this recipe content
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            long ingredientId = cursor.getLong(cursor
                    .getColumnIndex("ingredient_id"));
            Ingredient ingredient = IngredientData.getInstance().getIngredient(
                    ingredientId);
            Measurement measurement = null;
            if (!cursor.isNull(cursor.getColumnIndex("measurement_id"))) {
                long measurementId = cursor.getLong(cursor
                        .getColumnIndex("measurement_id"));
                measurement = MeasurementData.getInstance().getMeasurement(
                        measurementId);
            }

            String description = null;

            if (!cursor.isNull(cursor.getColumnIndex("description"))) {
                description = cursor.getString(cursor
                        .getColumnIndex("description"));
            }

            double amount = -1;
            if (!cursor.isNull(cursor.getColumnIndex("amount"))) {
                amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            }

            Content.ContentUnit contentUnit = new Content.ContentUnit(id,
                    ingredient, measurement, amount, description);
            contentUnits.add(contentUnit);
            cursor.moveToNext();
        }
        content.setContentUnits(contentUnits);
        return content;
    }

    public long[] createContent(Content content, long recipe_id) {
        ContentValues values = new ContentValues();
        int contentLength = content.getContentUnits().size();
        long[] contentIds = new long[contentLength];
        List<Content.ContentUnit> contentUnits = content.getContentUnits();

        for (int i = 0; i < contentLength; i++) {
            Content.ContentUnit currentContentUnit = contentUnits.get(i);
            values.put("recipe_id", recipe_id);
            long ingredientId = IngredientData.getInstance()
                    .getIngredient(currentContentUnit.getIngredient())
                    .getId();
            values.put("ingredient_id", ingredientId);
            IngredientData.getInstance().increaseRating(ingredientId);
            if (currentContentUnit.getMeasurement() != null) {
                long measurementId = MeasurementData.getInstance()
                        .getMeasurementId(currentContentUnit.getMeasurement());
                values.put("measurement_id", measurementId);
            }
            values.put("description", currentContentUnit.getDescription());

            if (currentContentUnit.getAmount() != -1) {
                values.put("amount", currentContentUnit.getAmount());
            }

            contentIds[i] = database.insert(TABLE_NAME, null, values);
        }
        return contentIds;
    }


    public Map<FoodType, List<ContentUnit>> getContentForTypes(Content content) {
        Map<FoodType, List<ContentUnit>> contentForTypes = new HashMap<FoodType, List<ContentUnit>>();
        Map<String, FoodType> stringToFoodType = new HashMap<String, FoodType>();
        for (ContentUnit contentUnit: content.getContentUnits()) {
            FoodType type = contentUnit.getIngredient().getType();
            if (!stringToFoodType.containsKey(type.getName())) {
                stringToFoodType.put(type.getName(), type);
            } else {
                type = stringToFoodType.get(type.getName());
            }
            if (!contentForTypes.containsKey(type)) {
                contentForTypes.put(type, new ArrayList<ContentUnit>());
            }
            contentForTypes.get(type).add(contentUnit);
        }
        return contentForTypes;
    }

}