package userstore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import userstore.dto.AuthenticationData;
import userstore.model.UserCredentials;
import userstore.repository.UserCredentialsRepository;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    private PasswordEncoder passwordEncoder;

    public AuthenticationService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean authenticate(AuthenticationData authenticationData)
    {
        log.debug("Authenticate: {}", authenticationData);
        UserCredentials userCredentialsFromDB = userCredentialsRepository.findByName(authenticationData.getName());
        if(userCredentialsFromDB == null) {
            return false;
        }
        return passwordEncoder.matches(authenticationData.getPassword(), userCredentialsFromDB.getPasswordHash());
    }

    public void register(AuthenticationData authenticationData) {
        log.debug("Save: {}", authenticationData);

        UserCredentials userCredentialsFromDB = userCredentialsRepository.findByName(authenticationData.getName());
        if(userCredentialsFromDB != null) {
            throw new IllegalArgumentException("User "+authenticationData.getName() + " already exists");
        }
        userCredentialsRepository.save(new UserCredentials(null, authenticationData.getName(), passwordEncoder.encode(authenticationData.getPassword())));
    }

}
