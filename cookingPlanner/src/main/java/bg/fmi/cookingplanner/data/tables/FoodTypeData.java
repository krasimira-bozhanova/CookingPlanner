package bg.fmi.cookingplanner.data.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.model.FoodType;
import bg.fmi.cookingplanner.model.Model;

public class FoodTypeData extends Data {

    private static FoodTypeData instance;

    private FoodTypeData() {

    }

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName()
                + "(_id integer primary key autoincrement, "
                + "name text not null, "
                + "image_name text not null "
                + ");";
    }

    @Override
    public String getTableName() {
        return "FOODTYPE";
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

    public FoodType getFoodTypeWithName(String name) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + getTableName()
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
                + getTableName()
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
                + getTableName(), null);
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

    @Override
    public void createObjects(List<Model> objects) {
        for (Model type: objects) {
            createFoodType((FoodType) type);
        }
    }

    public long createFoodType(FoodType type) {
        ContentValues values = new ContentValues();
        values.put("name", type.getName());
        values.put("image_name", type.getImageName());
        long typeId = database.insert(getTableName(), null, values);
        return typeId;
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

}
