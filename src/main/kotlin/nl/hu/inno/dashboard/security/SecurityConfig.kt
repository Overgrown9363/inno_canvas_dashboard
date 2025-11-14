package nl.hu.inno.dashboard.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().permitAll()
//                    .anyRequest().authenticated()
            }
            .oauth2Login { }
            .oauth2ResourceServer { resourceServer ->
                resourceServer.jwt { }
            }
            .csrf { csrf -> csrf.disable() }
            .headers { headers -> headers.frameOptions { it.disable() }}
        return http.build()
    }
}