package controllers;

import org.apache.mahout.cf.taste.common.TasteException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import test.Recommender;

import javax.inject.Inject;
import java.io.IOException;

public class Application extends Controller {

    @Inject
    Recommender recommender;

    public Result calc() throws IOException, TasteException{
        recommender.update();

        return ok();
    }

    public Result index() {
         return ok(Json.toJson(23571113));
    }
}
