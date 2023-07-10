package spring.start.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARKING_SPOT")
public class ParkingSpotModel {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 18)
	private String parkingSpotNumber;
	
	//Car
	@OneToOne
	private CarModel car;

	//Registration
	@Column(nullable = false)
	private LocalDateTime registrationDate;

	@Column(nullable = false, length = 78)
	private String reponsibleName;
	
	@Column(nullable = false, length = 30)
	private String apartament;
	
	@Column(nullable = false, length = 30)
	private String block;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
	}

	public CarModel getCar() {
		return car;
	}

	public void setCar(CarModel car) {
		this.car = car;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getReponsibleName() {
		return reponsibleName;
	}

	public void setReponsibleName(String reponsibleName) {
		this.reponsibleName = reponsibleName;
	}

	public String getApartament() {
		return apartament;
	}

	public void setApartament(String apartament) {
		this.apartament = apartament;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
	
	
}
