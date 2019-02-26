package com.templateTools.pub.config;

import com.templateTools.entity.CreateInfo;
import com.templateTools.utils.ThreadLocalUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateCodeDataSource extends DataSource {
    @Override
    public Connection getConnection() {

        DataSource dataSource = new DataSource();
        CreateInfo createInfo = ThreadLocalUtil.getThreadLocal().get();

        if ("mysql".equals(createInfo.getDatabaseType())) {
            dataSource.setUrl("jdbc:mysql://" + createInfo.getDatabaseAdress() + ":" + createInfo.getDatabasePort() + "/information_schema?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        } else if ("oracle".equals(createInfo.getDatabaseType())) {
            dataSource.setUrl("oracle:thin:@" + createInfo.getDatabaseAdress() + ":" + createInfo.getDatabasePort() + ":orcl" + "信息先不填 以后再改");
            dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        }

        dataSource.setUsername(createInfo.getDatabaseUsername());
        dataSource.setPassword(createInfo.getDatabasePassword());

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
