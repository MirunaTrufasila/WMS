package com.lts.config;

import com.lts.config.auth.CustomAuthenticationFailureHandler;
import com.lts.config.auth.CustomAuthenticationProvider;
import com.lts.config.auth.CustomPasswordEncoder;
import com.lts.config.auth.UserDetailsServiceImpl;
import com.lts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorBean")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.lts.repository")
public class AppConfig {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomPasswordEncoder passwordEncoder;

    public AppConfig(UserRepository userRepository,
                     UserDetailsServiceImpl userDetailsService,
                     CustomPasswordEncoder customPasswordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = customPasswordEncoder;
    }

    @Bean
    public AuditorAware auditorBean() {
        return new SpringSecurityAuditorAware();
    }

    /**
     * Verify authentication
     *
     * @return the authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(this.userRepository);
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * By default, the scheduler uses a single-thread executor com.lts.LiteTechnologiesSoftware.service => we are gonna use a
     * thread pool.
     *
     * @return the task scheduler
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        return new ConcurrentTaskScheduler(executorService);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean(name = "asyncThreadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    /**
     * Utility for converting DTO and entity and vice versa
     *
     * @return model mapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // make mapper match by exact properties names, without it, for example, idCustomer matched customer
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
