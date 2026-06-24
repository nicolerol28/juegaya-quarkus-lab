package com.nicoleroldan.court;

import com.nicoleroldan.court.dto.CourtRequest;
import com.nicoleroldan.court.dto.CourtResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/courts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Canchas", description = "Gestión de canchas deportivas")
public class CourtResource {

    private final CourtService courtService;

    @Inject
    public CourtResource(CourtService courtService) {
        this.courtService = courtService;
    }

    @GET
    @Operation(summary = "Listar todas las canchas")
    public List<CourtResponse> listAll() {
        return courtService.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener una cancha por ID")
    public CourtResponse getById(@PathParam("id") Long id) {
        return courtService.findById(id);
    }

    @POST
    @Operation(summary = "Crear una nueva cancha")
    public Response create(@Valid CourtRequest request) {
        CourtResponse created = courtService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar una cancha existente")
    public CourtResponse update(@PathParam("id") Long id, @Valid CourtRequest request) {
        return courtService.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar una cancha")
    public Response delete(@PathParam("id") Long id) {
        courtService.delete(id);
        return Response.noContent().build();
    }
}
