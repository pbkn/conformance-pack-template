package conformance.pack;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PublicAccessBlockConfiguration;
import software.amazon.awssdk.services.s3.model.PutPublicAccessBlockRequest;

import java.util.Map;

public class LambdaHandler implements RequestHandler<Map<String, Object>, String> {
    @Override
    public String handleRequest(Map<String, Object> event, Context context) {
        String bucketName = (String) event.get("BucketName");

        S3Client s3Client = S3Client.builder().build();

        PublicAccessBlockConfiguration blockConfiguration = PublicAccessBlockConfiguration.builder()
                .blockPublicAcls(true)
                .ignorePublicAcls(true)
                .blockPublicPolicy(true)
                .restrictPublicBuckets(true)
                .build();

        PutPublicAccessBlockRequest publicAccessBlockRequest = PutPublicAccessBlockRequest.builder()
                .bucket(bucketName)
                .publicAccessBlockConfiguration(blockConfiguration)
                .build();

        s3Client.putPublicAccessBlock(publicAccessBlockRequest);

        return "Public access blocked for bucket: " + bucketName;
    }
}
