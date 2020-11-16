package edu.cnm.deepdive.tunefull.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//  private final userService userService;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUri;
  @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
  private String clientId;

  //allows us to specify which endpoints need security (i.e. which ones need
  // you to be logged in - so not discover)
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests((auth) ->
            //set up so that everyone can get to discover - will take some
            //messing with the endpoints for the antPattern
            auth.antMatchers(HttpMethod.GET, "clips/**").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer().jwt();
//        .jwtAuthenticationConverter(userService);
  }
}
