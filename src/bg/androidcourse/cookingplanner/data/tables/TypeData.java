package bg.androidcourse.cookingplanner.data.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.models.Model;
import bg.androidcourse.cookingplanner.models.Type;

public class TypeData  extends Data {
    private static TypeData instance;

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

    public static TypeData getInstance() {
        if (instance == null) {
            instance = new TypeData();
        }
        return instance;
    }

    public Type getTypeWithName(String name) {
        Cursor cursor = database.rawQuery(
                "select _id from "
                + getTableName()
                + " where name='"
                + name + "'" , null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return new Type(id, name);
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model type: objects) {
            createType((Type) type);
        }
    }

    public long createType(Type type) {
        ContentValues values = new ContentValues();
        values.put("name", type.getName());
        return database.insert(getTableName(), null, values);
    }

    public Type getType(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + getTableName() , null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        return new Type(id, name);
    }

    public List<Type> getAllTypes() {
        Cursor cursor = database.rawQuery(
                "select * from "
                + getTableName(), null);
        cursor.moveToFirst();
        List<Type> typesResult = new ArrayList<Type>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            Type newType = new Type(id, name);
            typesResult.add(newType);
            cursor.moveToNext();
        }
        cursor.close();
        return typesResult;
    }

    public long getTypeId(Type type) {
        Cursor cursor = database.rawQuery(
                "select _id from "
                + getTableName()
                + " where name='"
                + type.getName() + "'", null);
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        cursor.close();
        return id;
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        // TODO Auto-generated method stub
        return (Class<T>) Type.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.types;
    }
}
