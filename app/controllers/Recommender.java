package controllers;

import models.BusinessUserProfile;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.bson.types.ObjectId;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ClubProfileORM;
import test.SampleRecommender;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Marek on 2015-06-28.
 * uses recommendation model and collects corresponding info
 */
public class Recommender extends Controller {
    @Inject
    SampleRecommender sampleRecommender;

    @Inject
    ClubProfileORM clubDatabase;

    public Result clubsFroUser(String user_id, int count, double x, double y) {
        final Optional<GenericUserBasedRecommender> recommender = sampleRecommender.getRecommender();

        final List<BusinessUserProfile> clubs = recommender.flatMap(genericUserBasedRecommender -> {
            try {
                return Optional.of(genericUserBasedRecommender
                                .recommend(SampleRecommender.id2long(user_id), count)
                                .parallelStream()
                                .map(RecommendedItem::getItemID)
                                .map(SampleRecommender::long2id)
                                .map(ObjectId::new)
                                .map(clubDatabase::get)
                                .sorted((o1, o2) -> cmp(o1, o2, x, y))
                                .collect(Collectors.toList())
                );
            } catch (UnsupportedEncodingException | TasteException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }).orElse(Collections.singletonList(new BusinessUserProfile()));

        return ok(Json.toJson(clubs));
    }

    private double xCord(BusinessUserProfile o) {
        return o.locationCoordinates.getxCoordinate();
    }
    private double yCord(BusinessUserProfile o) {
        return o.locationCoordinates.getxCoordinate();
    }
    private int cmp(BusinessUserProfile o1, BusinessUserProfile o2, double x, double y) {
        double distance1 = Math.pow(xCord(o1) - x, 2) + Math.pow(yCord(o1) - y, 2);
        double distance2 = Math.pow(xCord(o2) - x, 2) + Math.pow(yCord(o2) - y, 2);

        if (distance1 == distance2) return 0;
        if (distance1 >  distance2) return 1;
        return -1;
    }
}
