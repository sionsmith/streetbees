package kinesis;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Result;

/**
 * Created by sionsmith on 05/10/2014.
 */
public interface EventProducer {
    public Result send(String topic, JsonNode event);
}
