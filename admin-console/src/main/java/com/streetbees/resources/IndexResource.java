package com.streetbees.resources;

/**
 * Created by streetbees on 12/09/2014.
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.streetbees.model.Blog;
import com.streetbees.repositories.BlogRepository;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class IndexResource {

    @Autowired
    private BlogRepository blogRepository;

    private JacksonDBCollection<Blog, String> collection;

    public IndexResource(JacksonDBCollection<Blog, String> blogs) {
        this.collection = blogs;
    }

    public IndexResource() {
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Timed
    public List<Blog> index() {
//        DBCursor<Blog> dbCursor = collection.find();
//        List<Blog> blogs = new ArrayList<>();
//        while (dbCursor.hasNext()) {
//            Blog blog = dbCursor.next();
//            blogs.add(blog);
//        }
//
        List<Blog> blogs = Lists.newArrayList(blogRepository.findAll());

        System.out.println(blogs.size());
        return blogs;
//        return new IndexView(blogs);
//        return Arrays.asList(new Blog("Day 12: OpenCV--Face Detection for Java Developers",
//                "https://www.openshift.com/blogs/day-12-opencv-face-detection-for-java-developers"));
    }

}