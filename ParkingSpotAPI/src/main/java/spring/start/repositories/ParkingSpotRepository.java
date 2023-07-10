package spring.start.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.start.models.CarModel;
import spring.start.models.ParkingSpotModel;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, Long> {

	public boolean existsByParkingSpotNumber(String ParkingSpotNumber);

	public boolean existsByApartamentAndBlock(String apartament, String block);
	
	public boolean existsByCar(CarModel car);

}
