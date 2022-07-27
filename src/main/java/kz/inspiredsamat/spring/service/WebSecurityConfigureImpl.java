package kz.inspiredsamat.spring.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
@AllArgsConstructor
public class WebSecurityConfigureImpl extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/itransition", "/itransition/register").permitAll()
                .anyRequest().authenticated().and().formLogin().usernameParameter("email")
                .defaultSuccessUrl("/itransition")
                .and()
                .logout().logoutSuccessUrl("/itransition");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }
}