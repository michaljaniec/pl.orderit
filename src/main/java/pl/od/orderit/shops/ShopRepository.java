package pl.od.orderit.shops;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.od.orderit.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findAllByShopTownAndShopAmenity(String shopTown, String shopAmenity);
    Shop findAllByShopTownAndShopAmenityAndShopName(String shopTown, String shopAmenity, String shopName);
    Shop findById(long shopId);
    Optional<Shop> findByUser(long user);
    Shop deleteById(long shopId);
    Optional<Shop> findByShopName(String shopName);
    Optional<Shop> findShopByUser(User user);
}
