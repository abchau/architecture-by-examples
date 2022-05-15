package com.abchau.archexamples.hdddeda.subscribe.spring.config;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Log4j2
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {
        log.info("securityWebFilterChain()...invoked");

        return http
                .authorizeExchange()
                // .pathMatchers("/").permitAll()
                // .pathMatchers("/subscribe").permitAll()
                // .pathMatchers("/resend").permitAll()
                // .pathMatchers("/vertify").permitAll()
				// // 
                // .pathMatchers("/signin").permitAll()
                // // techSpecRoute()
                // .pathMatchers("/retrieve").hasAuthority("ROLE_USER")
                // .pathMatchers("/create").hasAuthority("ROLE_USER")
                // .pathMatchers("/import").hasAuthority("ROLE_USER")
                // // apiRoute()
                // .pathMatchers("/api").hasAuthority("ROLE_USER")
                // .anyExchange().authenticated()
                .anyExchange().permitAll()
                .and().httpBasic()

                .and().formLogin().disable()

				// .and()
				// .oauth2Login()
				// .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/protected"))
                .csrf().disable()
                .logout().disable()
                //.authenticationManager(myReactiveAuthenticationManager)
                //.securityContextRepository(myWebSessionServerSecurityContextRepository)
                .exceptionHandling()
                .and()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        log.info("userDetailsService()...invoked");

        final UserDetails defaultUser = User
                .withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();

        return new MapReactiveUserDetailsService(defaultUser);
    }


    // @Bean
    // public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
    //     return new DbUserService(userRepository);
    // }


}

// public class DbUserService implements OAuth2UserService<OidcUserRequest, OidcUser>, ReactiveUserDetailsService {
//     private static final String CLAIM_NAME = "name";
//     private static final String CLAIM_EMAIL = "email";

//     private UserRepository userRepository;

//     public DbUserService(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     @Override
//     public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
//         Map<String, Object> metadata = oidcUserRequest.getIdToken().getClaims();
//         String email = (String) metadata.get(CLAIM_EMAIL);
//         String name = (String) metadata.get(CLAIM_NAME);

//         Flux<UserEntity> userLookup = userRepository.findByEmail(email);
//         UserEntity userEntity = userLookup.blockFirst();

//         if (userEntity == null) {
//             userEntity = new UserEntity();
//             userEntity.setEmail(email);
//         }

//         userEntity.setName(name);
//         userEntity = userRepository.save(userEntity).block();

//         return new UserDetails(userEntity.getValue(), oidcUserRequest);
//     }

//     @Override
//     public Mono<org.springframework.security.core.userdetails.UserDetails> findByUsername(String username) {
//         return null; // not used
//     }
// }