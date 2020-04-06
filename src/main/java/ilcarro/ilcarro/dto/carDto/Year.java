package ilcarro.ilcarro.dto.carDto;

public class Year {

	private String year;

	private Engine engine;

	
	
	public Year() {
		super();
	}

	public Year(String year, Engine engine) {
		super();
		this.year = year;
		this.engine = engine;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

}
