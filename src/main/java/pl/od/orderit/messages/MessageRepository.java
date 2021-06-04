package pl.od.orderit.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.od.orderit.orders.Order;
import pl.od.orderit.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

//    List<Message> findMessagesByReceivingUsername(String receivingUsername);
//    List<Message> findMessageByReceivingId(long receivingId);

//      List<Message> findMessageById(long id);

      List<Message> findMessagesByUsers(User user);


}
