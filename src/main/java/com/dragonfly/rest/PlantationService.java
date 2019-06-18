package com.dragonfly.rest;


import com.dragonfly.entity.Plantation;
import com.dragonfly.exception.UnitNotFoundException;
import com.dragonfly.exception.UserNotFoundException;
import com.dragonfly.manager.PlantationManager;
import com.dragonfly.manager.TokenManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Path("/plantation")
public class PlantationService {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPlantation(@QueryParam("username") String username, @QueryParam("tokenid") String tokenID, @QueryParam("unitid") int unitID) {

        try {
            if (!TokenManager.isTokenValid(username, tokenID))
                return Response.status(Response.Status.BAD_REQUEST).entity("InvalidCredentialsException").build();

            List<Plantation> plantations = PlantationManager.getUnitPlantations(username, unitID);
            return Response.status(200).entity(plantations).build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("UserNotFoundException").build();

        } catch (UnitNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("UnitNotFoundException").build();

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
