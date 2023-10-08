package com.example.tp.config;

import com.example.tp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/signup", "/private/pre-cbt", "/private/cbt").permitAll()
                // 다른 URL 권한 설정
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .passwordParameter("pw")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/denied")
                .and()
                // CSRF 비활성화
                .csrf()
                .ignoringAntMatchers("/login", "/logout");
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

}