package pl.od.orderit.shops.typeOfService;

import lombok.Getter;
import lombok.Setter;
import pl.od.orderit.shops.Shop;

import javax.persistence.*;

@Entity
@Table(name="type_of_service")
@Getter
@Setter
public class TypeOfServiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "service_name")
    String serviceName;

    @Column(name = "sex")
    String sex;

    @Column(name = "price")
    int price;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public TypeOfServiceModel(String serviceName, String sex, int price){
        this.serviceName = serviceName;
        this.sex = sex;
        this.price = price;
    }

    public TypeOfServiceModel() {}
}
