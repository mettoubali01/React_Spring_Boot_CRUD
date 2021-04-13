package com.example.crud_react_js.configuration;

import com.example.crud_react_js.auth.UserDetailService;
import com.example.crud_react_js.jwt.JwtConfig;
import com.example.crud_react_js.jwt.JwtSecretKey;
import com.example.crud_react_js.jwt.JwtTokenVerifier;
import com.example.crud_react_js.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.example.crud_react_js.configuration.UserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final CorsFilter corsFilter;

    public WebSecurityConfiguration(JwtConfig jwtConfig,
                                    JwtSecretKey jwtSecretKey,
                                    PasswordEncoder passwordEncoder,
                                    UserDetailService userDetailService,
                                    CorsFilter corsFilter) {
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailService;
        this.corsFilter = corsFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, SessionManagementFilter.class)
                .csrf().disable()
                //stateless authentication
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(jwtConfig, jwtSecretKey, authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, jwtSecretKey), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").permitAll()/*hasAnyRole(ADMIN.name(), STUDENT.name())*/
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try{
            auth.authenticationProvider(daoAuthenticationProvider());

        }catch (Exception e){
            System.out.println("sadsdsdsds " + e);
        }
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    /* @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails mariemUser = User.builder()
                .username("mariem")
                .password(passwordEncoder.encode("qqq"))
                *//*.roles(STUDENT.name())*//*
                .authorities(STUDENT.getSimpleGrantedAuthorities())
                .build();

        UserDetails mohamedUser = User.builder()
                .username("mohamed")
                .password(passwordEncoder.encode("qqq"))
                *//*.roles(ADMIN.name())*//*
                .authorities(ADMIN.getSimpleGrantedAuthorities())
                .build();

        UserDetails moohUser = User.builder()
                .username("mooh")
                .password(passwordEncoder.encode("qqq"))
                *//*.roles(ADMINTRAINEE.name())*//*
                .authorities(ADMINTRAINEE.getSimpleGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                mariemUser,
                mohamedUser,
                moohUser
        );
    }*/
}
