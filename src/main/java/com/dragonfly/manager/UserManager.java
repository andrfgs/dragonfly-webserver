package com.dragonfly.manager;

import com.dragonfly.entity.User;
import com.dragonfly.exception.UserAlreadyExistsException;
import com.dragonfly.exception.UserNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class UserManager {


    public static User getUser(String username) throws InvocationTargetException, NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, UserNotFoundException {
        User u = DatabaseConnection.getSingleObject(User.class, "SELECT * FROM users WHERE users.Username = ?", username);

        if (u == null)
            throw new UserNotFoundException("");

        return u;
    }

    public static void createUser(User u) throws NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, UserAlreadyExistsException {
        if (userExists(u.getUsername()))
            throw new UserAlreadyExistsException("");

        DatabaseConnection.executeQuery("INSERT INTO users VALUES  (?, ?, ?, ?, ?)",
                u.getUsername(), u.getEmail(), u.getFirstName(), u.getLastName(), u.getPassword());
    }

    private static boolean userExists(String username) throws NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException {
        try
        {
            getUser(username);
        }
        catch (UserNotFoundException ex)
        {
            return false;
        }

        return true;
    }
}
