package project.avatar.api.controller.products;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorConverter {
    private static Map<String, Color> colors = new HashMap<>();

    static {
        colors.put("빨간색", new Color(255, 0, 0));
        colors.put("주황색", new Color(255, 165, 0));
        colors.put("노란색", new Color(255, 255, 0));
        // ... 기타 색상
    }
    private static double colorDistance(Color c1, Color c2) {
        int rDiff = c1.getRed() - c2.getRed();
        int gDiff = c1.getGreen() - c2.getGreen();
        int bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }
    public static String findClosestColorName(Color rgb) {
        double minDistance = Double.MAX_VALUE;
        String closestColorName = null;

        for (Map.Entry<String, Color> entry : colors.entrySet()) {
            double distance = colorDistance(rgb, entry.getValue());
            if (distance < minDistance) {
                minDistance = distance;
                closestColorName = entry.getKey();
            }
        }
        return closestColorName;
    }
    public static void main(String[] args) {
        // Hex to Color
        Color color = Color.decode("#FF6347");
        String closestColorName = findClosestColorName(color);
        System.out.println(closestColorName); // 출력: 빨간색 or 주황색
    }
}