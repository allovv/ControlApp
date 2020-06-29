package com.springapp.config;

import com.springapp.entities.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll() //Доступ разрешен всем пользователей по пути...
                    //.antMatchers("/registration").not().fullyAuthenticated() //Доступ только для не зарегистрированных пользователей
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

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles(Roles.ADMIN.name())
                        .build();
        return new InMemoryUserDetailsManager(user);
    }
}
