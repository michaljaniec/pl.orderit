package pl.od.orderit.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderingUsername(String orderingUsername);
    List<Order> findAllByShopOwnerId(long shopOwnerId);
    Long findShopOwnerIdByOrderId(long orderId);
    List<Order> findAllByUserId(long userId);
}
