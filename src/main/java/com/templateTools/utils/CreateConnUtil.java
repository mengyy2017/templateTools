package com.templateTools.utils;

import com.templateTools.entity.model.CreateInfo;
import com.templateTools.pub.commModel.ConnHolder;
import com.templateTools.pub.common.Consts;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateConnUtil extends DataSource {

    private static ConcurrentHashMap<String, LinkedList<ConnHolder>> dataSourceConHM = new ConcurrentHashMap<>();

    @Override
    public Connection getConnection() {
        try {
            String authToken = ThreadLocalUtil.getAuthToken();

//            if(Consts.LOGIN_CHEK_URL.equals(ThreadLocalUtil.getRequestThreadLocal().get().getRequestURI())) {
//                LinkedList<ConnHolder> logiConn = dataSourceConHM.get(Consts.LOGI_CONN);
//                if (logiConn == null || logiConn.size() == 0) {
//                   return getLogiConn();
//                }
//            }
            return popConnection(authToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createPutConn(CreateInfo createInfo) throws Exception {

        DataSource dataSource = createDataSource(createInfo);
        String authToken = createInfo.toString();

        for(int i = 0; i < 2; i++){
            Connection conn = dataSource.getConnection();
            if (dataSourceConHM.get(authToken) == null)
                dataSourceConHM.put(authToken, Stream.of(creHolder(conn)).collect(Collectors.toCollection(LinkedList::new)));
            else
                dataSourceConHM.get(authToken).addFirst(creHolder(conn));
        }

    }

    private Connection popConnection(String authToken) throws Exception {
//        List<Connection> syncList = Collections.synchronizedList(linkedList);

        authToken = authToken == null ? ThreadLocalUtil.getCreateInfoThreadLocal().get().toString() : authToken;

       if (dataSourceConHM.get(authToken) == null)
            createPutConn(CreateInfo.toCreateInfo(authToken));

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

    public Connection getLogiConn() throws Exception {
        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/security?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("1");
        dataSourceConHM.put(Consts.LOGI_CONN, new LinkedList(Arrays.asList(dataSource.getConnection())));
        return popConnection(Consts.LOGI_CONN);
    }

    public static ConcurrentHashMap<String, LinkedList<ConnHolder>> getDataSourceConHM() {
        return dataSourceConHM;
    }

    public ConnHolder creHolder(Connection conn) {
        return ConnHolder.createHolder(ConnHolder::new, conn);
    }

}

