package pl.od.orderit.shops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.od.orderit.exception.ResourceAlreadyExists;
import pl.od.orderit.orders.OrderService;
import pl.od.orderit.user.UserServiceImpl;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OrderService orderService;


    @DeleteMapping(path = "/deleteThisShop/{shopId}")
    public void deleteShop(@PathVariable("shopId") long shopId){
        shopService.deleteShop(shopId);
        System.out.println("usunieto sklep o id: "+shopId);
    }

    @GetMapping(value="/shopRegistration")
    public ModelAndView shopRegistration(){
        ModelAndView modelAndView = new ModelAndView();

        Shop shop = new Shop();
        modelAndView.addObject("shop", shop);
        modelAndView.setViewName("shopRegistration");
        return modelAndView;
    }

    @PostMapping(value = "/shopRegistration")
    public ModelAndView registerNewShop(@ModelAttribute("shop") Shop shop,
                                        BindingResult bindingResult) throws ResourceAlreadyExists {
        ModelAndView modelAndView = new ModelAndView();
        try {
            shopService.createNewShop(shop);
        } catch (ResourceAlreadyExists e) {
            bindingResult
                    .rejectValue("shopName", "error.shopName",
                            "There is already place registered with that name or your account is already registered to another place. ");
            modelAndView.setViewName("/shopRegistration");
            return modelAndView;
        }
        modelAndView.setViewName("/welcome");
        return modelAndView;
    }

    @GetMapping(path={"/manage/{shopId}"})
    public ModelAndView showOwnedShop(@PathVariable("shopId")long shopId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long shopOwnerIdFromSession = userService.findUserByUsername(auth.getName()).getId();

        ModelAndView modelAndView = new ModelAndView();

        if(!(shopService.findShopById(shopId).getId()==shopOwnerIdFromSession)){
            modelAndView.setViewName("home");
            return modelAndView;
        } else

        modelAndView.addObject("shop", shopService.findShopById(shopId));
        modelAndView.setViewName("manage");
        return modelAndView;
    }

    @RequestMapping(value = "/manage", method = RequestMethod.POST)
    public ModelAndView updateShopDescription(@RequestParam String shopDescription){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        long idOfOwnedShop = userService.findUserByUsername(auth.getName()).getShop().getId();
        shopService.changeShopDescription(idOfOwnedShop, shopDescription);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("shop", shopService.findShopById(idOfOwnedShop));
        modelAndView.setViewName("manage");
        return modelAndView;
    }

    @GetMapping("/manage")
    public ModelAndView findMyShop() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("order", orderService.showOrdersForShop());
        modelAndView.addObject("shop", shopService.findShopById(userService.findUserByUsername(auth.getName()).getShop().getId()));
        modelAndView.setViewName("manage");
        return modelAndView;
    }

    @PostMapping(value = {"/home", "/welcome"})
    public ModelAndView searchShopsByCityAndServiceType(@Valid Shop shop,
                                                        @RequestParam String shopTown, String shopAmenity,
                                                        BindingResult bindingResult) throws IOException, InterruptedException {

        ModelAndView modelAndView = new ModelAndView("/order/{shopTown}/{shopAmenity}");
        modelAndView.addObject("shop", shopService.findShops(shopTown, shopAmenity));
        if (modelAndView.getModel().get("shop").toString()=="[]") {
            bindingResult
                    .rejectValue("shopTown", "error.user",
                            "Bad query.");
            modelAndView.addObject("failMessage", "Please provide a valid data");
            modelAndView.setViewName("home");

            return modelAndView;
        } else
            modelAndView.setViewName("order");
        return modelAndView;
    }

    @GetMapping(path= "/offer/{shopTown}-{shopAmenity}-{shopName}")
    public ModelAndView showFilteredShops(@PathVariable("shopTown")String shopTown,
                                          @PathVariable("shopAmenity")String shopAmenity,
                                          @PathVariable("shopName")String shopName ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("shop", shopService.findShopsByName(shopTown, shopAmenity, shopName));
        modelAndView.setViewName("offer");
        return modelAndView;
    }



}



