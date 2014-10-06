package controllers;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kinesis.KinesisProducerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Play;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import util.Constants;

/**
* Created by sionsmith on 05/10/2014.
*/
public class StatsController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(StatsController.class);

    private static final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    @BodyParser.Of(BodyParser.Json.class)
    public static Result receiveEvent() {
        String ipAddress = request().remoteAddress();
        JsonNode receivedData = request().body().asJson();

        Result result = processEvent(receivedData, ipAddress);

        // Can be activated on the fly (with scan delay) by changing logback.xml
        if(log.isTraceEnabled())  // Additional check to reduce unnecessary work
            log.trace("REQUEST {} -> RESPONSE {}  FROM IP {}", receivedData, result, ipAddress);

        return result;
    }


    private static Result processEvent(JsonNode event, String ipAddress) {
        if(event != null && event.size() > 0){
            //validate node
            if(eventIsValidate(event)){
                //decorate event with extra info.
                ((ObjectNode) event).put(JsonPointer.compile("/ipAddress").getMatchingProperty(), ipAddress);

                //if status is 200 else bad data.
                String topicName;
                if(event.at(Constants.STATUS_POINTER).asInt() == 200)
                    topicName = Constants.KINESIS_TOPIC_GOOD;
                else
                    topicName = Constants.KINESIS_TOPIC_BAD;

                return upAuthProvider().send(topicName, event);
            }
        }

        return badRequest("Must contain event objects");
    }

    private static boolean eventIsValidate(JsonNode event) {
        //status
        if(event.at(Constants.STATUS_POINTER).isMissingNode())
            return false;

        //value
        if(event.at(Constants.VALUE_POINTER).isMissingNode())
            return false;

        //timestamp
        if(event.at(Constants.TIMESTAMP_POINTER).isMissingNode())
            return false;

        //everything is present.
        return true;
    }

    private static KinesisProducerPlugin upAuthProvider() {
        return Play.application().plugin(KinesisProducerPlugin.class);
    }
}
