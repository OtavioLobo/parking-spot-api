package spring.start.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import spring.start.models.CarModel;
import spring.start.repositories.CarRepository;
import spring.start.repositories.ParkingSpotRepository;

@Service
public class CarService {

	final CarRepository carRepository;

	public CarService(CarRepository carRepository) {
		super();
		this.carRepository = carRepository;
	}

	@Transactional
	public CarModel save(CarModel carModel) {
		return carRepository.save(carModel);
	}

	public Iterable<CarModel> findByLicensePlate(String plate) {
		return carRepository.findBylicensePlateCarContaining(plate);
	}
	
	public CarModel findByPlateLike(String plate) {
		return carRepository.findBylicensePlateCarLike(plate);
	}

	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return carRepository.existsByLicensePlateCar(licensePlateCar);
	}
	
	
}
