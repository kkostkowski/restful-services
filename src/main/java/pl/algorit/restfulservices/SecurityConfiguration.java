package pl.algorit.restfulservices;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Primary
    @SuppressWarnings("deprecated")
    public UserDetailsService userDetailsServiceFake() {
        val niki = User.withDefaultPasswordEncoder()
                .username("niki")
                .password("niki")
                .roles("WATCHER")
                .build();

        val heniek = User.withDefaultPasswordEncoder()
                .username("heniek")
                .password("heniek123")
                .roles("USER")
                .build();

        val bolek = User.withDefaultPasswordEncoder()
                .username("bolek")
                .password("bolek#1")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(niki, heniek, bolek);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/rest/api/v1/comments/").hasRole("USER")
                .mvcMatchers(HttpMethod.POST, "/rest/api/v1/movies/").hasRole("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and().formLogin()
                .and().httpBasic();


    }
}
