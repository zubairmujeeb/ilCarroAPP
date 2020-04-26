package ilcarro.ilcarro.dto.carDto;

public class Fuel {

	private String fuel;

	private Gear gears;
	
	

	public Fuel() {
		super();
	}

	public Fuel(String fuel, Gear gears) {
		super();
		this.fuel = fuel;
		this.gears = gears;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public Gear getGears() {
		return gears;
	}

	public void setGears(Gear gears) {
		this.gears = gears;
	}

}
