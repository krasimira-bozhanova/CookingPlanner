package bg.androidcourse.cookingplanner.models;


public class Description extends Model {

    private static final long serialVersionUID = 1L;
    private String[] stages;

    public Description() {

    }

    public Description(String[] stages) {
        this.stages = stages;
    }

    public String[] getStages() {
        return stages;
    }
    public void setStages(String[] stages) {
        this.stages = stages;
    }

}
