package bg.androidcourse.cookingplanner.models;

import java.util.List;

public class Recipe extends Model {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private Description description;
    private Type type;
    private int servings;
    boolean isFavourite;

    private Content content;

    private int time;
    private List<Image> images;

    public Recipe() {
    }

    public Recipe(String name, Description description, Content content,
            int time, List<Image> images, Type type, int servings) {
        this.name = name;
        this.description = description;
        this.content = content;
        this.time = time;
        this.images = images;
        this.type = type;
        this.servings = servings;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Recipe(long id, String name, Description description,
            Content content, int time, List<Image> images, Type type,
            int servings, boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.content = content;
        this.time = time;
        this.images = images;
        this.type = type;
        this.servings = servings;
        this.isFavourite = isFavourite;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public boolean insertInDatabase() {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

}
