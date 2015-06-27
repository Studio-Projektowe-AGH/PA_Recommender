package controllers;

import play.*;
import play.libs.Json;
import play.mvc.*;

import test.SampleRecommender;
import views.html.*;

import javax.inject.Inject;

public class Application extends Controller {

    @Inject
    SampleRecommender recommender;

    public Result calc() {
        return ok(Json.toJson(recommender.importantFunction(23571113)));
    }

    public Result index() {
         return ok(Json.toJson(23571113));
//         return ok(index.render("Your new application is ready."));
    }

}
