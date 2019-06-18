package com.dragonfly.manager;

import com.dragonfly.entity.NotificationLog;
import com.dragonfly.entity.SensorLog;
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

    public static void createSensorLog(SensorLog log) throws NoSuchMethodException, UnitNotFoundException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {
        getUnit(log.getUnitID());

        DatabaseConnection.executeQuery("CALL CreateSensorLog(?, ?, ?, ?, ?, ?)",
                System.currentTimeMillis(), log.getUnitID(), log.getTemperature(), log.getHumidity(), log.wasJustWatered(), log.getSector());
    }

    public static void createNotificationLog(NotificationLog log) throws NoSuchMethodException, UnitNotFoundException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException {
        getUnit(log.getUnitID());

        DatabaseConnection.executeQuery("CALL CreateNotificationLog(?, ?, ?, ?, ?, ?)",
                System.currentTimeMillis(), log.getUnitID(), log.getContent(), log.getLevel(), false, log.getSector());
    }

}
