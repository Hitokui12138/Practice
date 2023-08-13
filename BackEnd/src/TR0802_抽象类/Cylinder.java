package TR0802_抽象类;

public class Cylinder extends Circle {
	private double length;

	public Cylinder() {
		super();
		// TODO Auto-generated constructor stub
		this.length = 1;
	}
	public double findVolume() {
		return super.findArea() * this.length;
	}
	
	public double findArea() {
		return 2 * super.getRadius() * Math.PI * this.length;
	}
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
}
