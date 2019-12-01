package AutoComplete.api;

import AutoComplete.dto.google.GoogleAutoCompleteResponse;
import com.google.inject.name.Named;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.text.MessageFormat;

@Singleton
public class GoogleAutoCompleteAPI {

    private String urlTemplate;
    private String key;
    private String components;

    @Inject
    public GoogleAutoCompleteAPI (@Named("url-template") String urlTemplate,
                                  @Named("key") String key,
                                  @Named("components") String components) {
        this.urlTemplate = urlTemplate;
        this.key = key;
        this.components = components;
    }

    public GoogleAutoCompleteResponse callGoogleAutoComplete(String query) {
        String url = MessageFormat.format(urlTemplate, query, components, key);
        Client client = ClientBuilder.newClient();
        return client
                .target(url)
                .request()
                .get(GoogleAutoCompleteResponse.class);
    }
}
