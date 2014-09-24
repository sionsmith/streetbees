package com.streetbees.resources;

import com.streetbees.views.SignInView;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sionsmith on 9/23/14.
 */
@Service
@Path("/signin")
public class SignInResource {

    public SignInResource() {
    }

    @GET()
    @Produces(MediaType.TEXT_HTML)
    public SignInView getSignInViewFreemarker(){
        return new SignInView("ftl/account/signin.ftl");
    }
}
