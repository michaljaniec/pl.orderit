package pl.od.orderit.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.od.orderit.shops.Shop;
import pl.od.orderit.user.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserServiceImpl userService;
    @SneakyThrows
    @RequestMapping(path= "/offer/{shopTown}-{shopAmenity}-{shopName}")
    public ModelAndView createNewOrderController(
                                         @ModelAttribute("order") Order order,
                                         @RequestParam String serviceType,
                                         @RequestParam String dateInString,
                                         @RequestParam String commentsOnOrder,
                                         @PathVariable("shopTown")String shopTown,
                                         @PathVariable("shopAmenity")String shopAmenity,
                                         @PathVariable("shopName")String shopName
                                         ) throws JsonProcessingException {

        ModelAndView modelAndView = new ModelAndView();

        LocalDateTime dateOfPlacingTheOrder = LocalDateTime.now(ZoneId.of("UTC+02:00"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = sdf.parse(dateInString);

        order.setDateOfPlacingTheOrder(dateOfPlacingTheOrder);
        order.setDateOfService(date);
        order.setServiceType(serviceType);
        order.setCommentsOnOrder(commentsOnOrder);

        orderService.createNewOrder(order, shopTown, shopAmenity, shopName);
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

    @GetMapping(path="/myOrders")
    public ModelAndView showMyOrders(){
        ModelAndView modelAndView = new ModelAndView("myOrders");
        modelAndView.addObject("order", orderService.showOrders());
        modelAndView.setViewName("myOrders");
        return modelAndView;
    }

    @PostMapping(value={"/myOrders/deleteOrder/{orderId}","/manage/deleteOrder/{orderId}" })
    public ModelAndView deleteMyOrder(@PathVariable("orderId") long orderId){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userNameFromSession = userService.findUserByUsername(auth.getName()).getUsername();
        @NonNull String usernameFromOrder = orderService.getUsernameFromOrderById(orderId).get().getOrderingUsername();

        long ownerIdFromSession = userService.findUserByUsername(auth.getName()).getId();
        @NonNull long ownerIdOfShop = orderService.getUsernameFromOrderById(orderId).get().getShopOwnerId();

        if(!((userNameFromSession.equals(usernameFromOrder))||(ownerIdFromSession==(ownerIdOfShop)))){
            modelAndView.setViewName("welcome");
            return modelAndView;
        } else
        orderService.deleteOrder(orderId);
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

}
