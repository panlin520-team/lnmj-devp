package com.lnmj.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;


/**
 * @Auther: panlin
 * @Date: 2019/4/25 12:30
 * @Description:
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Resource
    private  TokenStore tokenStore;
    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() //将客户端的信息存储在内存中
                .withClient("user-service") //创建了一个Client为"user-service"的客户端
                .resourceIds("lnmj-account","lnmj-product","lnmj-authority")
                .secret("123456")
                .scopes("service") //客户端的域
                .authorizedGrantTypes("refresh_token", "password","client_credentials") //配置类验证类型为 refresh_token和password
                .accessTokenValiditySeconds(600); //10min过期
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
    }

    @Resource
    private AuthenticationManager authenticationManager;

    public static void main(String[] args) throws Exception{
/*        String s =new String(Base64Utils.encode("user-service:123456".getBytes()));
        System.out.println(s);*/
        System.out.println(new BCryptPasswordEncoder().encode("a1234567"));
    }
}