package com.streetbees.aws;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;


/**
 * Created by streetbees on 13/09/2014.
 */
public class DynamoDBUtils {
    private static final Log LOG = LogFactory.getLog(DynamoDBUtils.class);

    private static final String ATTRIBUTE_NAME_HASH_KEY = "resource";
    private static final String ATTRIBUTE_NAME_RANGE_KEY = "timestamp";

    private AmazonDynamoDB dynamoDB;

    /**
     * Create a new utility instance that uses the provided Amazon DynamoDB client.
     *
     * @param dynamoDB The Amazon DynamoDB client to use.
     */
    public DynamoDBUtils(AmazonDynamoDB dynamoDB) {
        if (dynamoDB == null) {
            throw new NullPointerException("dynamoDB must not be null");
        }
        this.dynamoDB = dynamoDB;
    }

    /**
     * Create a DynamoDB mapper that uses the provided table name in every request.
     *
     * @param tableName The name of the DynamoDB table the mapper will use for all requests.
     * @return A mapper capable of reading/writing to the table given.
     */
    public DynamoDBMapper createMapperForTable(String tableName) {
        DynamoDBMapperConfig config = new DynamoDBMapperConfig(TableNameOverride.withTableNameReplacement(tableName));
        return new DynamoDBMapper(dynamoDB, config);
    }

    /**
     * Creates the table to store our counts in with a hash key of "resource" and a range key of "timestamp" so we can
     * query counts for a given resource by time. This uses an initial provisioned throughput of 10 read capacity units
     * and 5 write capacity units
     *
     * @param tableName The name of the table to create.
     */
    public void createBlogTableIfNotExists(String tableName) {
        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement().withKeyType(KeyType.HASH).withAttributeName(ATTRIBUTE_NAME_HASH_KEY));
        ks.add(new KeySchemaElement().withKeyType(KeyType.RANGE).withAttributeName(ATTRIBUTE_NAME_RANGE_KEY));

        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(ATTRIBUTE_NAME_HASH_KEY)
                .withAttributeType(ScalarAttributeType.S));
        // Range key must be a String. DynamoDBMapper translates Dates to ISO8601 strings.
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(ATTRIBUTE_NAME_RANGE_KEY)
                .withAttributeType(ScalarAttributeType.S));

        // updates changes.
        CreateTableRequest createTableRequest =
                new CreateTableRequest().withTableName(tableName)
                        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                        .withKeySchema(ks)
                        .withAttributeDefinitions(attributeDefinitions);

        try {
            dynamoDB.createTable(createTableRequest);

            LOG.info(String.format("Created DynamoDB table: %s. Waiting up to 5 minutes for it to become ACTIVE...",
                    tableName));
            // Wait 5 minutes for the table to become ACTIVE
            if (!waitUntilTableIsActive(tableName, 10, TimeUnit.MINUTES.toSeconds(5))) {
                throw new IllegalStateException(String.format("Timed out while waiting for DynamoDB table %s to become ready",
                        tableName));
            }
        } catch (ResourceInUseException ex) {
            // Assume table exists and is ready to use
        }
    }

    /**
     * Delete a DynamoDB table.
     *
     * @param tableName The name of the table to delete.
     */
    public void deleteTable(String tableName) {
        LOG.info(String.format("Deleting DynamoDB table %s", tableName));
        try {
            dynamoDB.deleteTable(tableName);
        } catch (ResourceNotFoundException ex) {
            // Ignore, table could not be found.
        } catch (AmazonClientException ex) {
            LOG.error(String.format("Error deleting DynamoDB table %s", tableName), ex);
        }
    }

    /**
     * Wait for a DynamoDB table to become active and ready for use.
     *
     * @param tableName The name of the table to wait until it becomes active.
     * @param secondsBetweenPolls Seconds to wait between requests to DynamoDB.
     * @param timeoutSeconds Maximum amount of time, in seconds, to wait for a table to become ready.
     * @return {@code true} if the table is ready. False if our timeout exceeded or we were interrupted.
     */
    private boolean waitUntilTableIsActive(String tableName, long secondsBetweenPolls, long timeoutSeconds) {
        long sleepTimeRemaining = timeoutSeconds * 1000;

        while (!doesTableExist(tableName)) {
            if (sleepTimeRemaining <= 0) {
                return false;
            }

            long timeToSleepMillis = Math.min(1000 * secondsBetweenPolls, sleepTimeRemaining);

            try {
                Thread.sleep(timeToSleepMillis);
            } catch (InterruptedException ex) {
                LOG.warn("Interrupted while waiting for count table to become ready", ex);
                Thread.currentThread().interrupt();
                return false;
            }

            sleepTimeRemaining -= timeToSleepMillis;
        }

        return true;
    }

    /**
     * Determines if the table exists and is ACTIVE.
     *
     * @param tableName The name of the table to check.
     * @return {@code true} if the table exists and is in the ACTIVE state
     */
    private boolean doesTableExist(String tableName) {
        try {
            return "ACTIVE".equals(dynamoDB.describeTable(tableName).getTable().getTableStatus());
        } catch (AmazonClientException ex) {
            LOG.warn(String.format("Unable to describe table %s", tableName), ex);
            return false;
        }
    }

}
