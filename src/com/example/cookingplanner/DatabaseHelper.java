package com.example.cookingplanner;

import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TABLE_RECIPES = "RECIPES";
	private static final String TABLE_INGREDIENTS = "INGREDIENTS";
	private static final String TABLE_UNITS = "UNITS";
	private static final String TABLE_CONTENTS = "CONTENTS";
	private static final String DATABASE_NAME = "COOKINGDB";
	
	private static final String CREATE_RECIPES = "create table " + 
			TABLE_RECIPES + " (_id integer primary key autoincrement, "
            + "name text not null, "
            + "description text not null, "
            + "time integer not null, "
            + "image blob"
            + ");";
	
	private static final String CREATE_INGREDIENTS = "create table " + 
			TABLE_INGREDIENTS + "(_id integer primary key autoincrement, "
            + "name text not null"
            +");";
	private static final String CREATE_UNITS = "create table " + 
			TABLE_UNITS + "(_id integer primary key autoincrement, "
            + "name text not null"
            +");";
	
	private static final String CREATE_CONTENTS = "create table " + 
			TABLE_CONTENTS + "(_id integer primary key autoincrement, "
            + "recipe_id integer not null, "
            + "ingredient_id integer not null, "
            + "amount integer not null, "
            + "unit_id integer not null, "
            + "FOREIGN KEY(recipe_id) REFERENCES " + TABLE_RECIPES + "(_id), "
            + "FOREIGN KEY(unit_id) REFERENCES " + TABLE_UNITS + "(_id), "
            + "FOREIGN KEY(ingredient_id) REFERENCES " + TABLE_INGREDIENTS + "(_id)"
            + ");";


    private static final int DATABASE_VERSION = 1;    
	
	DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
    public void onCreate(SQLiteDatabase db) {
		System.out.println("In on create tables");
		db.execSQL(CREATE_RECIPES);
		db.execSQL(CREATE_INGREDIENTS);
		db.execSQL(CREATE_UNITS);
		db.execSQL(CREATE_CONTENTS);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENTS);
 
        // create new tables
        onCreate(db);
	}
	
	public long createRecipe(Recipe recipe, Content content) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		recipe.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		values.put("image", byteArray);
	    values.put("name", recipe.getName());
	    values.put("description", recipe.getDescription());
	    values.put("time", recipe.getTime());

	    long recipe_id = db.insert(TABLE_RECIPES, null, values);
	    
	    createContent(content, recipe_id);
	 
	    return recipe_id;
	}
	
	public Recipe[] getRecipeWithNecessaryContent (Content content) {
		
		return null;
	}
	
	public Recipe[] getRecipeWithSufficientContent (Content content) {
		
		return null;
	}
	
	public long[] createContent(Content content, long recipe_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int contentLength = content.getIngredients().length;
		long[] contentIds = new long[contentLength];
		
		for (int i = 0; i < contentLength; i++) {
			values.put("recipe_id", recipe_id);
			values.put("ingredients_id", content.getIngredients()[i]);
			values.put("units_id", content.getUnits()[i]);
			values.put("amount", content.getAmounts()[i]);
			contentIds[i] = db.insert(TABLE_CONTENTS, null, values);
		}
		return contentIds;
	}
	
	public long createUnit(String unit) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("name", unit);

	    long unit_id = db.insert(TABLE_UNITS, null, values);
	 
	    return unit_id;
	}
	
	public String getUnit(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_UNITS, new String[] { "name",
	            }, "id" + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    String unit = new String(cursor.getString(0));
	    // return contact
	    return unit;
	}
	
	public long createIngredient(String ingredient) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("name", ingredient);

	    long ingredient_id = db.insert(TABLE_INGREDIENTS, null, values);
	 
	    return ingredient_id;
	}

}
