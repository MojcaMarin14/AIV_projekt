package si.um.feri.jee.sample.rest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.jee.sample.Service.UserService;
import si.um.feri.jee.sample.vao.User;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{email}")
    public Response getUser(@PathParam("email") String email) {
        try {
            User user = userService.getUserByEmail(email);
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response addUser(User user) {
        try {
            userService.addUser(user);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response updateUser(User user) {
        try {
            userService.updateUser(user);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{email}")
    public Response deleteUser(@PathParam("email") String email) {
        try {
            userService.deleteUser(email);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
