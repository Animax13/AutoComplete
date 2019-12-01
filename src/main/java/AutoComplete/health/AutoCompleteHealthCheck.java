package AutoComplete.health;

import com.codahale.metrics.health.HealthCheck;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AutoCompleteHealthCheck extends HealthCheck {

    private final Client client;
    private final String URL = "http://localhost:8080/autocomplete/hello";
    private final String UNHEALTHY_SERVER_MESSAGE = "AutoComplete Service not running, Please check logs";

    public AutoCompleteHealthCheck() {
        super();
        this.client = new JerseyClientBuilder().build();
    }

    @Override
    protected Result check() {
        WebTarget webTarget = client.target(URL);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (response.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return Result.healthy();
        }
        return Result.unhealthy(UNHEALTHY_SERVER_MESSAGE);
    }
}
