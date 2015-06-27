package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;
import java.util.Map;

@Entity(value = "finishedVisits", noClassnameStored = true)
public class FinishedVisits {

    @Id
    private ObjectId id;
    private String user_id;
    private String club_id;
    private Long visit_start;
    private Long visit_end;
    private Long qr_scanned;
    private Long rating;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    
    public void setId(ObjectId id) {
            this.id = id;
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
    
    public Long getVisit_start() {
        return visit_start;
    }
    
    public void setVisit_start(Long visit_start) {
        this.visit_start = visit_start;
    }
    
    public Long getVisit_end() {
        return visit_end;
    }
    
    public void setVisit_end(Long visit_end) {
        this.visit_end = visit_end;
    }
    
    public Long getQr_scanned() {
        return qr_scanned;
    }
    
    public void setQr_scanned(Long qr_scanned) {
        this.qr_scanned = qr_scanned;
    }
    
    public Long getRating() {
        return rating;
    }
    
    public void setRating(Long rating) {
        this.rating = rating;
    }
    
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    

    
}

