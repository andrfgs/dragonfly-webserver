package com.dragonfly.util;

import java.util.logging.Logger;

public class DefaultLogger {

    public static Logger get() {
        return Logger.getLogger("org.glassfish.jersey.logging.LoggingFeature");
    }
}
