package test;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.UnknownHostException;
import java.util.Optional;



/**
 * Created by Marek on 2015-07-02.
 *
 */
@Singleton
public class Recommender {
    private MongoDBDataModel dataModel = null;
    public Optional<SVDRecommender> recommender = Optional.empty();

    @Inject
    public Recommender() {
        try {
            dataModel = new MongoDBDataModel(
                    "ds037601.mongolab.com",
                    37601,
                    "goparty",
//                    "ratings",
//                    "MMtest",
                    "finishedVisits",
                    false,
                    false,
                    null,
                    "app",
                    "rogus2015!"
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void update() throws TasteException {
        recommender = Optional.of(new SVDRecommender(dataModel, new ALSWRFactorizer(dataModel, 3, 0.05f, 50)));
    }

    public Optional<SVDRecommender> getRecommender() {
        return recommender;
    }

    public MongoDBDataModel getDataModel() {
        return dataModel;
    }
}
