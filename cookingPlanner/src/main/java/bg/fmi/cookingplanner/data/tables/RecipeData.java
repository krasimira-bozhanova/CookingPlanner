package bg.fmi.cookingplanner.data.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.Content;
import bg.fmi.cookingplanner.model.Description;
import bg.fmi.cookingplanner.model.Image;
import bg.fmi.cookingplanner.model.Ingredient;
import bg.fmi.cookingplanner.model.MealType;
import bg.fmi.cookingplanner.model.Model;
import bg.fmi.cookingplanner.model.Recipe;

public class RecipeData extends Data {

    private static RecipeData instance;

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName()
                + " (_id integer primary key autoincrement, "
                + "name text not null, " + "type_id integer not null, "
                + "time integer not null, " + "servings integer, "
                + "is_favourite integer, " + "FOREIGN KEY(type_id) REFERENCES "
                + MealTypeData.getInstance().getTableName() + "(_id) " + ");";
    }

    @Override
    public String getTableName() {
        return "RECIPES";
    }

    @Override
    public boolean isParsable() {
        return true;
    }

    public static RecipeData getInstance() {
        if (instance == null) {
            instance = new RecipeData();
        }
        return instance;
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model recipe : objects) {
            createRecipe((Recipe) recipe);
        }
    }

    public long createRecipe(Recipe recipe) {

        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("time", recipe.getTime());
        long typeId = MealTypeData.getInstance().getTypeId(recipe.getMealType());
        values.put("type_id", typeId);
        values.put("servings", recipe.getServings());
        values.put("is_favourite", 0);

        long recipe_id = database.insert(getTableName(), null, values);
        ImageData.getInstance().createImages(recipe.getImages(), recipe_id);
        ContentData.getInstance().createContent(recipe.getContent(), recipe_id);
        DescriptionData.getInstance().createDescription(
                recipe.getDescription(), recipe_id);

        return recipe_id;
    }

    public List<Recipe> getRecipesIdsWithIngredients(
            List<Ingredient> ingredients) {
        StringBuilder listIds = new StringBuilder("(");
        String prefix = "";
        for (Ingredient ingredient : ingredients) {
            listIds.append(prefix);
            prefix = ",";
            listIds.append(String.valueOf(ingredient.getId()));
        }
        listIds.append(")");
        // Get all recipes with ingredients in the given list
        String query = "select * from "
                + RecipeData.getInstance().getTableName() + " where _id in "
                + "(select recipe_id as recipeid from "
                + ContentData.getInstance().getTableName()
                + " where ingredient_id in " + listIds.toString()
                + " group by recipe_id"
                + " having count(*)=(select count(c._id) from "
                + ContentData.getInstance().getTableName() + " c "
                + " where c.recipe_id=recipeid))";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        List<Recipe> recipesResult = new ArrayList<Recipe>();
        while (!cursor.isAfterLast()) {
            recipesResult.add(constructRecipefFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return recipesResult;
    }

    public Recipe getRecipeWithId(long id) {
        Cursor cursor = database.rawQuery("select * from " + getTableName()
                + " where _id=" + id, null);

        Recipe recipe = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            recipe = constructRecipefFromCursor(cursor);
        }
        cursor.close();
        return recipe;
    }

    public List<Recipe> getFavouriteRecipes() {
        Cursor cursor = database.rawQuery("select * from " + getTableName()
                + " where is_favourite=1 ", null);
        List<Recipe> recipesResult = new ArrayList<Recipe>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe newRecipe = constructRecipefFromCursor(cursor);
            recipesResult.add(newRecipe);
            cursor.moveToNext();
        }
        cursor.close();
        return recipesResult;
    }

    public List<Recipe> getAllRecipes() {
        Cursor cursor = database.rawQuery("select * from " + getTableName(),
                null);
        List<Recipe> recipesResult = new ArrayList<Recipe>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe newRecipe = constructRecipefFromCursor(cursor);
            recipesResult.add(newRecipe);
            cursor.moveToNext();
        }
        cursor.close();
        return recipesResult;
    }

    private Recipe constructRecipefFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        long id = cursor.getLong(cursor.getColumnIndex("_id"));

        int time = cursor.getInt(cursor.getColumnIndex("time"));
        long typeId = cursor.getInt(cursor.getColumnIndex("type_id"));
        MealType mealType = MealTypeData.getInstance().getType(typeId);
        Content content = ContentData.getInstance().getContentWithRecipe(id);
        Description description = DescriptionData.getInstance()
                .getDescriptionWithRecipe(id);
        List<Image> images = ImageData.getInstance().getImagesWithRecipe(id);
        int servings = cursor.getInt(cursor.getColumnIndex("servings"));
        boolean isFavourite = cursor.getInt(cursor
                .getColumnIndex("is_favourite")) == 1 ? true : false;
        return new Recipe(id, name, description, content, time, images, mealType,
                servings, isFavourite);
    }

    public int addToFavourites(long recipeId) {
        String strFilter = "_id=" + recipeId;
        ContentValues args = new ContentValues();
        args.put("is_favourite", 1);
        return database.update(getTableName(), args, strFilter, null);
    }

    public int removeFromFavourites(long recipeId) {
        String strFilter = "_id=" + recipeId;
        ContentValues args = new ContentValues();
        args.put("is_favourite", 0);
        return database.update(getTableName(), args, strFilter, null);
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        return (Class<T>) Recipe.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.recipes;
    }
}
