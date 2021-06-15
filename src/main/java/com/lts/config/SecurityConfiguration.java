package com.lts.config;

import com.lts.config.auth.AuthenticationSuccessHandlerImpl;
import com.lts.config.auth.CustomLogoutSuccessHandler;
import com.lts.config.auth.CustomWebAuthenticationDetailsSource;
import com.lts.config.auth.UserDetailsServiceImpl;
import com.lts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final UserRepository userRepository;
    private final CustomWebAuthenticationDetailsSource authenticationDetailsSource;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final String redirectPath;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfiguration(
            UserRepository userRepository,
            CustomLogoutSuccessHandler logoutSuccessHandler,
            CustomWebAuthenticationDetailsSource authenticationDetailsSource,
            DaoAuthenticationProvider daoAuthenticationProvider,
            AuthenticationFailureHandler authenticationFailureHandler,
            @Value("${redirect-after-login}") String redirectPath,
            UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationDetailsSource = authenticationDetailsSource;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.redirectPath = redirectPath;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js, html, css}")
                .antMatchers("/node_modules/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().disable()
                .authorizeRequests()
                .antMatchers(
                        "/*.ico",
                        "/node_modules/**",
                        "/app/**/*.{js,html, css}",
                        "/swagger-resources/configuration/ui",
                        "/swagger-ui.html",
                        "/login*",
                        "/auth/**",
                        "/images/**",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandlerImpl(userRepository, redirectPath))
                .authenticationDetailsSource(authenticationDetailsSource)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .rememberMe()
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(24 * 60 * 60) // 24 hrs
                .userDetailsService(userDetailsService)
                .and()
                .httpBasic();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .invalidSessionUrl("/login")
                .maximumSessions(1).expiredUrl("/login?expired")
                .and().sessionFixation().newSession();

        http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
    }
}