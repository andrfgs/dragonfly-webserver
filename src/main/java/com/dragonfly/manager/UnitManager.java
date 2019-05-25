package com.dragonfly.manager;

import com.dragonfly.entity.Unit;
import com.dragonfly.exception.UnitNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class UnitManager {

    public static Unit getUnit(int unitID) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, UnitNotFoundException {
        Unit u = DatabaseConnection.getSingleObject(Unit.class, "SELECT * FROM units WHERE UnitID = ?", unitID);

        if (u == null)
            throw new UnitNotFoundException("");

        return u;
    }

    public static void sensorLog(int unitID, float temperature, float humidity, boolean wasJustWatered, int sector) throws NoSuchMethodException, UnitNotFoundException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {
        getUnit(unitID);


    }
}
