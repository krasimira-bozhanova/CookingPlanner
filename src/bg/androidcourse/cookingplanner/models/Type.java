package bg.androidcourse.cookingplanner.models;


public class Type extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private long id;

    public Type() {

    }

    public Type(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type(long id, String name) {
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
