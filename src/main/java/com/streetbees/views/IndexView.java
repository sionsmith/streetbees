package com.streetbees.views;

import com.google.common.base.Charsets;
import com.streetbees.model.Blog;
import io.dropwizard.views.View;

import java.util.List;

/**
 * Created by streetbees on 12/09/2014.
 */
public class IndexView extends View {

    private List<Blog> blogs;

    public IndexView(List<Blog> blogs) {
        super("/views/index.ftl", Charsets.UTF_8);
        this.blogs = blogs;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }
}
