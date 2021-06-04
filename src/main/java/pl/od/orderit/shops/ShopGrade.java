package pl.od.orderit.shops;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="grades")
@Getter
@Setter
public class ShopGrade {

    @Id
    @Column(name = "id")
    private Long id;

    @MapsId
    @OneToOne
    private Shop shop;

    @Column(name="number_of_1")
    int numberOf1;

    @Column(name="number_of_2")
    int numberOf2;

    @Column(name="number_of_3")
    int numberOf3;

    @Column(name="number_of_4")
    int numberOf4;

    @Column(name="number_of_5")
    int numberOf5;



    public ShopGrade(Shop shop, int numberOf1, int numberOf2, int numberOf3,
                                int numberOf4, int numberOf5){
        this.shop = shop;
        this.numberOf1 = numberOf1;
        this.numberOf2 = numberOf2;
        this.numberOf2 = numberOf3;
        this.numberOf2 = numberOf4;
        this.numberOf2 = numberOf5;
    }

    public ShopGrade() {

    }
}
