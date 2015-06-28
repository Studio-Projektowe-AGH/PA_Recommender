package services;

import models.FinishedVisits;

import javax.inject.Singleton;
import java.net.UnknownHostException;

@Singleton
public class FinishedVisitsORM extends DB<FinishedVisits> {

    protected FinishedVisitsORM() throws UnknownHostException {
        super();
    }
}
