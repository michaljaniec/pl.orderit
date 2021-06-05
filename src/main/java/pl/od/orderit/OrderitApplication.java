package pl.od.orderit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = {"pl.od"})
public class OrderitApplication {

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC+02:00"));
		System.out.println("Spring boot application running in UTC+2 timezone :" + new Date());
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderitApplication.class, args);
	}

}
