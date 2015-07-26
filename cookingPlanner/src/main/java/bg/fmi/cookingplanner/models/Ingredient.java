package bg.fmi.cookingplanner.models;


public class Ingredient extends Model {

    private static final long serialVersionUID = 1L;
    private String name;
    private long id;
    private long rating;
    private FoodType type;

    public Ingredient() {
        this.rating = 0;
    }

    public Ingredient(String name, FoodType type) {
        this.rating = 0;
        this.name = name;
        this.type = type;
    }

    public Ingredient(long id, String name, FoodType type) {
        this.id = id;
        this.name = name;
        this.rating = 0;
        this.type = type;
    }

    public Ingredient(long id, String name, long rating, FoodType type) {
        this.rating = rating;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
