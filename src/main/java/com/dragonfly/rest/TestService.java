package com.dragonfly.rest;

import com.dragonfly.entity.Plant;
import com.dragonfly.manager.DatabaseConnection;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/test")
public class TestService {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response testService() {
        Logger.getLogger("org.glassfish.jersey.logging.LoggingFeature").log(Level.FINE, "Testing Service request received!");
        return Response.status(200).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response testDatabase()
    {
        List<Plant> plants;
        try {
            plants = DatabaseConnection.getObjects(Plant.class, "SELECT * FROM plants");
        } catch (NoSuchMethodException | InstantiationException | SQLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return Response.status(400).entity(e.getMessage()).build();
        }

        return Response.status(200).entity(plants).build();
    }
}
