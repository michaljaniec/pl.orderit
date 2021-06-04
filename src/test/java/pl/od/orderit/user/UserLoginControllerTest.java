package pl.od.orderit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDisplayLoginWindow() {
    }

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Transactional
    public User initTestUser(){
        User user = new User();
        user.setUserRealName("TestUserName");
        user.setSurname("TestUserSurname");
        user.setEmail("TestUserEmail@gmail.com");
        user.setPhoneNumber("123456789");
        user.setUsername("TestUsername3");
        user.setPassword("TestUserPassword3");
        userService.save(user);
        return user;
    }

    @Test
    void shouldCreateNewUserController() throws Exception {
        MvcResult registrationResult = mockMvc.perform(post("/registration")
                .content(objectMapper.writeValueAsString(initTestUser()))
        )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        registrationResult.getResponse().getHeader("body");
    }

    @Test
    void shouldGetContentOfRegistrationPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors());

    }

    @Test
    void shouldNotGetContentOfWelcomePageIfNotLoggedInAndRedirectToTheLoginPage() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void shouldGetContentOfTheHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetContentOfLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk());
    }

    @Test
    void shouldLoginUserWithCorrectCredentials() throws Exception {
        mockMvc.perform(formLogin("/login").user("TestUsername3").password("TestUserPassword3")
        )
                .andDo(print())
                .andExpect(redirectedUrl("/welcome"))
                .andReturn();
    }
}