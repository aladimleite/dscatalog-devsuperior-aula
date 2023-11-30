package com.devsuperior.dscatalog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//aula 03-26
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //aula 03-27 liberar H2 ambiente de teste
    @Autowired
    private Environment env;

    @Autowired
    private JwtTokenStore tokenStore;

    //aula 03-27 liberar H2 ambiente de teste
    private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };

    private static final String[] OPERATOR_OR_ADMIN = { "/products/**", "/categories/**" };

    private static final String[] ADMIN = { "/users/**" }; 

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //02m52s com isso aqui o Resource server (vide figura) será capaz de decodificar o token e verificar se é válido
        resources.tokenStore(tokenStore);        
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //aula 03-27 liberar H2
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();            
        }

        http.authorizeRequests()
        .antMatchers(PUBLIC).permitAll()
        .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll()
        .antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN") //no banco é obrigatório o prefixo "ROLE". Ex.: ROLE_OPERATOR, ROLE_ADMIN
        .antMatchers(ADMIN).hasRole("ADMIN")
        .anyRequest().authenticated(); //para qualquer outra rota deve estar logado

    }
    
    
}
