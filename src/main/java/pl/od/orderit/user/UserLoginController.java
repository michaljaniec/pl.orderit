package pl.od.orderit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.od.orderit.security.SecurityService;
import pl.od.orderit.user.roles.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserLoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginAuth(@Valid User user, BindingResult bindingResult) {
        Optional<User> userFromDb = Optional.ofNullable(userRepository.findUserByUsername(user.getUsername()));
        userFromDb.get().getRoles();
        if (userFromDb == null) {
            bindingResult
                  .rejectValue("username", "error.user",
                    "Username that you have provided is not exsisting.");
        } else if (userFromDb.get().getPassword() != user.getPassword()) {
            bindingResult
                    .rejectValue("password", "error.user",
                            "Bad password provided.");
        }
        securityService.autoLogin(user.getUsername(), user.getPassword());
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> userFromDb = Optional.ofNullable(userService.findUserByUsername(user.getUsername()));
        if (userFromDb.isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already user registered with the USERNAME provided.");
        } else if (user.getPassword() == null) {
            bindingResult
                    .rejectValue("password", "error.user",
                            "Please provide your s3cr3t password.");
        } else if ((user.getEmail().isEmpty())||(userService.findByEmail(user.getEmail()).isPresent())) {
            bindingResult.rejectValue("email", "error.user",
                                     "Please provide valid email.");
        } else if ((user.getPhoneNumber().isEmpty())||(userService.findUserByPhoneNumber(user.getPhoneNumber()).isPresent())){
            bindingResult.rejectValue("phoneNumber", "error.user",
                                    "There is already registered account with phone number provided.");
        } else if (!bindingResult.hasErrors()) {
            userService.save(user);
            modelAndView.addObject("successMessage", "User has been registered successfully.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
        }
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public ModelAndView welcome(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = Optional.ofNullable(userRepository.findUserByUsername(auth.getName()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", user.get().getUsername());
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

    @GetMapping(value="/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

}