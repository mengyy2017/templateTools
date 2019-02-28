package com.templateTools.pub.config;

import java.sql.Connection;
import java.util.function.Function;

public class ConnHolder {

    private Connection connection;

    private long lastUseTime;

    private static long idleTime = 20 * 1000;

    public ConnHolder(Connection connection) {
        this.connection = connection;
        lastUseTime = System.currentTimeMillis();
    }



    public static ConnHolder createHolder(Function<Connection, ConnHolder> function, Connection conn) {
        return function.apply(conn);
    }

    public Connection get(){
        return this.connection;
    }

    public long getLastUseTime() {
        return lastUseTime;
    }
}
