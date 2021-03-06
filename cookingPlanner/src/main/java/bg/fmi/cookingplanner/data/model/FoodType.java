package bg.fmi.cookingplanner.data.model;


public class FoodType extends Model {

    private static final long serialVersionUID = 1L;
    private String name;
    private String imageName;
    private long id;

    public FoodType() {

    }

    public FoodType(long id, String name, String imageName) {
        this.id  = id;
        this.name = name;
        this.imageName = imageName;
    }

    public FoodType(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodType foodType = (FoodType) o;

        return name.equals(foodType.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
