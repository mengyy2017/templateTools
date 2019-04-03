package com.template.pub.commModel;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.function.Function;

public class ConnHolder {

    private Connection connection;

    private LocalDateTime lastUseTime;

    // 秒
    private static long idleTime = 900;

    public ConnHolder(Connection connection) {
        this.connection = connection;
        lastUseTime = LocalDateTime.now();
    }

    public static ConnHolder createHolder(Function<Connection, ConnHolder> function, Connection conn) {
        return function.apply(conn);
    }

    public Connection get(){
        return this.connection;
    }

    public LocalDateTime getLastUseTime() {
        return lastUseTime;
    }

    public static long getIdleTime() {
        return idleTime;
    }
}
