package edu.cnm.deepdive.tunefull.configuration;

import edu.cnm.deepdive.tunefull.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
/**
 * User entity holds the data model for User data and functions in the TuneFull server.
 *
 * @author Robert Dominugez
 * @author Roderick Frechette
 * @author Laura Steiner
 *
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUri;
  @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
  private String clientId;

  public SecurityConfiguration(UserService userService) {
    this.userService = userService;
  }

  //allows us to specify which endpoints need security (i.e. which ones need
  // you to be logged in - so not discover)
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests((auth) ->
            // TODO check to make sure this works; set up on client-side
            //define permit all/anonymous on client-side too
            auth.antMatchers(HttpMethod.GET, "clips/discovery").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer().jwt()
        .jwtAuthenticationConverter(userService);
  }

  //basic spring decoder only validates dates, so to get more validation we
  //need to create our own
  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder decoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
    OAuth2TokenValidator<Jwt> audienceValidator =
        new JwtClaimValidator<List<String>>(JwtClaimNames.AUD, (aud) -> aud.contains(clientId));
    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
    OAuth2TokenValidator<Jwt> combinedValidator =
        new DelegatingOAuth2TokenValidator<Jwt>(withIssuer, audienceValidator);
    decoder.setJwtValidator(combinedValidator);
    return decoder;
  }

}
