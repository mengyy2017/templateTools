package com.templateTools.utils;

import com.templateTools.entity.model.CreateInfo;
import com.templateTools.pub.commModel.ConnHolder;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateConnUtil extends DataSource {

    private static ConcurrentHashMap<String, LinkedList<ConnHolder>> dataSourceConHM = new ConcurrentHashMap<>();

    @Override
    public Connection getConnection() {
        try {
            String authToken = ThreadLocalUtil.getAuthToken();
            LinkedList<ConnHolder> someConnLinkedList = dataSourceConHM.get(Optional.ofNullable(authToken).orElse(""));

            if (authToken == null || someConnLinkedList == null)
                return createConnection();
            else
                return popConnection(authToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection createConnection() throws Exception {

        CreateInfo createInfo = ThreadLocalUtil.getCreateInfoThreadLocal().get();

        DataSource dataSource = createDataSource(createInfo);

        String authToken = createInfo.toString();

        for(int i = 0; i < 2; i++){
            Connection conn = dataSource.getConnection();
            if (dataSourceConHM.get(authToken) == null)
                dataSourceConHM.put(authToken, Stream.of(creHolder(conn)).collect(Collectors.toCollection(LinkedList::new)));
            else
                dataSourceConHM.get(authToken).addFirst(creHolder(conn));
        }

        return popConnection(authToken);
    }

    private Connection popConnection(String authToken) throws Exception {
//        List<Connection> syncList = Collections.synchronizedList(linkedList);

        LinkedList<ConnHolder> linkedList = dataSourceConHM.get(authToken);

        synchronized (this) {
            while (linkedList.size() == 0){
                this.wait();
            }
            return getConncetionProxy(linkedList.pop().get(), authToken);
        }
    }

    private Connection getConncetionProxy(Connection conn, String token) {
        return (Connection)Proxy.newProxyInstance(conn.getClass().getClassLoader(),
                conn.getClass().getInterfaces(), (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        dataSourceConHM.get(token).addFirst(creHolder(conn));
                        this.notify();
                        return null;
                    }
                    return method.invoke(conn, args);
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

    public static ConcurrentHashMap<String, LinkedList<ConnHolder>> getDataSourceConHM() {
        return dataSourceConHM;
    }

    public ConnHolder creHolder(Connection conn) {
        return ConnHolder.createHolder(ConnHolder::new, conn);
    }

}

