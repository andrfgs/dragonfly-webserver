package com.dragonfly.rest;

import com.dragonfly.entity.LoginInfo;
import com.dragonfly.entity.Token;
import com.dragonfly.entity.User;
import com.dragonfly.exception.InvalidCredentialsException;
import com.dragonfly.exception.UserAlreadyExistsException;
import com.dragonfly.exception.UserNotFoundException;
import com.dragonfly.manager.TokenManager;
import com.dragonfly.manager.UserManager;
import com.dragonfly.util.DefaultLogger;
import com.dragonfly.util.ExceptionUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;

@Path("/user")
public class UserService {

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response createUser(User u) {
        try {
            UserManager.createUser(u);
            return Response.status(200).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(400).entity("UserAlreadyExistsException").build();
        } catch (NoSuchMethodException | SQLException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            DefaultLogger.get().log(Level.WARNING, ExceptionUtils.stackTraceToString(e));
            return Response.status(400).entity(ExceptionUtils.stackTraceToString(e)).build();
        }


    }

    @GET
    @Path("/login")
    //@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
            Token t = TokenManager.getOrRenovateToken(username, password);
            return Response.status(200).entity(t).build();

        } catch (UserNotFoundException e) {
            return Response.status(400).entity("UserNotFoundException").build();

        } catch (InvalidCredentialsException e) {
            return Response.status(400).entity("InvalidCredentialsException").build();

        } catch (InstantiationException | InvocationTargetException | SQLException | NoSuchMethodException | IllegalAccessException e) {
            DefaultLogger.get().log(Level.WARNING, ExceptionUtils.stackTraceToString(e));
            e.printStackTrace();

        }

        return Response.status(400).build();
    }
}
