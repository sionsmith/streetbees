package com.streetbees.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.streetbees.aws.DynamoDBUtils;
import com.streetbees.config.DynamoDBConfig;
import com.streetbees.model.Blog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by streetbees on 9/17/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DynamoDBConfig.class})
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Before
    public void setUp() throws Exception {
        DynamoDBUtils utils = new DynamoDBUtils(amazonDynamoDB);

        //create table in DynamoDB if not exists.
        utils.createBlogTableIfNotExists("blog2");

    }

    @Test
    public void testInsertBlogPost() throws Exception {
        Blog blogPost = new Blog("sample blog post", "http://localhost:8080/sampleBlog");
        blogRepository.save(blogPost);

        //assert id
        assertNotNull(blogPost.getId());
        assertNotNull(blogRepository.findOne(blogPost.getId()));
    }

}
