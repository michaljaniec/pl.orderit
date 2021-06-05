package pl.od.orderit.orders;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.od.orderit.exception.ResourceAlreadyExists;
import pl.od.orderit.shops.Shop;
import pl.od.orderit.shops.ShopService;
import pl.od.orderit.security.SecurityServiceImpl;
import pl.od.orderit.user.User;
import pl.od.orderit.user.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Before
    public User initTestUser(){
        User user = new User();
        user.setUserRealName("TestUserName");
        user.setSurname("TestUserSurname");
        user.setEmail("TestUserEmail@gmail.com");
        user.setPhoneNumber("123456789");
        user.setUsername("TestUsername2");
        user.setPassword("TestUserPassword2");
        userService.save(user);
        return user;
    }

    @BeforeTestMethod
    public void loginUser() throws Exception {
        securityService.autoLogin("TestUsername2", "TestUserPassword2");
    }

    public void createShop() throws ResourceAlreadyExists, Exception {
        Shop newShop = new Shop();
        newShop.setShopName("TestShopName");
        newShop.setShopAmenity("hairdresser");
        newShop.setShopType("hairdresser");
        newShop.setShopRoad("Long St. 4/22");
        newShop.setShopTown("Warsaw");
        newShop.setShopState("mazowieckie");
        newShop.setShopDescription("Test description");
        newShop.setShopPostcode("11-1111");
        shopService.createNewShop(newShop);
    }

    @Before
    public Order initOrder() throws Exception {
        Order order = new Order();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dateOfService = sdf.parse("01-02-2021 10:00");
        LocalDateTime dateOfPlacingTheOrder = LocalDateTime.now(ZoneId.of("UTC+02:00"));
        order.setUserId(1);
        order.setOrderingUsername("123456");
        order.setOrderingName("Michael");
        order.setOrderingPhoneNumber("123456789");
        order.setServiceType("Men's haircut");
        order.setCommentsOnOrder("CommentFromTest");
        order.setDateOfPlacingTheOrder(dateOfPlacingTheOrder);
        order.setDateOfService(dateOfService);
        return order;
    }

    @Test
    void shouldCreateNewOrderServiceTest() throws Exception, ResourceAlreadyExists {
        initTestUser();
        loginUser();
        createShop();
        Order newOrder = initOrder();
        String shopTown = "Warsaw";
        String shopAmenity = "hairdresser";
        String shopName = "TestShopName";
        when(orderService.createNewOrder(newOrder, shopTown, shopAmenity, shopName))
                .thenReturn(newOrder);

    }

    @Test
    void shouldReturnOrdersForLocal() throws Exception {
        loginUser();
        Order order = initOrder();
        orderRepository.save(order);
        List<Order> orders = orderService.showOrders();
        when(orderService.showOrders()).thenReturn(orders);
        assertThat(orders).isNotNull();
    }
}