package si.um.feri.jee.sample.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.jee.sample.Service.PolnilnicaService;
import si.um.feri.jee.sample.Service.UserService;
import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.User;

import java.util.List;

@Path("/polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PolnilnicaResource {

    @Inject
    PolnilnicaService polnilnicaService;

    @Inject
    UserService userService;

    @GET
    public List<Polnilnica> getAllStations() {
        return polnilnicaService.getAllChargingStations();
    }

    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") String id) {
        try {
            Polnilnica p = polnilnicaService.getChargingStationById(id);
            return Response.ok(p).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response add(Polnilnica polnilnica) {
        try {
            polnilnicaService.addChargingStation(polnilnica);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/start")
    public Response startCharging(@PathParam("id") String id, @QueryParam("email") String email) {
        try {
            boolean ok = polnilnicaService.startCharging(id, email);
            if (ok)
                return Response.ok().entity("Polnjenje začeto.").build();
            else
                return Response.status(Response.Status.CONFLICT).entity("Polnjenje ni mogoče.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/end")
    public Response endCharging(@PathParam("id") String id) {
        try {
            polnilnicaService.endCharging(id);
            return Response.ok().entity("Polnjenje zaključeno.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}