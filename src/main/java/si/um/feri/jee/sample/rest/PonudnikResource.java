package si.um.feri.jee.sample.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.jee.sample.Service.PonudnikService;
import si.um.feri.jee.sample.vao.Ponudnik;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.List;

@Path("/ponudniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PonudnikResource {

    @Inject
    PonudnikService ponudnikService;

    @GET
    public List<Ponudnik> getAllProviders() {
        return ponudnikService.getAllProviders();
    }

    @GET
    @Path("/{id}")
    public Ponudnik getProvider(@PathParam("id") int id) {
        return ponudnikService.getProviderById(id);
    }

    @POST
    public Response addProvider(Ponudnik ponudnik) {
        ponudnikService.addProvider(ponudnik);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/{id}/polnilnice/{stationId}")
    public Response addChargingStation(
            @PathParam("id") int id,
            @PathParam("stationId") String stationId
    ) {
        ponudnikService.addChargingStationToProvider(id, stationId);
        return Response.ok().build();
    }

    @GET
    @Path("/polnilnice")
    public List<Polnilnica> getStationsAlphabetically() {
        return ponudnikService.getAllStationsAlphabetically();
    }
}