package pl.od.orderit.messages;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.od.orderit.orders.OrderService;
import pl.od.orderit.user.UserServiceImpl;


@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value="/message/{receivingId}", method = RequestMethod.GET)
    public ModelAndView showMessageWritingBox(@PathVariable ("receivingId") long receivingId){

        orderService.getShopOwnerIdByOrderId(receivingId);
        ModelAndView modelAndView = new ModelAndView();

        Message message = new Message();
        modelAndView.addObject("message", message);
        modelAndView.setViewName("message");
        return modelAndView;
    }

    @RequestMapping(value = "/message/{receivingId}", method = RequestMethod.POST)
    public ModelAndView sendNewMessage(@PathVariable ("receivingId") long receivingId,
                                       @ModelAttribute("message") Message message,
                                       BindingResult bindingResult
                                       ){
        ModelAndView modelAndView = new ModelAndView();

        if(message.getMessageBody().isBlank()){
            bindingResult.rejectValue("messageBody", "error.message",
                    "Your message is empty!");
            modelAndView.setViewName("/message");

        } else if(!bindingResult.hasErrors()){
            messageService.addMessage(receivingId, message.getMessageBody());
            modelAndView.setViewName("messages");
        }
        return modelAndView;
    }

    @GetMapping("/messages")
    public ModelAndView showMessageBox(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", messageService.showAllMessages());
        modelAndView.setViewName("messages");
        modelAndView.toString();
        return modelAndView;
    }

    @PostMapping(value={"/messages/deleteMessage/{id}"})
    public ModelAndView deleteMessage(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userNameFromSession = userService.findUserByUsername(auth.getName()).getUsername();
        @NonNull String sendingUsernameFromMessage = messageService.getUsernameFromMessageByMessageId(id).get().getSendingUsername();

        if(!((userNameFromSession.equals(sendingUsernameFromMessage)))){
            modelAndView.setViewName("welcome");
            return modelAndView;
        } else
            messageService.deleteMessage(id);

        modelAndView.setViewName("welcome");
        return modelAndView;
    }

}
