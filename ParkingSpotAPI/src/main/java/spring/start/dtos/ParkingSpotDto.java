package spring.start.dtos;


import javax.validation.constraints.NotBlank;

import spring.start.models.CarModel;

public class ParkingSpotDto {
	
	@NotBlank
	private String parkingSpotNumber;
	
	@NotBlank
	private String reponsibleName;
	
	@NotBlank
	private String apartament;
	
	@NotBlank
	private String block;
	
	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
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
