package userstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userstore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);
}