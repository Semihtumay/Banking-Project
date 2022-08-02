package com.semihtumay.bankingproject.configs;

import com.semihtumay.bankingproject.services.LoginUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final PasswordEncoder passwordEncoder;
    final LoginUserDetailService loginUserDetailService;
    final JwtFilter jwtFilter;


    public SecurityConfig(PasswordEncoder passwordEncoder, LoginUserDetailService loginUserDetailService, JwtFilter jwtFilter) {

        this.passwordEncoder = passwordEncoder;
        this.loginUserDetailService = loginUserDetailService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(getPermitAll()).permitAll()
                .antMatchers(getUserRole()).hasRole("user")
                .antMatchers(getAdminRole()).hasRole("admin")
                .antMatchers(getCustomerAdmin_Role()).hasAnyRole("admin","customer")
                .and()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class );
        http.cors();


    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserDetailService).passwordEncoder(passwordEncoder);
    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    private String[] getUserRole(){
        String[] userRole={"/withdraw","/deposit"};
        return userRole;
    }
    private String[] getAdminRole(){
        String[] adminRole={"/user/register"};
        return adminRole;
    }

    private String[] getCustomerAdmin_Role(){
        String[] bothRole={
                "/category/list","/product/listbyCategory",
                "/product/search","/product/list/company"};
        return bothRole;

    }
    private String[] getPermitAll(){
        String[] permitAll={"/admin/register",
                "/user/application","/login"};
        return permitAll;
    }
}
