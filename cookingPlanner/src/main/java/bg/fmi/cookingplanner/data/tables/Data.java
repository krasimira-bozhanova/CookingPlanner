package bg.fmi.cookingplanner.data.tables;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import bg.fmi.cookingplanner.model.Model;

public abstract class Data {

    protected static SQLiteDatabase database;

    public void createTable(SQLiteDatabase db) {
        db.execSQL(getCreateTableStatement());
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());
    }

    public abstract String getCreateTableStatement();
    public abstract String getTableName();
    public boolean isParsable() {
        return false;
    }

    public void createObjects(List<Model> objects) {
    }

    public int getResourceJson() {
        throw new UnsupportedOperationException();
    }

    public abstract <T extends Model> Class<T> getModel();

    public static void setDatabase(SQLiteDatabase db) {
        database = db;
    }
}
