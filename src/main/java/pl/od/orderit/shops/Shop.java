package pl.od.orderit.shops;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import pl.od.orderit.shops.typeOfService.TypeOfServiceModel;
import pl.od.orderit.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="shops")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicUpdate
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "shop")
    private ShopGrade shopGrade;

    @OneToOne(mappedBy = "shop")
    private User user;

    @NonNull
    @Column(name="shop_name")
    private String shopName;

    @NonNull
    @Column(name="shop_amenity")
    private String shopAmenity;

    @NonNull
    @Column(name="shop_type")
    private String shopType;

    @NonNull
    @Column(name="shop_road")
    private String shopRoad;

    @NonNull
    @Column(name="shop_town")
    private String shopTown;

    @NonNull
    @Column(name="shop_state")
    private String shopState;

    @NonNull
    @Column(name="shop_postcode")
    private String shopPostcode;

    @NonNull
    @Column(name="shop_description")
    private String shopDescription;

    @Column(name="avatar_name")
    private String avatarName;

    @OneToMany(mappedBy = "shop")
    private Set<TypeOfServiceModel> typeOfServiceModel;

}
