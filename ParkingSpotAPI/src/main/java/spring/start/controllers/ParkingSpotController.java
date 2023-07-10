package spring.start.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.start.dtos.ParkingSpotDto;
import spring.start.models.CarModel;
import spring.start.models.ParkingSpotModel;
import spring.start.services.CarService;
import spring.start.services.ParkingSpotService;

@RestController
@RequestMapping("/parkingspot")
public class ParkingSpotController {

	final ParkingSpotService parkingSpotService;
	final CarService carService;


	public ParkingSpotController(ParkingSpotService parkingSpotService, CarService carService) {
		super();
		this.parkingSpotService = parkingSpotService;
		this.carService = carService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@Valid ParkingSpotDto parkingSpotDto, @RequestParam("plate") String plate) {
		
		//Setando o carro
		CarModel carModel = carService.findByPlateLike(plate);
		if (carModel == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: O carro não foi encontrado");
		}
		
		//Verfificações
		if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: A vaga de estacionamento já está em uso");

		}
		
		if(parkingSpotService.existsByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: Outra vaga de estacionamento já cadastrada para este apartamento");

		}
		
		if(parkingSpotService.existsByCar(carModel)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: Esse carro já ta em uma vaga");

		}
		
		var parkingSpotModel = new ParkingSpotModel();	
		parkingSpotModel.setCar(carModel);	
	
		//Copia um objeto para outro
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		
		//Colocando a data de registro do parkingSpot
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}
	
	@GetMapping
	public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable Long id) {
		Optional<ParkingSpotModel> optionalParkingSpot = parkingSpotService.findById(id);
		
		if(!optionalParkingSpot.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: Vaga de estacionamento não foi encotrada");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(optionalParkingSpot.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable Long id) {
		Optional<ParkingSpotModel> optionalParkingSpot = parkingSpotService.findById(id);

		if(!optionalParkingSpot.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: Vaga de estacionamento não foi encotrada");
		}
		
		parkingSpotService.delete(optionalParkingSpot.get());
		
		return ResponseEntity.status(HttpStatus.OK).body("OK: Vaga de estacionamento excluida");
	}
	
	@PutMapping("/{id}")
	public  ResponseEntity<Object> updateParkingSpot(@PathVariable Long id, @Valid ParkingSpotDto parkingSpotDto) {
		Optional<ParkingSpotModel> optionalParkingSpot = parkingSpotService.findById(id);
	
		if(!optionalParkingSpot.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: Vaga de estacionamento não foi encotrada");
		}
		
		var parkingSpotModel = new ParkingSpotModel();
		
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setId(optionalParkingSpot.get().getId());
		parkingSpotModel.setRegistrationDate(optionalParkingSpot.get().getRegistrationDate());
		
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
	}
	
	@PutMapping("/car/{id}")
	public  ResponseEntity<Object> updateParkingSpotCar(@PathVariable Long id, @RequestParam("plate") String plate) {
		Optional<ParkingSpotModel> optionalParkingSpot = parkingSpotService.findById(id);
	
		if(!optionalParkingSpot.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: Vaga de estacionamento não foi encotrada");
		}
		
		CarModel carModel = carService.findByPlateLike(plate);
		
		if (carModel == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: O carro não foi encontrado");
		}
		
		if(parkingSpotService.existsByCar(carModel)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO: Esse carro já ta em uma vaga");
		}
		
		var parkingSpotModel = optionalParkingSpot.get();
		
		parkingSpotModel.setCar(carModel);
	
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
	}
	
	
}
