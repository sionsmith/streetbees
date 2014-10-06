package kinesis;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Application;
import play.Configuration;
import play.Plugin;
import play.mvc.Result;
import util.Constants;

import java.io.IOException;
import java.nio.ByteBuffer;

import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

/**
 * An example Play 2 plugin written in Java.
 */
public class KinesisProducerPlugin extends Plugin implements EventProducer {
    private static final Logger log = LoggerFactory.getLogger(KinesisProducerPlugin.class);

    private final Application application;

    private static AmazonKinesis kinesis;

    private final ObjectMapper jsonMapper;

    public KinesisProducerPlugin(Application application) {
        this.application = application;
        this.jsonMapper = new ObjectMapper();

    }

    @Override
    public void onStart() {
        Configuration configuration = application.configuration();
        // you can now access the application.conf settings, including any custom ones you have added
        log.info("MyExamplePlugin has started");
        configure();
    }

    @Override
    public void onStop() {
        // you may want to tidy up resources here
        log.info("MyExamplePlugin has stopped");
    }

    @Override
    public Result send(String topic, JsonNode event) {
        try {
            //write to kinesis
            byte[] bytes;
            try {
                bytes = jsonMapper.writeValueAsBytes(event);
            } catch (IOException e) {
//                logger.warn("Skipping pair. Unable to serialize: '" + event + "'", e);
                return null;
            }
            PutRecordRequest putRecord = new PutRecordRequest();
            putRecord.setStreamName(topic);
            putRecord.setPartitionKey(event.at(Constants.IPADDRESS_POINTER).textValue());
            //append data
            putRecord.setData(ByteBuffer.wrap(bytes));
            // Order is not important for this application so we do not send a SequenceNumberForOrdering
            putRecord.setSequenceNumberForOrdering(null);

            kinesis.putRecord(putRecord);

            return ok();
        } catch (Exception ex) {
            log.error("Failed sending message to kinesis stream: {}\nCaused by: {}", topic, ex);
            return internalServerError("Error: Please try again later.");
        }
    }

    public void configure() {
        log.info("configuring kinesis producer...");
        AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
        ClientConfiguration clientConfig = KinesisUtils.configureUserAgentForSample(new ClientConfiguration());
        kinesis = new AmazonKinesisClient(credentialsProvider, clientConfig);
        kinesis.setRegion(KinesisUtils.parseRegion("eu-west-1"));
    }

}
