package br.com.luizcanassa.projetintegrador2.config;

import br.com.luizcanassa.projetintegrador2.filters.DashboardFilter;
import br.com.luizcanassa.projetintegrador2.filters.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/resources/**",
                                "/static/**",
                                "/js/**",
                                "/css/**",
                                "/img/**",
                                "/json/**",
                                "/login",
                                "/",
                                "/orders-table/**"
                        ).permitAll()
                        .requestMatchers("/dashboard/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ROOT")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login?logout")
                )
                .exceptionHandling(handle -> handle
                        .accessDeniedPage("/dashboard/403")
                )
                .addFilterAfter(new LoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new DashboardFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
