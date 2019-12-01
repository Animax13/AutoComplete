package AutoComplete.core;

import AutoComplete.api.GoogleAutoCompleteAPI;
import AutoComplete.cache.AutoCompleteCache;
import AutoComplete.dto.google.GoogleAutoCompleteResponse;
import AutoComplete.dto.google.MatchedSubstring;
import AutoComplete.dto.google.Prediction;
import AutoComplete.dto.google.StructuredFormatting;
import AutoComplete.dto.response.AutoCompleteResponse;
import AutoComplete.enums.GoogleStatusCode;
import AutoComplete.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AutoCompleteServiceTest {

    private AutoCompleteService target;

    @Mock
    private GoogleAutoCompleteAPI googleAutoCompleteAPI;

    @Mock
    private AutoCompleteCache autoCompleteCache;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        target = new AutoCompleteService(googleAutoCompleteAPI, autoCompleteCache);
    }

    @Test
    public void getAutoCompleteResult_nullRequest() {
        when(autoCompleteCache.getFromCache(null)).thenReturn(null);

        AutoCompleteResponse autoCompleteResponse = target.getAutoCompleteResult(null);
        assertEquals(autoCompleteResponse.getStatus(), Status.FAILURE);
    }

    @Test
    public void getAutoCompleteResult_handlesException() {
        when(autoCompleteCache.getFromCache(anyString())).thenThrow(new RuntimeException());

        AutoCompleteResponse autoCompleteResponse = target.getAutoCompleteResult("test");
        assertEquals(autoCompleteResponse.getStatus(), Status.FAILURE);
    }

    @Test
    public void getAutoCompleteResult_responseFromCache() {
        String autoCompleteResponse = "{\"predictions\":[{\"name\":null,\"matches\":null,\"mainText\":\"dummy_main_text\",\"secondaryText\":null,\"placeId\":null}],\"status\":\"SUCCESS\"}";
        when(autoCompleteCache.getFromCache("test")).thenReturn(String.valueOf(autoCompleteResponse));
        AutoCompleteResponse response = target.getAutoCompleteResult("test");


        assertEquals(response.getStatus(), Status.SUCCESS);
        assertEquals(response.getPredictions().get(0).getMainText(), "dummy_main_text");
    }

    @Test
    public void getAutoCompleteResult_successResponseFromAPI() {
        GoogleAutoCompleteResponse googleAutoCompleteResponse = new GoogleAutoCompleteResponse();
        googleAutoCompleteResponse.setStatus(GoogleStatusCode.OK);
        Prediction prediction = new Prediction();
        prediction.setDescription("dummy_description");
        MatchedSubstring matchedSubstring = new MatchedSubstring();
        matchedSubstring.setLength(2);
        prediction.setMatchedSubstrings(Collections.singletonList(matchedSubstring));
        StructuredFormatting structuredFormatting = new StructuredFormatting();
        structuredFormatting.setMainText("dummy_main_text");
        prediction.setStructuredFormatting(structuredFormatting);
        googleAutoCompleteResponse.setPredictions(Collections.singletonList(prediction));

        when(autoCompleteCache.getFromCache(anyString())).thenReturn(null);
        when(googleAutoCompleteAPI.callGoogleAutoComplete(anyString())).thenReturn(googleAutoCompleteResponse);

        AutoCompleteResponse autoCompleteResponse = target.getAutoCompleteResult("test");

        assertEquals(autoCompleteResponse.getStatus(), Status.SUCCESS);
        assertEquals(autoCompleteResponse.getPredictions().get(0).getMainText(), "dummy_main_text");
        assertEquals(autoCompleteResponse.getPredictions().get(0).getMatches(), 2);
        assertEquals(autoCompleteResponse.getPredictions().get(0).getName(), "dummy_description");
    }

    @Test
    public void getAutoCompleteResult_failureResponseFromAPI() {
        GoogleAutoCompleteResponse googleAutoCompleteResponse = new GoogleAutoCompleteResponse();
        googleAutoCompleteResponse.setStatus(GoogleStatusCode.ZERO_RESULTS);
        googleAutoCompleteResponse.setPredictions(null);

        when(autoCompleteCache.getFromCache(anyString())).thenReturn(null);
        when(googleAutoCompleteAPI.callGoogleAutoComplete(anyString())).thenReturn(googleAutoCompleteResponse);

        AutoCompleteResponse autoCompleteResponse = target.getAutoCompleteResult("test");

        assertEquals(autoCompleteResponse.getStatus(), Status.FAILURE);
    }
}