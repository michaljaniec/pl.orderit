package pl.od.orderit.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.od.orderit.user.User;
import pl.od.orderit.shops.ShopService;
import pl.od.orderit.user.UserServiceImpl;
import pl.od.orderit.user.roles.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ShopService shopService;

    @Transactional
    public Order createNewOrder(
                                        Order order,
                                        String shopTown,
                                        String shopAmenity,
                                        String shopName
    ) throws JsonProcessingException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        long shopOwnerId = shopService.findShopsByName(shopTown,shopAmenity,shopName).getUser().getId();
        String placeRoad = (shopService.findShopsByName(shopTown,shopAmenity,shopName).getShopRoad());

        order.setUserId(user.getId());
        order.setOrderingUsername(user.getUsername());
        order.setShopOwnerId(shopOwnerId);
        order.setOrderingName(user.getUserRealName());
        order.setOrderingPhoneNumber(user.getPhoneNumber());
        order.setShopName(shopName);
        order.setShopTown(shopTown);
        order.setShopRoad(placeRoad);

        order.setUser(user);
        user.setOrders(Collections.singleton(order));
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Transactional
    public List<Order> showOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        List<Order> orders = orderRepository.findAllByUserId(user.getId());
        return orders;
    }

    @Transactional
    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public List<Order> showOrdersForShop(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long shopOwnerId = userService.findUserByUsername(auth.getName()).getId();
        List<Order> orders = orderRepository.findAllByShopOwnerId(shopOwnerId);
        return orders;
    }

    @Transactional
    public Optional<Order> getUsernameFromOrderById(long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Long getShopOwnerIdByOrderId(long orderId){
        return orderRepository.findShopOwnerIdByOrderId(orderId);
    }
}
