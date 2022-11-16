package com.eazyschool.config;

import com.eazyschool.constants.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    String[] permittedEndPoints = {"/public/**", "/h2-console/**"};
    String[] authenticatedEndPoints = {"/authenticated"};
    String[] csrfIgnoredEndPoints = {"/saveMsg", "/h2-console/**"};
    String[] adminSpecificEndPoints = {"/admin/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers(csrfIgnoredEndPoints).and().authorizeRequests().mvcMatchers(authenticatedEndPoints).authenticated()
                .mvcMatchers(adminSpecificEndPoints).hasRole(Roles.ADMIN.toString())
                .mvcMatchers(permittedEndPoints).permitAll().and().formLogin().defaultSuccessUrl("/authenticated/dashboard").failureUrl("/login?error=true")
                .and().logout().logoutSuccessUrl("/public/logout").invalidateHttpSession(true).and().httpBasic();
        http.headers().frameOptions().disable();
        return http.build();
    }
}
