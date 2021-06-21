package pl.od.orderit.shops.typeOfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.od.orderit.shops.ShopService;

@RestController
public class TypeOfServiceController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/manage/setAvailableShopServices", method = RequestMethod.POST)
    public ModelAndView setAvailableShopServices(@ModelAttribute("typeOfServiceModel") TypeOfServiceModel typeOfServiceModel,
                                                 @RequestParam String serviceName,
                                                 @RequestParam String serviceSex,
                                                 @RequestParam int servicePrice ) {
        ModelAndView modelAndView = new ModelAndView();
        shopService.setNewServiceProvided(typeOfServiceModel, serviceName, serviceSex, servicePrice);
        modelAndView.setViewName("manage");
        return modelAndView;
    }

}
