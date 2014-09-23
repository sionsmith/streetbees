package com.streetbees.resources;

import com.streetbees.views.SignInView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sionsmith on 9/23/14.
 */
public class SignInResource {

    @GET()
    @Path("/signin")
    @Produces(MediaType.TEXT_HTML)
    public SignInView getSignInViewFreemarker(){
        return new SignInView("");
    }
}
