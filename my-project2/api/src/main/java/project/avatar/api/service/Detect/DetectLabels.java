package project.avatar.api.service.Detect;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetectLabels {

    public static void main(String[] args) throws IOException {
        // TODO: Replace with your image InputStream
        InputStream imageStream = getImageInputStream();

        String result = detectLabels(imageStream);
        System.out.println(result);
    }

    // Detects labels in the specified image InputStream and returns the result as a String.
    public static String detectLabels(InputStream imageStream) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(imageStream);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            StringBuilder resultBuilder = new StringBuilder();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    resultBuilder.append("Error: ").append(res.getError().getMessage()).append("\n");
                    return resultBuilder.toString();
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    annotation
                            .getAllFields()
                            .forEach((k, v) -> resultBuilder.append(k).append(" : ").append(v.toString()).append("\n"));
                }
            }

            return resultBuilder.toString();
        }
    }

    // TODO: Implement a method to get the image InputStream
    private static InputStream getImageInputStream() {
        // Replace with your logic to get the image InputStream
        return null;
    }
}
