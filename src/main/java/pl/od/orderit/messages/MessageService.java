package pl.od.orderit.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.od.orderit.user.User;
import pl.od.orderit.user.roles.UserRepository;
import pl.od.orderit.security.SecurityService;
import pl.od.orderit.user.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    private UserServiceImpl userService;

    @Transactional
    public void addMessage(long receivingId,
                           String messageBody) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userFromDb = userService.findUserByUsername(auth.getName());
        Optional<User> user = userService.findUserById(receivingId);
        Message message = new Message(userFromDb.getUsername(), messageBody);
        user.get().getMessages().add(message);
        messageRepository.save(message);
    }

    @Transactional
    public List<Message> showAllMessages() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        List<Message> messages = messageRepository.findMessagesByUsers(user);
        return messages;
    }

    @Transactional
    public void deleteMessage(long id) {



        messageRepository.deleteById(id);
    }

    public Optional<Message> getUsernameFromMessageByMessageId(long id) {
        return messageRepository.findById(id);
    }
}
