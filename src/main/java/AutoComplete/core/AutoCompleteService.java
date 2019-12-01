package AutoComplete.core;

import AutoComplete.api.GoogleAutoCompleteAPI;
import AutoComplete.cache.AutoCompleteCache;
import AutoComplete.dto.google.GoogleAutoCompleteResponse;
import AutoComplete.dto.response.AutoCompleteResponse;
import AutoComplete.enums.GoogleStatusCode;
import AutoComplete.enums.Status;
import AutoComplete.mappers.AutoCompletePredictionMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Collectors;

@Singleton
public class AutoCompleteService {

    private final Logger logger = LoggerFactory.getLogger(AutoCompleteService.class);
    private final AutoCompletePredictionMapper mapper = Mappers.getMapper(AutoCompletePredictionMapper.class);
    private ObjectMapper objectMapper;

    @Inject
    private  GoogleAutoCompleteAPI googleAutoCompleteAPI;

    @Inject
    private AutoCompleteCache autoCompleteCache;

    public AutoCompleteService() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public AutoCompleteResponse getAutoCompleteResult (String query) {
        AutoCompleteResponse autoCompleteResponse = null;
        try {
            autoCompleteResponse = getResponseFromCache(query);
            if (autoCompleteResponse == null) {
                autoCompleteResponse = getResponseFromGoogle(query);
            }
        } catch (Exception ex) {
            logger.error("Exception in AutoCompleteService: ", ex);
        }
        if (autoCompleteResponse == null) {
            autoCompleteResponse = new AutoCompleteResponse();
            autoCompleteResponse.setStatus(Status.FAILURE);
        }
        return autoCompleteResponse;
    }

    private AutoCompleteResponse getResponseFromCache (String query) {
        AutoCompleteResponse autoCompleteResponse = null;
        String resultInCache = autoCompleteCache.getFromCache(query);
        if (StringUtils.isNotEmpty(resultInCache)) {
            try {
                autoCompleteResponse = objectMapper.readValue(resultInCache, AutoCompleteResponse.class);
            } catch (IOException e) {
                logger.error("Exception while converting value from cache into object : ", e);
                e.printStackTrace();
            }
        }
        return autoCompleteResponse;
    }

    private AutoCompleteResponse getResponseFromGoogle (String query) {
        AutoCompleteResponse autoCompleteResponse = null;
        GoogleAutoCompleteResponse result = googleAutoCompleteAPI.callGoogleAutoComplete(query);
        if (GoogleStatusCode.OK.equals(result.getStatus())) {
            autoCompleteResponse = new AutoCompleteResponse();
            autoCompleteResponse.setPredictions(result.getPredictions().stream().map(mapper::map).collect(Collectors.toList()));
            autoCompleteResponse.setStatus(Status.SUCCESS);
            putResponseInCache(query, autoCompleteResponse);
        } else {
            logger.error("Could not get autocomplete response from Google: Status Code: " + result.getStatus());
        }
        return autoCompleteResponse;
    }

    private void putResponseInCache (String query, AutoCompleteResponse autoCompleteResponse) {
        String value = null;
        try {
            value = objectMapper.writeValueAsString(autoCompleteResponse);
        } catch (JsonProcessingException e) {
            logger.error("Exception while converting autocomplete response to string for putting in cache: ", e);
            e.printStackTrace();
        }
        autoCompleteCache.putInCache(query, value);
    }
}