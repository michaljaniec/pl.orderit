package pl.od.orderit.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.od.orderit.orders.Order;
import pl.od.orderit.user.User;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

      List<Message> findMessagesByUsers(User user);


}
