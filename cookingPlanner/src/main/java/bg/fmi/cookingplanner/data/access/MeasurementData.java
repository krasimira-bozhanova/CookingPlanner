package bg.fmi.cookingplanner.data.access;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import java.util.List;

import bg.fmi.cookingplanner.R;
import bg.fmi.cookingplanner.data.model.Measurement;
import bg.fmi.cookingplanner.data.model.Model;

public class MeasurementData extends Data<Measurement> {

    private static MeasurementData instance;
    private static final String TABLE_NAME = "MEASUREMENTS";

    @Override
    public String getCreateTableStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(_id integer primary key autoincrement, "
                + "name text not null"
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

    @Override
    public Class<Measurement> getModel() {
        // TODO Auto-generated method stub
        return Measurement.class;
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
        long measurementId = database.insert(TABLE_NAME, null, values);
        return measurementId;
    }

    public Measurement getMeasurement(long id) {
        Cursor cursor = database.rawQuery(
                "select * from "
                + TABLE_NAME
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
                + TABLE_NAME
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
