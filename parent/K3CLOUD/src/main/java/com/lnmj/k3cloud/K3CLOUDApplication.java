package com.lnmj.k3cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.lnmj.k3cloud.**",/* "com.lnmj.auth.**",*/"com.lnmj.common.**"})
@EnableAuthorizationServer
@EnableFeignClients(basePackages = {"com.lnmj.k3cloud.serviceProxy", "com.lnmj.account","com.lnmj.auth"})
@EnableTransactionManagement
public class K3CLOUDApplication  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(K3CLOUDApplication.class, args);

    }
    /*@Override//为了打包springboot项目
  protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/
}
