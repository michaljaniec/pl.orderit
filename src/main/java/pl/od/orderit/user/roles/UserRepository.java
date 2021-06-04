package pl.od.orderit.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.od.orderit.messages.Message;
import pl.od.orderit.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findUserByUsername(String username);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    Optional<User> findUserById(long userId);
    User deleteById(long userId);

}
