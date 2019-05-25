package com.dragonfly.manager;

import com.dragonfly.entity.Plant;
import com.dragonfly.entity.Plantation;
import com.dragonfly.entity.Unit;
import com.dragonfly.exception.UnitNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantationManager {

    public static List<Plantation> getUnitPlantations(String username, int unidID)
            throws NoSuchMethodException, UnitNotFoundException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {
        // If unit does not exist, this will throw UnitNotFoundException
        Unit u = UnitManager.getUnit(unidID);
        if (!u.getOwner().equals(username))
            throw new UnitNotFoundException("");

        // Get all plantations and store in a temporary representation
        return DatabaseConnection.getObjects(
                Plantation.class,
                "SELECT * FROM plantations WHERE UnitID = ? AND SowedPlant IS NOT NULL", unidID);
    }

    // Returns all plants indexed by their name
    private static Map<String, Plant> getAllIndexedPlants()
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {
        // Get all plants registered in the db
        List<Plant> plants = PlantManager.getAllPlants();

        // Build index
        Map<String, Plant> plantsByName = new HashMap<>(plants.size());
        for (Plant p : plants)
            plantsByName.put(p.getName(), p);

        return plantsByName;

    }

}
