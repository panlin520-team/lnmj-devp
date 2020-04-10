package com.lnmj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.lnmj.account.**", /*"com.lnmj.auth.**",*/"com.lnmj.common.**"})
@EnableFeignClients(basePackages = {"com.lnmj.account.serviceProxy", "com.lnmj.auth"})
@EnableAuthorizationServer
@EnableTransactionManagement
@EnableScheduling
public class AccountApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    /*@Override//为了打包springboot项目
  protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/
}
