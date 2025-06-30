package bankapp.jwt.utils;

import bankapp.jwt.model.BankUser;
import bankapp.jwt.repository.BankUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseUtil {

    private final BankUserRepository bankUserRepository;

    public DatabaseUtil(BankUserRepository bankUserRepository) {
        this.bankUserRepository = bankUserRepository;
    }

    @PostConstruct
    public void init() {
        BankUser client = new BankUser();
        client.setUsername("client");
        client.setPassword("$2a$10$TOR7PyTEagm4Dj9fJpjLj.UqeePdmE6CNcnN.socT/s25BkTvPePa");
        client.setRole("USER");
        bankUserRepository.save(client);
        log.info("User {} created", client.getUsername());

        BankUser supervisor = new BankUser();
        supervisor.setUsername("supervisor");
        supervisor.setPassword("$2a$10$jj15caV5c9IKTTrpkiQn6OiG8AGWFQt5wOohsWmv/wjH8FB07gP2i");
        supervisor.setRole("ADMIN");
        bankUserRepository.save(supervisor);
        log.info("User {} created", supervisor.getUsername());
    }
}