/*
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course number: 08600
 * date: Sep 24th,2014
 * referrence from the note of Lecture4
 */
public class Shape {
	private double area;
	private double perimeter;
	public Shape(double newArea, double newPerimeter) {
		area = newArea;
		perimeter = newPerimeter;
		
	}
	
	public int compareTo(Object obj) {
		Shape s = (Shape)obj;
		if(s.getArea() > this.getArea())
			return 1;
		else
			return -1;
	}
	
	public double getArea() { return area; }
	public double getPerimeter() {return perimeter;}
	public String toString() {
		return "Shape(area=" + area + ")";
	}

	
}
