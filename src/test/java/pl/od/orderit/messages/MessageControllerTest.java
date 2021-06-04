package pl.od.orderit.messages;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.od.orderit.security.SecurityService;
import pl.od.orderit.user.User;
import pl.od.orderit.user.UserServiceImpl;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
class MessageControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SecurityService securityService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Before
    public void initTestUser(){
        User user = new User();
        user.setUserRealName("TestUserName");
        user.setSurname("TestUserSurname");
        user.setEmail("TestUserEmail@gmail.com");
        user.setPhoneNumber("123456789");
        user.setUsername("TestUsername1");
        user.setPassword("TestUserPassword1");
        userService.save(user);
    }

    @Before
    public Message initTestMessage(){
        Message message = new Message();
        message.setMessageBody("Test message body.");
        message.setSendingUsername("TestUsername1");
        System.out.println(message);
        return message;
    }

    @BeforeTestMethod
    public void loginUser() throws Exception {
        securityService.autoLogin("TestUsername1", "TestUserPassword1");
    }

    @Test
    void shouldGetContentOfShowMessageBoxController() throws Exception {
        loginUser();
        mockMvc.perform(get("/messages"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors());
    }

    @Test
    void shouldGetContentOfSendingMessageView() throws Exception {
        loginUser();
        mockMvc.perform(get("/message/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    void shouldCreateNewMessageController() throws Exception {
        initTestUser();
        loginUser();
        Message newMessage = initTestMessage();
        mockMvc.perform(post("/message/1")
                        .param("messageBody", newMessage.getMessageBody())
                        .param("sendingUsername", newMessage.getSendingUsername())
        )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

}