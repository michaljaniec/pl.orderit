package pl.od.orderit.user;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;
import pl.od.orderit.shops.Shop;
import pl.od.orderit.messages.Message;
import pl.od.orderit.orders.Order;
import pl.od.orderit.user.roles.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private long id;

    @Column(name = "user_name")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String username;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "phone_number")
    @Length(min= 9, message = "*Your phone number should be between 9-11" +
            " characters long and contains only numbers!")
    @NotEmpty(message = "*Please provide your phone number")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private String phoneNumber;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    @Pattern(regexp = "[a-zA-Z]+", message = "name must contain only letters")
    private String userRealName;

    @Column(name = "surname")
    @NotEmpty(message = "*Please provide your last name")
    @Pattern(regexp = "[a-zA-Z]+", message = "surname must contain only letters")
    private String surname;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy ="user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Order> orders;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_messages",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "messages_id"))
    private Set<Message> messages = new HashSet<Message>();

    @OneToOne
    @JoinTable(
            name = "user_shop",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")})
    private Shop shop;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
