package fr.norips.projettechno.server.sensors;

public class Gyro extends Sensor {
	private String className;
	public Gyro(String name) {
		super(name);
		className = "Gyroscope";
	}
	@Override
	public String getClassName() {
		return className;
	}
}
