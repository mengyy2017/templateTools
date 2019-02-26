package com.templateTools.pub.config;

import com.templateTools.entity.CreateInfo;
import com.templateTools.utils.ThreadLocalUtil;
import org.apache.tomcat.jdbc.pool.DataSource;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateCodeDataSource extends DataSource {

    private ConcurrentHashMap<String, LinkedList<Connection>> dataSourceConHM = new ConcurrentHashMap<>();

    @Override
    public Connection getConnection() {

        try {
            DataSource dataSource = new DataSource();
            CreateInfo createInfo = ThreadLocalUtil.getCreateInfoThreadLocal().get();

            if (createInfo != null) {
                if ("mysql".equals(createInfo.getDatabaseType())) {
                    dataSource.setUrl("jdbc:mysql://" + createInfo.getDatabaseAdress() + ":" + createInfo.getDatabasePort() + "/information_schema?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
                    dataSource.setDriverClassName("com.mysql.jdbc.Driver");

                } else if ("oracle".equals(createInfo.getDatabaseType())) {
                    dataSource.setUrl("oracle:thin:@" + createInfo.getDatabaseAdress() + ":" + createInfo.getDatabasePort() + ":orcl" + "信息先不填 以后再改");
                    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
                }

                dataSource.setUsername(createInfo.getDatabaseUsername());
                dataSource.setPassword(createInfo.getDatabasePassword());
                // 这里多次getConnection可以得到多个connection吗？？？？
                // 而且这里是第一次创建 创建完还要用 所以put不能在这里 应该在代理对象的close方法时
                dataSourceConHM.put("primaryKey!!!!!", Stream.of(dataSource.getConnection()).collect(Collectors.toCollection(LinkedList::new)));
                // 这里不应该直接返回connection 返回他的一个代理
                return dataSource.getConnection();

            } else {
                String authToken = ThreadLocalUtil.getRequestThreadLocal().get().getHeader("authToken");
                return dataSourceConHM.get(authToken).pop();
            }





        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
