package project.avatar.api.service.Detect;

public class ObjectAnnotation {
    private String name;
    private float score;

    public ObjectAnnotation(String name, float score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }
}
