package bg.fmi.cookingplanner.data.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.models.MealType;
import bg.fmi.cookingplanner.models.Model;

public class MealTypeData extends Data {
    private static MealTypeData instance;

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS "
                + getTableName() + "(_id integer primary key autoincrement, "
                + "name text not null" + ");";
    }

    @Override
    public String getTableName() {
        return "TYPES";
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

    public MealType getTypeWithName(String name) {
        Cursor cursor = database.rawQuery(
                "select _id from "
                + getTableName()
                + " where name='"
                + name + "'" , null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return new MealType(id, name);
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model type: objects) {
            createType((MealType) type);
        }
    }

    public long createType(MealType mealType) {
        ContentValues values = new ContentValues();
        values.put("name", mealType.getName());
        return database.insert(getTableName(), null, values);
    }

    public MealType getType(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + getTableName() , null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return new MealType(id, name);
    }

    public List<MealType> getAllTypes() {
        Cursor cursor = database.rawQuery(
                "select * from "
                + getTableName(), null);
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
                + getTableName()
                + " where name='"
                + mealType.getName() + "'", null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        // TODO Auto-generated method stub
        return (Class<T>) MealType.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.meal_types;
    }
}
