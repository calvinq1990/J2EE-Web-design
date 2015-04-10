/*
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course number: 08600
 * date: Sep 24th,2014
 * referrence from the note of Lecture4
 */
public class Hexagon extends Shape{
	private double side;
	public Hexagon (double newSide) {
		super (3 * Math.sqrt(3) / 2 * Math.pow(newSide, 2), 6 * newSide);
		side = newSide;
	}

	
	public double getSide() { return side; }

	public String toString() {
		return "Hexagon";
	}

}
