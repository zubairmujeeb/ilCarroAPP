package ilcarro.ilcarro.dto.carDto;

public class Engine {

	private String engine;

	private Fuel fuel;
	
	

	public Engine() {
		super();
	}

	public Engine(String engine, Fuel fuel) {
		super();
		this.engine = engine;
		this.fuel = fuel;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public Fuel getFuel() {
		return fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}

}
