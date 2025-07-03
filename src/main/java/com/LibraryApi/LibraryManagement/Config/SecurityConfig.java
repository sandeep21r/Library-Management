package com.LibraryApi.LibraryManagement.Config;

import com.LibraryApi.LibraryManagement.Exception.CustomAuthenticationEntryPoint;
import com.LibraryApi.LibraryManagement.Filters.JwtAuthenticationFilter;
import com.LibraryApi.LibraryManagement.Filters.RequestLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private  final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final RequestLoggingFilter requestLoggingFilter;


    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,CustomAuthenticationEntryPoint customAuthenticationEntryPoint,RequestLoggingFilter requestLoggingFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint=customAuthenticationEntryPoint;
        this.requestLoggingFilter=requestLoggingFilter;

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/**")  // optional but more explicit
                .csrf(AbstractHttpConfigurer::disable)

               .authorizeHttpRequests(auth -> auth

                       //USER
                       .requestMatchers("/library/user/registerUser").hasAnyRole("ADMIN", "SUPERADMIN")
//                       .requestMatchers("/library/user/registerToTenant").hasAnyRole("ADMIN", "SUPERADMIN")
                       .requestMatchers("/library/user/list").hasAnyRole("ADMIN", "SUPERADMIN")
                       .requestMatchers("/library/user/login").permitAll()
                       .requestMatchers("/library/user/{id}").hasAnyRole("ADMIN", "SUPERADMIN")
//                       .requestMatchers("/library/user/userTenant/list").hasAnyRole("ADMIN", "SUPERADMIN")
//                       .requestMatchers("/library/user/userTenant/{id}").hasAnyRole("ADMIN", "SUPERADMIN")

                       //TENANT
                       .requestMatchers("/library/tenant/registerTenant").hasRole("SUPERADMIN")
                       .requestMatchers("/library/tenant/list").hasRole("SUPERADMIN")
//                       .requestMatchers("/library/tenant/list").any()

                       //BOOK
                       .requestMatchers("/library/book/create").hasRole("ADMIN")


                       .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(requestLoggingFilter,JwtAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

