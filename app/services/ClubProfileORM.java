package services;

import models.BusinessUserProfile;

import java.net.UnknownHostException;

/**
 * Created by Marek on 2015-06-28.
 */
public class ClubProfileORM extends DB<BusinessUserProfile> {
    protected ClubProfileORM() throws UnknownHostException {
        super();
    }
}
