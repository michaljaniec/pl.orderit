package pl.od.orderit.security;

public interface SecurityService {

    String findLoggedInUsername(String username);
   void autoLogin(String username, String password);

}