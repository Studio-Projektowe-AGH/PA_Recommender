package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Marek on 2015-06-28.
 * Class returned by analises
 */
@Entity(value = "importantEntity", noClassnameStored = true)
public class ImmportantResult {
    @Id
    private ObjectId id;
    private String importantField;

    public ImmportantResult(String importantArgument) {
        importantField = importantArgument;
    }

    public String getImportantField() {
        return importantField;
    }

    public void setImportantField(String importantField) {
        this.importantField = importantField;
    }
}
