package com.dragonfly.rest;

import com.dragonfly.entity.NotificationLog;
import com.dragonfly.entity.Plantation;
import com.dragonfly.entity.SensorLog;
import com.dragonfly.exception.UnitNotFoundException;
import com.dragonfly.exception.UserNotFoundException;
import com.dragonfly.manager.PlantationManager;
import com.dragonfly.manager.TokenManager;
import com.dragonfly.manager.UnitManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Path("/unit")
public class UnitService {

    @POST
    @Path("/notificationlog")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response createNotificationLog(@QueryParam("username") String username, @QueryParam("tokenid") String tokenID, NotificationLog log)
    {
        try {
            if (!TokenManager.isTokenValid(username, tokenID))
                return Response.status(Response.Status.BAD_REQUEST).entity("InvalidCredentialsException").build();

            UnitManager.createNotificationLog(log);
            return Response.status(200).build();

        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("UserNotFoundException").build();

        }  catch (UnitNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("UnitNotFoundException").build();

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/sensorlog")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response createSensorLog(@QueryParam("username") String username, @QueryParam("tokenid") String tokenID, SensorLog log)
    {
        try {
            if (!TokenManager.isTokenValid(username, tokenID))
                return Response.status(Response.Status.BAD_REQUEST).entity("InvalidCredentialsException").build();

            UnitManager.createSensorLog(log);
            return Response.status(200).build();

        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("UserNotFoundException").build();

        }  catch (UnitNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("UnitNotFoundException").build();

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
