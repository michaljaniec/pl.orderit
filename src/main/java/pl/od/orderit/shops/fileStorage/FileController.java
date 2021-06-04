package pl.od.orderit.shops.fileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.od.orderit.shops.ShopService;
import pl.od.orderit.user.UserServiceImpl;

@RestController
public class FileController {

    @Autowired
    private FileStorageServiceInterface fileStorageServiceInterface;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserServiceImpl userService;


    @RequestMapping(value = "/manage/uploadFile", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        System.out.println(file.getContentType().length());

        if(file.getSize()>2000000){
            message = "Your file is too large it should not be larger than '2MB'.";
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(new ResponseMessage(message));
        } else if (!(file.getContentType().contains("image/jpeg")||file.getContentType().contains("image/png"))){
            message = "You can upload only JPG or PNG files!";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage(message));
        }

        try {
            String fileName;
            fileStorageServiceInterface.save(file);
            fileName = file.getOriginalFilename();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            long idOfOwnedShop = userService.findUserByUsername(auth.getName()).getShop().getId();

            shopService.changeShopAvatarName(idOfOwnedShop, fileName);

            message = "File uploaded successfully!" + fileName;
            return  ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "File upload failed!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
