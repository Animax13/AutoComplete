package AutoComplete.resources;

import AutoComplete.core.AutoCompleteService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/autocomplete")
public class AutoCompleteResource {

    @Inject
    private AutoCompleteService autoCompleteService;

    @Path("/{query}")
    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutoCompleteResponse (@PathParam("query") String query) {
        return Response.status(Response.Status.OK).entity(autoCompleteService.getAutoCompleteResult(query)).build();
    }
}
