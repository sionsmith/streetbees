package com.streetbees.views;

import io.dropwizard.views.View;

import java.nio.charset.Charset;

/**
 * Created by sionsmith on 9/23/14.
 */
public class SignInView extends View {

    public SignInView(String templateName) {
        super("sigin.ftl");
    }

    protected SignInView(String templateName, Charset charset) {
        super(templateName, charset);
    }
}
