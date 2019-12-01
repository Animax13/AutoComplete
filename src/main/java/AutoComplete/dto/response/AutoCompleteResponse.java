package AutoComplete.dto.response;

import AutoComplete.enums.Status;

import java.io.Serializable;
import java.util.List;

public class AutoCompleteResponse implements Serializable {

    private List<AutoCompletePrediction> predictions;
    private Status status;

    public List<AutoCompletePrediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<AutoCompletePrediction> predictions) {
        this.predictions = predictions;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
