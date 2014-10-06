package util;

import com.fasterxml.jackson.core.JsonPointer;

/**
 * Created by sionsmith on 06/10/2014.
 */
public class Constants {

    //json points to event positions
    public static final JsonPointer STATUS_POINTER = JsonPointer.compile("/status");
    public static final JsonPointer VALUE_POINTER = JsonPointer.compile("/value");
    public static final JsonPointer IPADDRESS_POINTER = JsonPointer.compile("/ipAddress");
    public static final JsonPointer TIMESTAMP_POINTER = JsonPointer.compile("/timestamp");
    public static final String KINESIS_TOPIC_GOOD = "test-streetbees-good";
    public static final String KINESIS_TOPIC_BAD = "test-streetbees-bad";
}
