package bg.fmi.cookingplanner.data.access;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.model.MealType;
import bg.fmi.cookingplanner.data.model.Model;

public class MealTypeData extends Data<MealType> {
    private static MealTypeData instance;
    private static final String TABLE_NAME = "MEALTYPES";


    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "(_id integer primary key autoincrement, "
                + "name text unique not null" + ");";
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean isParsable() {
        return true;
    }

    public static MealTypeData getInstance() {
        if (instance == null) {
            instance = new MealTypeData();
        }
        return instance;
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model type: objects) {
            createType((MealType) type);
        }
    }

    @Override
    public Class<MealType> getModel() {
        // TODO Auto-generated method stub
        return MealType.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.meal_types;
    }

    public long createType(MealType mealType) {
        ContentValues values = new ContentValues();
        values.put("name", mealType.getName());
        return database.insert(TABLE_NAME, null, values);
    }

    public MealType getType(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME , null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return new MealType(id, name);
    }

    public List<MealType> getAllTypes() {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME, null);
        cursor.moveToFirst();
        List<MealType> typesResult = new ArrayList<MealType>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            MealType newMealType = new MealType(id, name);
            typesResult.add(newMealType);
            cursor.moveToNext();
        }
        cursor.close();
        return typesResult;
    }

    public long getTypeId(MealType mealType) {
        Cursor cursor = database.rawQuery(
                "select _id from "
                + TABLE_NAME
                + " where name='"
                + mealType.getName() + "'", null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    public MealType getTypeWithName(String name) {
        Cursor cursor = database.rawQuery(
                "select _id from "
                        + TABLE_NAME
                        + " where name='"
                        + name + "'" , null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return new MealType(id, name);
    }
}
