package bg.fmi.cookingplanner.data.access;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.model.FoodType;
import bg.fmi.cookingplanner.data.model.Model;

public class FoodTypeData extends Data {

    private static FoodTypeData instance;
    private static final String TABLE_NAME = "FOODTYPES";

    private FoodTypeData() {

    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(_id integer primary key autoincrement, "
                + "name text not null, "
                + "image_name text not null "
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

    public static FoodTypeData getInstance() {
        if (instance == null) {
            instance = new FoodTypeData();
        }
        return instance;
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        // TODO Auto-generated method stub
        return (Class<T>) FoodType.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.food_types;
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model type: objects) {
            createFoodType((FoodType) type);
        }
    }

    public FoodType getFoodType(FoodType foodType) {
        String name = foodType.getName();
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME
                + " where name='"
                + name + "'", null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String imageName = cursor.getString(cursor.getColumnIndex("image_name"));
        cursor.close();
        return new FoodType(id, name, imageName);
    }

    public FoodType getFoodTypeWithId(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME
                + " where _id='"
                + id + "'", null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String imageName = cursor.getString(cursor.getColumnIndex("image_name"));
        cursor.close();
        return new FoodType(id, name, imageName);
    }

    public List<FoodType> getAllTypes() {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME, null);
        List<FoodType> typesResult = new ArrayList<FoodType>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String imageName = cursor.getString(cursor.getColumnIndex("image_name"));
            FoodType newType = new FoodType(id, name, imageName);
            typesResult.add(newType);
            cursor.moveToNext();
        }
        cursor.close();
        return typesResult;
    }

    public long createFoodType(FoodType type) {
        ContentValues values = new ContentValues();
        values.put("name", type.getName());
        values.put("image_name", type.getImageName());
        long typeId = database.insert(TABLE_NAME, null, values);
        return typeId;
    }



}
