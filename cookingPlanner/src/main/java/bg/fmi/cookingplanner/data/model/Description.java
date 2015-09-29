package bg.fmi.cookingplanner.data.model;


import java.util.List;

public class Description extends Model {

    private static final long serialVersionUID = 1L;
    private List<String> stages;

    public Description() {

    }

    public Description(List<String> stages) {
        this.stages = stages;
    }

    public List<String> getStages() {
        return stages;
    }
    public void setStages(List<String> stages) {
        this.stages = stages;
    }

}
