package ilcarro.ilcarro.dto.carDto;

public class Gear {

	private String gear;

	private WheelsDrive wheelDrives;
	
	

	public Gear() {
		super();
	}

	public Gear(String gear, WheelsDrive wheelDrives) {
		super();
		this.gear = gear;
		this.wheelDrives = wheelDrives;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public WheelsDrive getWheelDrives() {
		return wheelDrives;
	}

	public void setWheelDrives(WheelsDrive wheelDrives) {
		this.wheelDrives = wheelDrives;
	}

}
