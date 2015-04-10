//Name: Jiaqi Luo(jiaqiluo)
//course: 08600
//date: Sep 8th, 2014

// use char at then minus 48 to get each digit
public class CheckDigit{
	public static void main(String[] args) {
		System.out.println(args[0]);
		int a, b, c, d, e, f, g, h, i, j, k, x;
		a = args[0].charAt(0) - 48;
		b = args[0].charAt(1) - 48;
		c = args[0].charAt(2) - 48;
		d = args[0].charAt(3) - 48;
		e = args[0].charAt(4) - 48;
		f = args[0].charAt(5) - 48;
		g = args[0].charAt(6) - 48;
		h = args[0].charAt(7) - 48;
		i = args[0].charAt(8) - 48;
		j = args[0].charAt(9) - 48;
		k = args[0].charAt(10) - 48;
		
		x = (10- (a*3 + b + c*3 + d + e *3 + f + g *3 + h + i*3 + j + k*3) % 10) % 10;
		
		System.out.println(x);
	}
}

		
		
