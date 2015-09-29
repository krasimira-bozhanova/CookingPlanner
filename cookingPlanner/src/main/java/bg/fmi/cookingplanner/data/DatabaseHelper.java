package bg.fmi.cookingplanner.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import bg.fmi.cookingplanner.data.access.ContentData;
import bg.fmi.cookingplanner.data.access.Data;
import bg.fmi.cookingplanner.data.access.DescriptionData;
import bg.fmi.cookingplanner.data.access.FoodTypeData;
import bg.fmi.cookingplanner.data.access.ImageData;
import bg.fmi.cookingplanner.data.access.IngredientData;
import bg.fmi.cookingplanner.data.access.MealTypeData;
import bg.fmi.cookingplanner.data.access.MeasurementData;
import bg.fmi.cookingplanner.data.access.RecipeData;
import bg.fmi.cookingplanner.data.model.Model;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "COOKINGDB";
    private static final int DATABASE_VERSION = 1;
    private final Data[] tablesInstances = { MeasurementData.getInstance(),
            FoodTypeData.getInstance(), IngredientData.getInstance(),
            MealTypeData.getInstance(), RecipeData.getInstance(),

            ContentData.getInstance(), ImageData.getInstance(),
            DescriptionData.getInstance() };

    private Context context;

    public DatabaseHelper(Context context, boolean initialRun) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
        Data.setDatabase(this.getWritableDatabase());
        if (initialRun) {
            parseJSONs();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Data instance : tablesInstances) {
            instance.createTable(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        for (Data instance : tablesInstances) {
            instance.dropTable(db);
        }
        // create new tables
        onCreate(db);
    }

    public void parseJSONs() {
        for (Data instance : tablesInstances) {
            if (instance.isParsable()) {
                List<Model> parsedObjects = pareseJSONFile(instance.getModel(),
                        instance.getResourceJson());
                instance.createObjects(parsedObjects);
            }
        }
    }

    public <T extends Model> List<T> pareseJSONFile(Class<T> cls, int resourseId) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    resourseId);
            JavaType type = mapper.getTypeFactory().constructCollectionType(
                    List.class, cls);
            List<T> parsedObject = mapper.readValue(inputStream, type);

            return parsedObject;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
