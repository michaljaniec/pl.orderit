package pl.od.orderit.shops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.od.orderit.exception.ResourceAlreadyExists;
import pl.od.orderit.user.User;
import pl.od.orderit.user.UserServiceImpl;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserServiceImpl userService;

    public void deleteShop(long shopId){
        shopRepository.deleteById(shopId);
    }

    @Transactional
    public void createNewShop(Shop shop) throws ResourceAlreadyExists {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        if(shopRepository.findByShopName(shop.getShopName()).isPresent()){
            throw new ResourceAlreadyExists("The name that u have provided already exists!");
        } else if (shopRepository.findShopByUser(user).isPresent()){
            throw new ResourceAlreadyExists("This user already has a shop assigned.");
        }
        ShopGrade shopGrade = new ShopGrade(shop, 0, 0, 0 ,0, 0);
        shop.setShopGrade(shopGrade);
        shop.setAvatarName("hairdresserDefaultAvatar.png");
        user.setShop(shop);
        shopRepository.save(shop);
    }

    @Transactional
    public List<Shop> findShops(String shopTown, String shopAmenity) {
        List<Shop> shops = (shopRepository.findAllByShopTownAndShopAmenity(shopTown, shopAmenity));
        return shops;
    }

    @Transactional
    public Shop findShopsByName(@RequestParam String shopTown, String shopAmenity, String shopName) {
        Shop shop = shopRepository.findAllByShopTownAndShopAmenityAndShopName(shopTown, shopAmenity, shopName);
        return shop;
    }

    @Transactional
    public Shop changeShopDescription(@RequestParam long shopId, String shopDescription) {
        Shop shop = shopRepository.findById(shopId);
        shop.setShopDescription(shopDescription);
        Shop updatedShopDescription = shopRepository.save(shop);
        return updatedShopDescription;
    }

    public Shop changeShopAvatarName(@RequestParam long shopId, String shopAvatarName){
        Shop shop = shopRepository.findById(shopId);
        shop.setAvatarName(shopAvatarName);
        Shop updatedShopAvatarName = shopRepository.save(shop);
        return updatedShopAvatarName;
    }

    @Transactional
    public Shop findShopById(long shopId){
        Shop shop = shopRepository.findById(shopId);
        return shop;
    }

}

