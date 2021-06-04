package pl.od.orderit.messages;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.od.orderit.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="messages")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String sendingUsername;

    @NonNull
    @Column(name = "message_body")
    @Length(max = 188)
    private String messageBody;

    @ManyToMany(mappedBy = "messages")
    private Set<User> users = new HashSet<User>();

    public Message(String userFromDb, String messageBody) {
        this.sendingUsername = userFromDb;
        this.messageBody = messageBody;
    }

    public Message() { }

}
