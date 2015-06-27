package services;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import models.FinishedVisits;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * Created by Marek on 2015-06-27.
 */
@Singleton
public class DB extends BasicDAO<FinishedVisits, ObjectId> {
    protected DB() throws UnknownHostException {
        super(
                new MongoClient(new MongoClientURI("mongodb://app:rogus2015!@ds037601.mongolab.com:37601/goparty")),
                new Morphia(),
                "goparty"
        );
    }
}
