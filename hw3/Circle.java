/*
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course number: 08600
 * date: Sep 24th,2014
 * referrence from the note of Lecture4
 */
public class Circle extends Shape {
	private double radius;
	

	public Circle(double newRadius) {
		super(Math.PI*newRadius*newRadius, Math.PI * 2 * newRadius);
		radius = newRadius;
	}
	

	public double getRadius() { return radius; }

	public String toString() {
		return "Circle";
	}

}
