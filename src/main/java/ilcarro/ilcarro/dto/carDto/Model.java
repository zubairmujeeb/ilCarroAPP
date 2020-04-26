package ilcarro.ilcarro.dto.carDto;

public class Model {

	private String model;
	private Year years;

	public Model() {
		super();
	}

	public Model(String model, Year years) {
		super();
		this.model = model;
		this.years = years;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Year getYears() {
		return years;
	}

	public void setYears(Year years) {
		this.years = years;
	}

}
