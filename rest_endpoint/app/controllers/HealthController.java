package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by sionsmith on 05/10/2014.
 */
public class HealthController extends Controller {

    public static Result healthCheck() {
        return ok();
    }
}
