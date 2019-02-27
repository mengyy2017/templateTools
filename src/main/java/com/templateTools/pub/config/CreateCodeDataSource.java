package com.templateTools.pub.config;

import com.templateTools.entity.CreateInfo;
import com.templateTools.utils.ThreadLocalUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateCodeDataSource extends DataSource {

    private static ConcurrentHashMap<String, LinkedList<Connection>> dataSourceConHM = new ConcurrentHashMap<>();

    @Override
    public Connection getConnection() {

        try {

            String authToken = ThreadLocalUtil.getAuthToken();
            LinkedList<Connection> someConnLinkedList = dataSourceConHM.get(authToken);

            if (authToken == null || someConnLinkedList == null)
                return createConnection();
            else
                return popConnection(someConnLinkedList, authToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection popConnection(LinkedList<Connection> linkedList, String authToken) throws Exception {

//        List<Connection> syncList = Collections.synchronizedList(linkedList);

        synchronized (this) {
            while (linkedList.size() == 0){
                this.wait();
            }

            return getConncetionProxy(linkedList.pop(), authToken);
        }
    }

    private Connection createConnection() throws SQLException {

        CreateInfo createInfo = ThreadLocalUtil.getCreateInfoThreadLocal().get();

        DataSource dataSource = createDataSource(createInfo);
        Connection connection = dataSource.getConnection();

        return getConncetionProxy(connection, createInfo.toString());
    }

    private Connection getConncetionProxy(Connection connection, String token) {
        return (Connection)Proxy.newProxyInstance(connection.getClass().getClassLoader(), connection.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())){
                        dataSourceConHM.put(token, Stream.of(connection).collect(Collectors.toCollection(LinkedList::new)));
                        this.notifyAll();
                        return null;
                    }
                    return method.invoke(connection, args);
                });
    }

    public DataSource createDataSource(CreateInfo createInfo){
        DataSource dataSource = new DataSource();

        if ("mysql".equals(createInfo.getDatabaseType())) {
            dataSource.setUrl("jdbc:mysql://" + createInfo.getDatabaseAdress() + ":" + createInfo.getDatabasePort() + "/information_schema?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        } else if ("oracle".equals(createInfo.getDatabaseType())) {
            dataSource.setUrl("oracle:thin:@" + createInfo.getDatabaseAdress() + ":" + createInfo.getDatabasePort() + ":orcl" + "信息先不填 以后再改");
            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        }

        dataSource.setUsername(createInfo.getDatabaseUsername());
        dataSource.setPassword(createInfo.getDatabasePassword());

        return dataSource;
    }

    public static ConcurrentHashMap<String, LinkedList<Connection>> getDataSourceConHM() {
        return dataSourceConHM;
    }

}
