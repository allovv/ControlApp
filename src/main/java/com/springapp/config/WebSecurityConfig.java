package com.springapp.config;

import com.springapp.entities.Roles;
import com.springapp.services.UserDetailsServiceImpl;
import com.springapp.services.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.springapp.controllers.common.RedirectController.SUCCESS_AUTH_URL;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //-------------------------------------------------------------
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private UserRepoService userRepoService;

    //-------------------------------------------------------------
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority(Roles.ADMIN.name())
                    .antMatchers("/user/**").hasAuthority(Roles.USER.name())
                    .antMatchers("/", "/registration", "/static/**").permitAll() //Доступ разрешен всем пользователям
                    .anyRequest().authenticated() //Все остальные страницы требуют аутентификации
                    .and()
                .formLogin()
                    .loginPage("/login") //страница для входа в систему
                    .permitAll() //разрешаем всем доступ
                    .defaultSuccessUrl(SUCCESS_AUTH_URL, true) //Перенарпавление после успешного входа
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/")
                    .and()
                .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false); //Убивает прошлую сессию без предупреждения //TODO: потом изменить работу с сессиями
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        userRepoService.registerAdmin(); //добавление администратора в базу данных

        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}