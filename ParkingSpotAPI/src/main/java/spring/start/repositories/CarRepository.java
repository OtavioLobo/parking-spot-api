package spring.start.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.start.models.CarModel;

@Repository
public interface CarRepository extends JpaRepository<CarModel, UUID>{

	public Iterable<CarModel> findBylicensePlateCarContaining(String nome);
	
	
	//@Query("SELECT c.licensePlateCar, c.brandCar, c.modelCar, c.colorCar FROM CarModel c WHERE c.licensePlateCar LIKE :nome")
	@Query("SELECT c FROM CarModel c WHERE c.licensePlateCar LIKE :nome")
	public CarModel findBylicensePlateCarLike(@Param("nome") String nome);
	
	boolean existsByLicensePlateCar(String licensePlateCar);


}
