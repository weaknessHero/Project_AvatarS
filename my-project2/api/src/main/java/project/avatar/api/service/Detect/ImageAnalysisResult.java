package project.avatar.api.service.Detect;

import java.util.List;

public class ImageAnalysisResult {
    private List<ObjectAnnotation> objectAnnotations;
    private ImageColor dominantColor;

    // 생성자, getter 및 setter 메서드 등을 정의해주세요.

    public ImageAnalysisResult() {
    }

    public ImageAnalysisResult(List<ObjectAnnotation> objectAnnotations, ImageColor dominantColor) {
        this.objectAnnotations = objectAnnotations;
        this.dominantColor = dominantColor;
    }

    // getter와 setter 메서드 등을 추가해주세요.
}
