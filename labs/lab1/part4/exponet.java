import java.util.*;

public class exponet{

	public static void main(String []args){
		System.out.println("8^0=" + pow(8,0));
		System.out.println("8^1=" + pow(8,1));
		System.out.println("3^8=" + pow(3,8));
		System.out.println("10^10=" + pow(10,10));
	}
	public static int pow (final int base, final int exponent) {
	  int result = base;
	  for (int i = 1; i < exponent; i++)
	     for (int j = 0; j < base; j++)
	         result += base;
	  return result;
	}

}
