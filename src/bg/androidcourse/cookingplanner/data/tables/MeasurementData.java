package bg.androidcourse.cookingplanner.data.tables;


import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import bg.androidcourse.cookingplanner.R;
import bg.androidcourse.cookingplanner.models.Measurement;
import bg.androidcourse.cookingplanner.models.Model;

public class MeasurementData extends Data {

    private static MeasurementData instance;

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName()
                + "(_id integer primary key autoincrement, "
                + "name text not null"
                + ");";
    }

    @Override
    public String getTableName() {
        return "MEASUREMENTS";
    }

    @Override
    public boolean isParsable() {
        return true;
    }

    @Override
    public <T extends Model> Class<T> getModel() {
        // TODO Auto-generated method stub
        return (Class<T>) Measurement.class;
    }

    @Override
    public int getResourceJson() {
        return R.raw.measurements;
    }


    public static MeasurementData getInstance() {
        if (instance == null) {
            instance = new MeasurementData();
        }
        return instance;
    }

    @Override
    public void createObjects(List<Model> objects) {
        for (Model measurement: objects) {
            createMeasurement((Measurement) measurement);
        }
    }

    public long createMeasurement(Measurement measurement) {
        ContentValues values = new ContentValues();
        values.put("name", measurement.getName());
        long measurementId = database.insert(getTableName(), null, values);
        return measurementId;
    }

    public Measurement getMeasurement(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + getTableName()
                + " where _id=" + id, null);
        if (cursor != null)
            cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex("name"));
        Measurement measurement = new Measurement(id, name);
        cursor.close();
        return measurement;
    }

    public long getMeasurementId(Measurement measurement) {
        Cursor cursor = database.rawQuery(
                "select _id from "
                + getTableName()
                + " where name='"
                + measurement.getName() + "'", null);
        cursor.moveToFirst();
        long id = 0;
        try {
            id = cursor.getLong(cursor.getColumnIndex("_id"));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        cursor.close();
        return id;
    }
}
