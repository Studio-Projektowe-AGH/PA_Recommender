package controllers;

import models.BusinessUserProfile;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.bson.types.ObjectId;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ClubProfileORM;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;
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
    ClubProfileORM clubDatabase;

    @Inject
    test.Recommender testRec;

    public Result clubsFroUser(String user_id, int count, double x, double y) {
        final Optional<SVDRecommender> maybeRecommender = testRec.getRecommender();

        final List<BusinessUserProfile> clubs = maybeRecommender.flatMap(recommender -> {
            try {
                final MongoDBDataModel dataModel = testRec.getDataModel();
                final long userID = Long.parseLong(dataModel.fromIdToLong(user_id, false));
                final List<RecommendedItem> recommendation = recommender.recommend(
                        userID,
                        count);
                Logger.debug(String.valueOf(recommendation.size()));

                return Optional.of(recommendation
                                .parallelStream()
                                .map(RecommendedItem::getItemID)
                                .map(dataModel::fromLongToId)
                                .map(ObjectId::new)
                                .map(clubDatabase::get)
                                .sorted((o1, o2) -> cmp(o1, o2, x, y))
                                .collect(Collectors.toList())
                );
            } catch (TasteException e) {
                final StringWriter out = new StringWriter();
                e.printStackTrace(new PrintWriter(out));
                Logger.warn(out.toString());
                e.printStackTrace();
                return Optional.empty();
            }
        }).orElse(Collections.emptyList());

        return ok(Json.toJson(clubs));
    }

    private double xCord(BusinessUserProfile o) {
        final Double aDouble = o.locationCoordinates.getxCoordinate();
        return aDouble != null ? aDouble : 0;
    }
    private double yCord(BusinessUserProfile o) {
        final Double aDouble = o.locationCoordinates.getyCoordinate();
        return aDouble != null ? aDouble : 0;
    }
    private int cmp(BusinessUserProfile o1, BusinessUserProfile o2, double x, double y) {
        double distance1 = Math.pow(xCord(o1) - x, 2) + Math.pow(yCord(o1) - y, 2);
        double distance2 = Math.pow(xCord(o2) - x, 2) + Math.pow(yCord(o2) - y, 2);

        if (distance1 == distance2) return 0;
        if (distance1 >  distance2) return 1;
        return -1;
    }
}
