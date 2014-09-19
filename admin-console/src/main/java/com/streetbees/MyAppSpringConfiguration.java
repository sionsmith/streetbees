package com.streetbees;

import com.streetbees.config.DynamoDBConfig;
import com.streetbees.security.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

/**
 Main Spring Configuration
 */
@Configuration
//@ImportResource("classpath:/spring-security.xml")
@ContextConfiguration(classes = {DynamoDBConfig.class, SecurityConfig.class})
@ComponentScan(basePackages = {"com.streetbees"})
public class MyAppSpringConfiguration {

}
