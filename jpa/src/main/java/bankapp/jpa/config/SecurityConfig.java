package bankapp.jpa.config;

import bankapp.jpa.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->

                        // Разрешаем доступ без аутентификации к /public и к H2-консоли
                        auth.requestMatchers( "/h2-console/**").permitAll()

                                // Все  запросы требуют аутентификации
                                .anyRequest().authenticated()
                )
                // Включаем стандартную форму логина (Spring Security создаёт её автоматически)
                .formLogin();

        // Необходимые настройки для корректной работы H2 консоли:
        http.headers().frameOptions().disable(); // Разрешаем встраивание H2 Console в iframe
        http.csrf().disable(); // Отключаем CSRF для простоты тестирования H2

        return http.build();
    }

    /**
     * Конфигурация поставщика аутентификации, использующего кастомный сервис пользователей и кодировщик пароля.
     *
     * @param customUserDetailService реализация UserDetailsService, работающая с JPA
     * @return бин DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailService customUserDetailService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        // Указываем кастомную реализацию UserDetailsService (работа с БД через JPA)
        authenticationProvider.setUserDetailsService(customUserDetailService);

        // Устанавливаем кодировщик паролей (BCrypt)
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


    /**
     * Кодировщик паролей. Используется как при регистрации, так и при проверке логина.
     *
     * @return BCryptPasswordEncoder — безопасный и широко используемый алгоритм хеширования
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
