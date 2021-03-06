package bg.fmi.cookingplanner.data.access;


import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.model.FoodType;
import bg.fmi.cookingplanner.data.model.Ingredient;
import bg.fmi.cookingplanner.data.model.Model;

public class IngredientData extends Data<Ingredient> {

    private static IngredientData instance;

    private static final String TABLE_NAME = "INGREDIENTS";

    private IngredientData() {
    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "(_id integer primary key autoincrement, "
                + "name text not null, "
                + "rating int not null, "
                + "type_id int not null, "
                + "FOREIGN KEY(type_id) REFERENCES " + FoodTypeData.getInstance().getTableName() + "(_id) "
                + ");";
    }
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean isParsable() {
        return true;
    }

    public static IngredientData getInstance() {
        if (instance == null) {
            instance = new IngredientData();
        }
        return instance;
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model ingredient: objects) {
            createIngredient((Ingredient) ingredient);
        }
    }

    @Override
    public Class<Ingredient> getModel() {
        // TODO Auto-generated method stub
        return Ingredient.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.ingredients;
    }

    public Ingredient getIngredient(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME
                + " where _id = "
                + id, null);
        cursor.moveToFirst();
        Ingredient ingredient = null;
        if (!cursor.isAfterLast()) {
            ingredient = constructIngredientFromCursor(cursor);
        }
        cursor.close();
        return ingredient;
    }

    public long createIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put("name", ingredient.getName());
        values.put("rating", ingredient.getRating());
        long typeId = FoodTypeData.getInstance().getFoodType(
                ingredient.getType()).getId();
        values.put("type_id", typeId);
        long ingredient_id = database.insert(TABLE_NAME, null, values);
        return ingredient_id;
    }

    private int getRating(long ingredientId) {
        Cursor cursor = database.rawQuery(
                "select rating from "
                + TABLE_NAME
                + " where _id="
                + ingredientId, null);
        cursor.moveToFirst();
        int rating = cursor.getInt(cursor.getColumnIndex("rating"));
        cursor.close();
        return rating;
    }

    public List<Ingredient> getAllIngredients() {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME
                + " order by rating desc", null);
        List<Ingredient> ingredientsResult = new ArrayList<Ingredient>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Ingredient newIngredient = constructIngredientFromCursor(cursor);
            ingredientsResult.add(newIngredient);
            cursor.moveToNext();
        }
        cursor.close();
        return ingredientsResult;
    }

    public Map<String, List<Ingredient>> getIngredientsForType() {
        Map<String, List<Ingredient>> ingredientsForFoodType =
                new HashMap<String, List<Ingredient>>();
        for (Ingredient ingredient: getAllIngredients()) {
            String name = ingredient.getType().getName();
            if (!ingredientsForFoodType.containsKey(name)) {
                ingredientsForFoodType.put(name, new ArrayList<Ingredient>());
            }
            ingredientsForFoodType.get(name).add(ingredient);
        }
        return ingredientsForFoodType;
    }

    public List<Ingredient> getNextIngredientsWithHighestRating(int count) {
        Cursor cursor = database.rawQuery(
                "select * from "
                        + TABLE_NAME
                        + " order by rating desc limit "
                        + count, null);
        cursor.moveToFirst();
        List<Ingredient> ingredientsResult = new ArrayList<Ingredient>();
        while(!cursor.isAfterLast()) {
            Ingredient newIngredient = constructIngredientFromCursor(cursor);
            ingredientsResult.add(newIngredient);
            cursor.moveToNext();
        }
        cursor.close();
        return ingredientsResult;
    }

    public Ingredient getIngredient(Ingredient ingredient) {
        Cursor cursor = database.rawQuery(
                "select * from "
                        + TABLE_NAME
                        + " where name='"
                        + ingredient.getName() + "'", null);
        cursor.moveToFirst();

        Ingredient fullIngredient = constructIngredientFromCursor(cursor);
        cursor.close();
        return fullIngredient;
    }

    public void increaseRating(long ingredientId) {
        long currentRating = getRating(ingredientId);
        String strFilter = "_id=" + ingredientId;

        ContentValues args = new ContentValues();
        args.put("rating", ++currentRating);
        database.update(TABLE_NAME, args, strFilter, null);
    }

    private Ingredient constructIngredientFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        int rating = cursor.getInt(cursor.getColumnIndex("rating"));
        long typeId = cursor.getLong(cursor.getColumnIndex("type_id"));
        FoodType type = FoodTypeData.getInstance().getFoodTypeWithId(typeId);
        return new Ingredient(id, name, rating, type);
    }

}
