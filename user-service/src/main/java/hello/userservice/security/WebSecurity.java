package hello.userservice.security;

import hello.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurity {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**")
                .permitAll()
//                .hasIpAddress("192.168.0.1")  //해당 아이피만 허용
                .and()
                .addFilter(getAuthenticationFilter())
        ;
        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, env);
        // authenticationManager -> 기본적으로 ProviderManager
        authenticationFilter.setAuthenticationManager(new ProviderManager(provider));

        return authenticationFilter;
    }
}
