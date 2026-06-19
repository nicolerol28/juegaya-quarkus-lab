package com.nicoleroldan.court;

import com.nicoleroldan.court.dto.CourtRequest;
import com.nicoleroldan.court.dto.CourtResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/courts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourtResource {

    @Inject
    CourtService courtService;

    @GET
    public List<CourtResponse> listAll() {
        return courtService.listAll();
    }

    @GET
    @Path("/{id}")
    public CourtResponse getById(@PathParam("id") Long id) {
        return courtService.findById(id);
    }

    @POST
    public Response create(@Valid CourtRequest request) {
        CourtResponse created = courtService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public CourtResponse update(@PathParam("id") Long id, @Valid CourtRequest request) {
        return courtService.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        courtService.delete(id);
        return Response.noContent().build();
    }
}