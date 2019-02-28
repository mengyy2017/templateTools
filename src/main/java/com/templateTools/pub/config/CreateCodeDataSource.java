package com.templateTools.pub.config;

import com.templateTools.entity.CreateInfo;
import com.templateTools.utils.ThreadLocalUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateCodeDataSource extends DataSource {

    private static ConcurrentHashMap<String, LinkedList<ConnHolder>> dataSourceConHM = new ConcurrentHashMap<>();

    static {
        new Thread(() -> {
            while (true) {
                dataSourceConHM.values().forEach(linkedList -> {
                    try {
                        for(int i = 0; i < linkedList.size(); i++){
                            ConnHolder connHolder = linkedList.get(i);
                            System.out.println("连接个数：" + linkedList.size());
                            System.out.println("索引：" + i);
                            System.out.println(connHolder.get().isClosed());
                            System.out.println(connHolder.getLastUseTime());
                            System.out.println("----------------------------");
                        }
                        Thread.sleep(20000);
                        ConnHolder connHolder = linkedList.get(1);
                        if(connHolder != null) {
                            connHolder.get().close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
    }

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

        for(int i = 0; i < 5; i++){
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

