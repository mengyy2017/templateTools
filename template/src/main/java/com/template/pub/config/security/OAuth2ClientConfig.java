package com.template.pub.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import java.util.*;

@Configuration
public class OAuth2ClientConfig {

    @Value("${spring.security.oauth2.client.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Bean
    public ResourceOwnerPasswordResourceDetails resourceDetails(){
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setScope(Arrays.asList(scope));
        resourceDetails.setAccessTokenUri(accessTokenUri);
        return  resourceDetails;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(ResourceOwnerPasswordResourceDetails resourceDetails){
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails);

        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        provider.setAuthenticationHandler((resource, form, headers) -> {
            form.add("client_id", resource.getClientId());
            form.add("client_secret", resource.getClientSecret());
        });
//        AccessTokenProviderChain providerChain = new AccessTokenProviderChain(Arrays.asList(provider));
        oAuth2RestTemplate.setAccessTokenProvider(provider);

        return oAuth2RestTemplate;

    }

}
