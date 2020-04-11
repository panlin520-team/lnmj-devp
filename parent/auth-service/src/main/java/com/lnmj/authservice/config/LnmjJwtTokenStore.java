package com.lnmj.authservice.config;

import com.lnmj.authservice.common.RSAUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @Auther: panlin
 * @Date: 2019/4/26 15:24
 * @Description:
 */
@Configuration
public class LnmjJwtTokenStore {
    private static final String CLASSPATH = "classpath:.*";
    @Value("${oauth2.key.priKeyPath}")
    private String priKeyPath;
    @Value("${oauth2.key.pubKeyPath}")
    private String pubKeyPath;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
      /*  //注意此处需要相应的jks文件
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("fzp-jwt.jks"), "fzp123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("fzp-jwt"));*/
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 对称加密
        KeyPair keyPair = null;
        try {
            Resource resource;
   /*         URL url = null;
            if (priKeyPath.matches(CLASSPATH)) {
                url = this.getClass().getClassLoader().getResource(priKeyPath.replaceAll("classpath:", ""));
            } else {
                url = this.getClass().getClassLoader().getResource(priKeyPath);
            }*/
            File filepri = new File(priKeyPath);
            resource = new FileSystemResource(filepri.getPath());
            PrivateKey priKey = RSAUtils.readPriKey(resource);
/*            if (pubKeyPath.matches(CLASSPATH)) {
                url = this.getClass().getClassLoader().getResource(pubKeyPath.replaceAll("classpath:", ""));
            } else {
                url = this.getClass().getClassLoader().getResource(pubKeyPath);
            }*/
            File filepub = new File(pubKeyPath);
            resource = new FileSystemResource(filepub.getPath());
            PublicKey pubKey = RSAUtils.readPubKey(resource);
            keyPair = new KeyPair(pubKey, priKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            /* log.error(e.getMessage(), e);*/
        }
        assert keyPair != null;
        accessTokenConverter.setKeyPair(keyPair);
        return accessTokenConverter;

        /* return converter;*/
    }
}
