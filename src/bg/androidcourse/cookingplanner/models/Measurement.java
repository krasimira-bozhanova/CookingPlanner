package bg.androidcourse.cookingplanner.models;


public class Measurement extends Model {

    private static final long serialVersionUID = 1L;
    private String name;
    private long id;

    public Measurement() {

    }

    public Measurement(String name) {
        this.name = name;
    }

    public Measurement(long id, String name) {
        this.name = name;
        this.id = id;
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
}
