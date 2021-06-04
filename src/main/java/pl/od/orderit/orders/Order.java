package pl.od.orderit.orders;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.od.orderit.shops.Shop;
import pl.od.orderit.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long orderId;

    @NonNull
    @Column(name = "shop_owner_id")
    private long shopOwnerId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    @NonNull
    private String orderingUsername;

    @NonNull
    private String orderingName;

    @NonNull
    private String orderingPhoneNumber;

    @Column(name="comments_on_order")
    @Length(max = 188)
    private String commentsOnOrder;

    @NonNull
    @Column(name = "service_type")
    @Length(max = 50)
    private String serviceType;

    @NonNull
    @Column(name = "shop_name")
    private String shopName;

    @NonNull
    @Column(name = "shop_town")
    private String shopTown;

    @NonNull
    @Column(name = "shop_road")
    private String shopRoad;

    @NonNull
    @Column(name = "date_of_placing_the_order")
    private Date dateOfPlacingTheOrder;

    @NonNull
    @Column(name = "date_of_service")
    private Date dateOfService;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Order(long userId, String username, long shopOwnerId, String orderingName, String orderingPhoneNumber, String serviceType, String shopName,
                 String shopTown, String shopRoad, Date dateOfPlacingTheOrder, Date dateOfService, String commentsOnOrder) {
        this.userId = userId;
        this.orderingUsername=username;
        this.shopOwnerId = shopOwnerId;
        this.orderingName = orderingName;
        this.orderingPhoneNumber = orderingPhoneNumber;
        this.serviceType = serviceType;
        this.shopName = shopName;
        this.shopTown = shopTown;
        this.shopRoad = shopRoad;
        this.dateOfPlacingTheOrder = dateOfPlacingTheOrder;
        this.dateOfService = dateOfService;
        this.commentsOnOrder = commentsOnOrder;

    }

}
