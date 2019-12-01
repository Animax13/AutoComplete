package AutoComplete.api;

import AutoComplete.dto.google.GoogleAutoCompleteResponse;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.text.MessageFormat;

@Singleton
public class GoogleAutoCompleteAPI {

    private final String COMPONENTS = "country:in";
    private final String KEY = "AIzaSyB88LTfd1C5OQyrU9NhEQLnTLK7-O4ewWM";
    private final String URLTemplate = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input={0}&components={1}&key={2}";

    public GoogleAutoCompleteResponse callGoogleAutoComplete(String query) {
        String url = MessageFormat.format(URLTemplate, query, COMPONENTS, KEY);
        Client client = ClientBuilder.newClient();
        return client
                .target(url)
                .request()
                .get(GoogleAutoCompleteResponse.class);
    }
}
