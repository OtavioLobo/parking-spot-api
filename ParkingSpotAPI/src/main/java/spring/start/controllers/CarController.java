package spring.start.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.start.dtos.CarDto;
import spring.start.models.CarModel;
import spring.start.services.CarService;

@RestController
@RequestMapping("/car")
public class CarController {

	final CarService carService;

	public CarController(CarService carService) {
		super();
		this.carService = carService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveCar(@Valid CarDto carDto){

		if(carService.existsByLicensePlateCar(carDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: A placa do carro já está em uso");
		}
		
		var carModel = new CarModel();
		
		BeanUtils.copyProperties(carDto, carModel);
	
		return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(carModel));
	}
	
	@GetMapping(path = "/{plate}")
	public CarModel findByPlateLike(@PathVariable String plate) {
		return carService.findByPlateLike(plate);
	}
	
}
