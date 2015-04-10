public class Square extends Rectangle {
	private double side;

	public Square(double newSide) {
		super(newSide, newSide);
		side = newSide;
	}

	public double getSide() { return side; }

	public String toString() {
		return "Square";
	}
}
