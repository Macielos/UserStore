package userstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userstore.model.User;
import userstore.model.UserCredentials;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

	UserCredentials findByName(String name);
}