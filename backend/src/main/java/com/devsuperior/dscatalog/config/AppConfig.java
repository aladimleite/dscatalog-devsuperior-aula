package com.devsuperior.dscatalog.config;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {

    //'jwt.secret' is an unknown property.
    //@Value("${jwt.secret}") //aula 03-24 variável definida na classe application.properties
    //private String jwtSecret;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 

    //aula 03-23 0051
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
	    tokenConverter.setSigningKey("MY-JWT-SECRET"); //aula 03-24 0504
        //tokenConverter.setSigningKey(jwtSecret);
	    return tokenConverter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }      
}
