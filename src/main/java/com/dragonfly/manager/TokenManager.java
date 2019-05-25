package com.dragonfly.manager;

import com.dragonfly.entity.Token;
import com.dragonfly.entity.User;
import com.dragonfly.exception.InvalidCredentialsException;
import com.dragonfly.exception.UserNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class TokenManager {

    public static Token getOrRenovateToken(String username, String password) throws NoSuchMethodException, UserNotFoundException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, InvalidCredentialsException {
        User u = UserManager.getUser(username);
        if (!u.getUsername().equals(username) || !u.getPassword().equals(password))
            throw new InvalidCredentialsException("");

        Token t = getLastToken(username);

        if (!isTokenValid(t))
        {
            // Token has expired, create new token
            t = new Token(username);
            DatabaseConnection.executeQuery("INSERT INTO tokens VALUES (?, ?, ?, ?)",
                    t.getTokenID(), t.getUsername(), t.getCreationDate(), t.getExpirationDate());
        }

        return t;
    }

    public static boolean isTokenValid(String username, String tokenID) throws
            NoSuchMethodException, UserNotFoundException, InstantiationException,
            SQLException, IllegalAccessException, InvocationTargetException {
        UserManager.getUser(username);
        Token t = getLastToken(username);

        if (t == null) return false;
        if (!t.getTokenID().equals(tokenID)) return false;

        return isTokenValid(t);
    }

    private static boolean isTokenValid(Token t)
    {
        if (t == null) return false;
        return t.getExpirationDate() > System.currentTimeMillis();
    }

    private static Token getLastToken(String username)
    {
        List<Token> r = null;
        try {
            r = DatabaseConnection.getObjects(Token.class, "SELECT * FROM tokens WHERE tokens.CreationDate = (SELECT MAX(CreationDate) FROM tokens WHERE Username = ?)", username);
        } catch (NoSuchMethodException | SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        if (r == null || r.size() == 0)
            return null;

        return r.get(0);
    }
}
