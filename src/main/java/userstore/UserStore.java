package userstore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "userstore")
public class UserStore {

    public static void main(String[] args) {
        log.info("Launching UserStore...");
        log.debug("Launching UserStore on debug...");
        SpringApplication.run(UserStore.class, args);
    }
}
