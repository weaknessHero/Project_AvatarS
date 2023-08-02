/*package project.avatar.api.service.Detect;

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
}*/

package project.avatar.api.service.Detect;

import com.google.cloud.vision.v1.BoundingPoly;
import com.google.cloud.vision.v1.Vertex;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
public class ObjectAnnotation {
    private String name;
    private float score;
    private BoundingPoly boundingPoly; // Add BoundingPoly field

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    // Calculate the most dominant color from the bounding box
    public ImageColor calculateDominantColor() {
        // Initialize a HashMap to store color frequencies
        Map<ImageColor, Integer> colorFrequencies = new HashMap<>();

        // Get the vertices of the bounding box
        List<Vertex> vertices = boundingPoly.getVerticesList();

        // Iterate through the vertices and extract colors
        for (Vertex vertex : vertices) {
            int red = (int) (vertex.getX() * 255); // X-coordinate corresponds to red
            int green = (int) (vertex.getY() * 255); // Y-coordinate corresponds to green
            int blue = (int) ((vertex.getX() + vertex.getY()) * 255 / 2); // Combination of X and Y corresponds to blue

            // Create an ImageColor object
            ImageColor color = new ImageColor(red, green, blue);

            // Update the color frequency
            colorFrequencies.put(color, colorFrequencies.getOrDefault(color, 0) + 1);
        }

        // Find the most frequent color
        ImageColor dominantColor = null;
        int maxFrequency = 0;
        for (Map.Entry<ImageColor, Integer> entry : colorFrequencies.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                dominantColor = entry.getKey();
            }
        }

        return dominantColor;
    }
}
