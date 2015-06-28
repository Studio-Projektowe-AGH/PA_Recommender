package services;

import models.ImmportantResult;

import javax.inject.Singleton;
import java.net.UnknownHostException;

/**
 * Created by Marek on 2015-06-28.
 * Used when connecting to ImmportantResult table
 */

@Singleton
public class ImportantResultORM extends DB<ImmportantResult> {
    protected ImportantResultORM() throws UnknownHostException {
        super();
    }
}
