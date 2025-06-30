package bankapp.jwt.serice;


import bankapp.jwt.model.BankUser;
import bankapp.jwt.repository.BankUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Кастомная реализация UserDetailsService, подключённая к базе данных через JPA.
 * Используется Spring Security для загрузки информации о пользователе при аутентификации.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private BankUserRepository bankUserRepository;

    /**
     * Метод загружает пользователя из базы данных по имени пользователя (username).
     * Вызывается Spring Security во время процесса аутентификации.
     *
     * @param username имя пользователя, введённое при логине
     * @return объект UserDetails, содержащий имя, пароль и роли
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в базе по имени
        BankUser bankUser = bankUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Возвращаем объект User из Spring Security с его ролями и паролем
        return new User(
                bankUser.getUsername(),
                bankUser.getPassword(),
                List.of(() -> "ROLE_" + bankUser.getRole()) // Префикс "ROLE_" обязателен для Spring Security
        );
    }
}