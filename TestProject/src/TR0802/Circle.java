package TR0802;

public class Circle {
	private double radius;
	public Circle() {
		this.radius = 1;
	}
	public double findArea() {
		return Math.PI * Math.pow(radius, 2);
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
