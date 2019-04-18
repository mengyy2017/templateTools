package com.authorization.pub.config.security;

import com.common.util.BuildUtil;
import com.grpc.client.SystemGrpcClient;
import com.grpc.proto.datasource.DatasourceMsg;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthoriServerConf extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private SystemGrpcClient systemGrpcClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(new InMemoryTokenStore());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        DatasourceMsg datasourceMsg = systemGrpcClient.getDatasource(DatasourceMsg.newBuilder().build());

        DataSource dataSource = BuildUtil.newAndSet0(DataSource::new
                , new String[]{datasourceMsg.getUrl(), datasourceMsg.getDriverClassName(), datasourceMsg.getUsername(), datasourceMsg.getPassword()}
                , DataSource::setUrl, DataSource::setDriverClassName, DataSource::setUsername, DataSource::setPassword);

        clients.withClientDetails(new JdbcClientDetailsService(dataSource));

//        clients.inMemory()
//                .withClient("createCodeClient")
////                .resourceIds("createCode")
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("select")
////                .authorities("ROLE_ADMIN")
//                .secret(new RawEncoder().encode("secret"));

                // .and()
                // .withClient("createCodeClient")
                // .resourceIds("createCode")
                // .authorizedGrantTypes("client_credentials", "refresh_token")
                // .scopes("select")
                // .authorities("client")
                // .secret("123456")
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

}
