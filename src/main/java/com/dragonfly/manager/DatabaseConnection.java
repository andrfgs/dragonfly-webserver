package com.dragonfly.manager;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

/**
 * Contains the static methods for interfacing the DB and converting a table into an object.
 * Calls are made in form of SQL queries and each row resulting from that query will be an
 * object. For each row, each non null column value will be set on the objects equivalent
 * field (i.e. if there is a column with name X and the object has field with name X, that
 * field will be set according to the returned result for that row). If value does not match
 * field type, the method will return an exception. Any fields that were not found will not
 * be set and will be given their default Java value.
 * If the user wishes to map a column to a different field name, he can use the @DatabaseName(x)
 * annotation under the field, where x is the name of the column.
 */
public class DatabaseConnection {

    private static final String DATABASE_URL = "jdbc:mariadb://localhost/plants";
    private static final String USERNAME = "root";
    private static final String PASSWORD = null;

    public static ResultSet executeQuery(String query, Object... params) throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        ResultSet rs;

        if (params.length == 0)
        {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }
        else
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            int paramCtr = 1;
            for (Object o : params)
                stmt.setObject(paramCtr++, o);

            rs = stmt.executeQuery();
        }

        conn.close();
        return rs;
    }

    /**
     * Constructs objects from an SQL query using the provided class, creating an object
     * for each row and converting each row's column values to the corresponding class fields.
     * An empty query result will return an empty list.
     * @param c The Class from where the desired object derives from
     * @param query The SQL query
     * @param params The parameters in case parametrized SQL is used.
     * @param <T> The type of the Class
     * @return A list of objects constructed from the query result
     * @throws NoSuchMethodException If object has no default empty constructor.
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> List<T> getObjects(Class<T> c, String query, Object... params) throws NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> ctor = c.getConstructor();
        ResultSet rs;
        if (params.length == 0)
            rs = executeQuery(query);
        else
            rs = executeQuery(query, params);
        ResultSetMetaData meta = rs.getMetaData();

        int columns = meta.getColumnCount();
        Map<String, Field> fields = new HashMap<>();
        for (Field f : getAllClassFields(c)) {
            DatabaseName n = f.getAnnotation(DatabaseName.class);
            if (n == null)
                fields.put(f.getName().toLowerCase(), f);
            else
                fields.put(n.value().toLowerCase(), f);
            f.setAccessible(true);
        }


        List<T> createdObjs = new LinkedList<>();
        while (rs.next())
        {
            T newObject = ctor.newInstance();
            for (int i = 1; i < columns + 1; i++)
            {
                Object colValue = rs.getObject(i);

                if (colValue == null) continue;

                Field f = fields.get(meta.getColumnName(i).toLowerCase());

                if (f != null)
                    f.set(newObject, colValue);
            }

            createdObjs.add(newObject);
        }

        for (Field f : c.getDeclaredFields())
            f.setAccessible(false);

        return createdObjs;
    }

    /**
     * Does the same as <code>getObjects</code> but only returns the first object.
     * An empty query result will return <code>null</code>.
     * @param c The Class from where the desired object derives from
     * @param query The SQL query
     * @param params The parameters in case parametrized SQL is used.
     * @param <T> The type of the Class
     * @return The first object obtained by the list of all constructed objects from the query result
     * @throws NoSuchMethodException If object has no default empty constructor.
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <T> T getSingleObject(Class<T> c, String query, Object... params) throws InvocationTargetException, NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException {
        List<T> l = getObjects(c, query, params);

        if (l.size() == 0)
            return null;
        else
            return l.get(0);
     }

    /**
     * Returns all the fields in a Class, public or private, including fields of all
     * parent classes, in case of inheritance.
     * @param startClass The initial class to perform the search on.
     * @return All the fields in the Class hierarchy, including all interfaces and
     * extended classes.
     */
    private static Iterable<Field> getAllClassFields(Class<?> startClass) {

        List<Field> currentClassFields = Arrays.asList(startClass.getDeclaredFields());
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null) {
            List<Field> parentClassFields =
                    (List<Field>) getAllClassFields(parentClass);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }
}
