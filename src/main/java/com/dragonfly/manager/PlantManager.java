package com.dragonfly.manager;

import com.dragonfly.entity.Plant;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class PlantManager {


    public static List<Plant> getAllPlants() throws InvocationTargetException, NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException {
        return DatabaseConnection.getObjects(Plant.class, "SELECT * FROM plants");
    }
}

