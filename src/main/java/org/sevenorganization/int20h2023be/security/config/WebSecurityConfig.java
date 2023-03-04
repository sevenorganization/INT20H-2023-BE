package org.sevenorganization.int20h2023be.security.config;

import org.sevenorganization.int20h2023be.security.exceptionhandling.DefaultAccessDeniedHandler;
import org.sevenorganization.int20h2023be.security.exceptionhandling.DefaultAuthenticationEntryPoint;
import org.sevenorganization.int20h2023be.security.filter.JwtAuthorizationFilter;
import org.sevenorganization.int20h2023be.security.oauth2.DefaultOAuth2UserService;
import org.sevenorganization.int20h2023be.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import org.sevenorganization.int20h2023be.security.oauth2.handler.OAuth2AuthenticationFailureHandler;
import org.sevenorganization.int20h2023be.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import org.sevenorganization.int20h2023be.security.service.DefaultUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;
    private final DefaultUserDetailsService defaultUserDetailsService;
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


    // TODO: try to avoid HttpCookieOAuth2AuthorizationRequestRepository and JwtAuthorizationFilter bean methods
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf()
                    .disable()
                .httpBasic()
                    .disable()
                .formLogin()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(defaultAccessDeniedHandler)
                    .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                    .and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                .requestMatchers("/auth/forgotPassword", "/auth/verifyRegistration").permitAll()
                .requestMatchers("/forgot-password").permitAll()
                .requestMatchers("/test/all").permitAll()
                .requestMatchers("/test/authenticated").authenticated()
                .anyRequest()
                    .authenticated()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(defaultOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                    .and()
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(defaultUserDetailsService)
                .passwordEncoder(passwordEncoder()).and()
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000/")
                                .allowedHeaders("*")
                                .allowedMethods(
                                        HttpMethod.GET.name(),
                                        HttpMethod.POST.name(),
                                        HttpMethod.PUT.name(),
                                        HttpMethod.DELETE.name()
                                );
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
