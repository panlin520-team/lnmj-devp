package com.lnmj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.lnmj.store.**",/*"com.lnmj.auth.**",*/"com.lnmj.common.**"})
@EnableFeignClients(basePackages = {"com.lnmj.store.serviceProxy","com.lnmj.auth"})
@EnableAuthorizationServer
public class StoreApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	/*@Override//为了打包springboot项目
  protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/
}
