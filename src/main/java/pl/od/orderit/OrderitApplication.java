package pl.od.orderit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//@EnableJpaRepositories(repositoryBaseClass = AvaliablePlaceRepository.class)
//@EnableJpaRepositories(repositoryFactoryBeanClass = AvaliablePlaceRepository.class)
@SpringBootApplication(scanBasePackages = {"pl.od"})
public class OrderitApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderitApplication.class, args);
	}

}
