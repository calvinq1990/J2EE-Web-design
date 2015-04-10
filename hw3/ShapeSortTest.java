/*
 * Name: Jiaqi Luo
 * ID: jiaqiluo
 * course number: 08600
 * date: Sep 24th,2014
 * referrence from the note of Lecture4
 */
public class ShapeSortTest {
	public static void main (String[] args) {
		int i = 0;
		int size = 0;
		Shape[] arrayShape = new Shape[args.length];
		for (i = 0; i< args.length; i++) {
			size = Integer.parseInt(args[i].substring(1));
			if (args[i].substring(0, 1).equals("C"))
				arrayShape[i] = new Circle(size);
			else if (args[i].substring(0, 1).equals("H"))
				arrayShape[i] = new Hexagon(size);
			else 
				arrayShape[i] = new Square(size);
		}
		//sorts the objects in ascending order of area of Shape,and print	
		for (int k = 0; k < arrayShape.length; k++) {
		    for (int j=k+1; j< arrayShape.length; j++) {
		        if (arrayShape[k].getArea() > arrayShape[j].getArea()) {
		           Shape temp = arrayShape[k];
		           arrayShape[k] = arrayShape[j];
		           arrayShape[j] = temp;
		        } 
		    }
		}
		for (int m = 0; m < arrayShape.length; m++)
			System.out.format("%s %.3f %.3f\n", arrayShape[m], arrayShape[m].getArea(),arrayShape[m].getPerimeter());
		
		// re-sorts the objects in descending order of perimeter of Shape,then print out
		for (int k1 = 0; k1 < arrayShape.length; k1++) {
		    for (int j1=k1+1; j1< arrayShape.length; j1++) {
		        if (arrayShape[k1].getArea() < arrayShape[j1].getArea()) {
		           Shape temp = arrayShape[k1];
		           arrayShape[k1] = arrayShape[j1];
		           arrayShape[j1] = temp;
		        } 
		    }
		}
		for (int m1 = 0; m1 < arrayShape.length; m1++)
			System.out.format("%s %.3f %.3f\n", arrayShape[m1], arrayShape[m1].getArea(),arrayShape[m1].getPerimeter());
	}
}
