package pl.od.orderit.shops.typeOfService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfServiceRepository extends JpaRepository<TypeOfServiceModel, Long> {
    List<TypeOfServiceModel> findAllByShopId(long shopId);
}
