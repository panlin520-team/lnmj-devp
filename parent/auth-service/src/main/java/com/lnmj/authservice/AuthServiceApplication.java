package com.lnmj.authservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
/*@SpringBootApplication
@EnableEurekaClient*/
@EnableAutoConfiguration
/*@ComponentScan("com.**")*/
@ComponentScan(basePackages = {"com.lnmj.authservice.**", /*"com.lnmj.auth.**",*/"com.lnmj.common.**"})
@MapperScan("com.lnmj.authservice.Dao")
@EnableFeignClients(basePackages = {"com.lnmj.authservice.serviceProxy"})
public class AuthServiceApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    /*@Override//为了打包springboot项目
  protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/
}
