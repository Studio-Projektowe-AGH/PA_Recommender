package controllers;

import models.FinishedVisits;
import org.bson.types.ObjectId;
import play.*;
import play.libs.Json;
import play.mvc.*;

import services.DB;
import test.SampleRecommender;
import views.html.*;

import javax.inject.Inject;
import java.util.List;

public class Application extends Controller {

    @Inject
    SampleRecommender recommender;

    @Inject
    DB database;

    public Result calc() {
        List<FinishedVisits> res = database.find().asList();
        return ok(Json.toJson(res));
    }

    public Result index() {
         return ok(Json.toJson(23571113));
//         return ok(index.render("Your new application is ready."));
    }

}
