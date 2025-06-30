package bankapp.inmemory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->

                // Разрешаем доступ без аутентификации только к URL /public
                auth.requestMatchers(("/api/public/info")).permitAll()

                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
        );

        // Собираем и возвращаем цепочку фильтров безопасности
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // Создаем пользователя с логином "user", паролем "password" и ролью "USER"
        UserDetails user = User.withUsername("client1")
                .password(passwordEncoder().encode("12345")) // Пароль кодируется с помощью BCrypt
                .roles("USER")
                .build();

        // Создаем пользователя с логином "admin", паролем "admin" и ролью "ADMIN"
        UserDetails admin = User.withUsername("bankadmin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        // Возвращаем менеджер пользователей, работающий с памятью (без базы данных)
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Используем BCrypt — безопасный алгоритм хеширования паролей
        return new BCryptPasswordEncoder();
    }
}
