package bg.fmi.cookingplanner.data.model;


public class MealType extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static String neutralString = "all";
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

    public boolean isNeutral() {
        return name.equals(neutralString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealType mealType = (MealType) o;

        return !(name != null ? !name.equals(mealType.name) : mealType.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public static MealType getNeutralMealType() {
        return new MealType(neutralString);
    }

}
