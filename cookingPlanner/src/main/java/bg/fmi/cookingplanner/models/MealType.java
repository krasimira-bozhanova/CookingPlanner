package bg.fmi.cookingplanner.models;


public class MealType extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private long id;

    public MealType() {

    }

    public MealType(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MealType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
