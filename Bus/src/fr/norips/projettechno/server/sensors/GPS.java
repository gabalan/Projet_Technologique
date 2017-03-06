package fr.norips.projettechno.server.sensors;

public class GPS extends Sensor {
	private String className;
	public GPS(String name) {
		super(name);
		className = "GPS";
	}
	
	@Override
	public String getClassName() {
		return className;
	}
}
