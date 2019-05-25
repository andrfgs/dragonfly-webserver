package com.dragonfly.rest;

import com.dragonfly.manager.PlantManager;
import com.dragonfly.util.DefaultLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/plants")
public class PlantService {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPlants() {
        try {
            return Response.status(200).entity(PlantManager.getAllPlants()).build();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | SQLException | IllegalAccessException e) {

            DefaultLogger.get().log(Level.FINE, "Testing Service request received!");
        }

        return Response.status(500).build();
    }

}
