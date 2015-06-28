package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import models.FinishedVisits;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.FinishedVisitsORM;
import services.ImportantResultORM;
import test.SampleRecommender;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class Application extends Controller {

    @Inject
    SampleRecommender recommender;

    @Inject
    FinishedVisitsORM inputDatabase;

    @Inject
    ImportantResultORM outputDatabase;

    public Result calc() {
        List<Reduced> res = inputDatabase.find().asList().parallelStream().map(Reduced::new).collect(Collectors.toList());

        CsvSchema schema = CsvSchema.builder()
                .addColumn("user_id")
                .addColumn("club_id")
                .addColumn("rating")
                .build();

        try {
            String csv = new CsvMapper().writer(schema).writeValueAsString(res);

            outputDatabase.save(recommender.importantFunction(csv));

            return ok();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return internalServerError();
        }
    }

    public Result index() {
         return ok(Json.toJson(23571113));
    }

    private class Reduced {
        private String user_id;
        private String club_id;
        private Long rating;

        public Reduced(FinishedVisits fv) {
            user_id = fv.getUser_id();
            club_id = fv.getClub_id();
            rating  = fv.getRating();
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getClub_id() {
            return club_id;
        }

        public void setClub_id(String club_id) {
            this.club_id = club_id;
        }

        public Long getRating() {
            return rating;
        }

        public void setRating(Long rating) {
            this.rating = rating;
        }
    }
}
