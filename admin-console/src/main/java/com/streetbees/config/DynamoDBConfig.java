package com.streetbees.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.apache.commons.lang3.StringUtils;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBOperations;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* Created by streetbees on 29/05/2014.
*/
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.streetbees.repositories", dynamoDBOperationsRef="dynamoDBOperations")
public class DynamoDBConfig {

//    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint = "https://dynamodb.eu-west-1.amazonaws.com";

//    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey = "";

//    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey = "";

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(
                amazonAWSCredentials());
        if (StringUtils.isNotEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }
        return amazonDynamoDB;
    }

    @Bean
    public DynamoDBOperations dynamoDBOperations()
    {
        return new DynamoDBTemplate(amazonDynamoDB());
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

}
