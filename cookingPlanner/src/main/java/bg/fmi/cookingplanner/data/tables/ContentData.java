package bg.fmi.cookingplanner.data.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.models.Content;
import bg.fmi.cookingplanner.models.Content.ContentUnit;
import bg.fmi.cookingplanner.models.FoodType;
import bg.fmi.cookingplanner.models.Ingredient;
import bg.fmi.cookingplanner.models.Measurement;
import bg.fmi.cookingplanner.models.Model;

public class ContentData extends Data {

    private static ContentData instance;

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName()
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

    public Content getContentWithRecipe(long recipeId) {
        Cursor cursor = database.rawQuery("select * from " + getTableName()
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
        int arraySize = contentUnits.size();
        Content.ContentUnit[] contentUnitsArray = contentUnits
                .toArray(new Content.ContentUnit[arraySize]);
        content.setContentUnits(contentUnitsArray);
        return content;
    }

    public long[] createContent(Content content, long recipe_id) {
        ContentValues values = new ContentValues();
        int contentLength = content.getContentUnits().length;
        long[] contentIds = new long[contentLength];
        Content.ContentUnit[] contentUnits = content.getContentUnits();

        for (int i = 0; i < contentLength; i++) {
            values.put("recipe_id", recipe_id);
            long ingredientId = IngredientData.getInstance()
                    .getIngredient(contentUnits[i].getIngredient().getName())
                    .getId();
            values.put("ingredient_id", ingredientId);
            IngredientData.getInstance().increaseRating(ingredientId);
            if (contentUnits[i].getMeasurement() != null) {
                long measurementId = MeasurementData.getInstance()
                        .getMeasurementId(contentUnits[i].getMeasurement());
                values.put("measurement_id", measurementId);
            }
            values.put("description", contentUnits[i].getDescription());

            if (contentUnits[i].getAmount() != -1) {
                values.put("amount", contentUnits[i].getAmount());
            }

            contentIds[i] = database.insert(getTableName(), null, values);
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

    @Override
    public <T extends Model> Class<T> getModel() {
        return (Class<T>) Content.class;
    }

}