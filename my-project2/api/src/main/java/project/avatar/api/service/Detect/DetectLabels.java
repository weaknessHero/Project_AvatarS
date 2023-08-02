/*package project.avatar.api.service.Detect;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetectLabels {

    public static void main(String[] args) throws IOException {
        // TODO(developer): Replace these variables before running the sample.
        String filePath = "avatar_1.png";
        //Resource resource = new ClassPathResource(filePath);
        //File file = resource.getFile();

        //detectLabels(file.getPath());
    }

    // Detects objects in the specified image.
    public static List<ObjectAnnotation> detectObjects(MultipartFile image) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.copyFrom(image.getBytes());

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List<ObjectAnnotation> objectAnnotations = new ArrayList<>();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.err.println("Error: " + res.getError().getMessage());
                    return objectAnnotations;
                }

                for (LocalizedObjectAnnotation annotation : res.getLocalizedObjectAnnotationsList()) {
                    objectAnnotations.add(new ObjectAnnotation(annotation.getName(), annotation.getScore()));
                }
            }

            return objectAnnotations;
        }
    }
}*/

package project.avatar.api.service.Detect;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetectLabels {


    // Detects objects in the specified image.
    public static List<ObjectAnnotation> detectObjects(MultipartFile image) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.copyFrom(image.getBytes());

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List<ObjectAnnotation> objectAnnotations = new ArrayList<>();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.err.println("Error: " + res.getError().getMessage());
                    return objectAnnotations;
                }

                for (LocalizedObjectAnnotation annotation : res.getLocalizedObjectAnnotationsList()) {
                    ObjectAnnotation objectAnnotation = new ObjectAnnotation();
                    objectAnnotation.setName(annotation.getName());
                    objectAnnotation.setScore(annotation.getScore());
                    objectAnnotations.add(objectAnnotation);

                    // 바운딩 박스 정보 출력 (Normalized Vertices)
                    System.out.println("Bounding Poly:");
                    for (NormalizedVertex vertex : annotation.getBoundingPoly().getNormalizedVerticesList()) {
                        System.out.printf("(%.2f, %.2f)\n", vertex.getX(), vertex.getY());
                    }
                }
            }

            return objectAnnotations;
        }
    }
}
