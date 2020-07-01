package com.springapp.config;

import com.springapp.entities.Roles;
import com.springapp.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //-------------------------------------------------------------
    private UserDetailsServiceImpl userDetailsServiceImpl;
    //-------------------------------------------------------------

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin").hasRole(Roles.ADMIN.name())
                    .antMatchers("/", "/registration").permitAll() //Доступ разрешен всем пользователей по пути...
                    .anyRequest().authenticated() //Все остальные страницы требуют аутентификации
                    .and()
                .formLogin()
                    .loginPage("/login") //страница для входа в систему
                    .permitAll() //разрешаем всем доступ
                    .defaultSuccessUrl("/projects", false) //Перенарпавление на главную страницу после успешного входа
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsServiceImpl);
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles(Roles.ADMIN.name());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}