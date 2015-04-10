/*
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course number: 08600
 * date: Sep 24th,2014
 * referrence from the note of Lecture4
 */
public class Rectangle extends Shape {
	private double width;
	private double height;

	public Rectangle(double newWidth, double newHeight) {
		super(newWidth * newHeight, 2 * (newWidth + newHeight));
		width  = newWidth;
		height = newHeight;
	}
	

	public double getHeight() { return height; }
	public double getWidth()  { return width;  }

	public String toString() {
		return "Rectangle(width=" + width+", height=" + height + ")";
	}
}
