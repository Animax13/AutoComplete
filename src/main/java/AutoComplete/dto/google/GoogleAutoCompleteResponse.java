package AutoComplete.dto.google;

import AutoComplete.enums.GoogleStatusCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleAutoCompleteResponse implements Serializable {

    private List<Prediction> predictions;
    private GoogleStatusCode status;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public GoogleStatusCode getStatus() {
        return status;
    }

    public void setStatus(GoogleStatusCode status) {
        this.status = status;
    }
}
