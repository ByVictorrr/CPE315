import java.util.*;

public class exponet{
	public static void main(String [] args)
	{
		System.out.println("2^8="+pow(2,8));
		System.out.println("8^2="+pow(8,2));
		System.out.println("5^6="+pow(5,6));
		System.out.println("1^6="+pow(1,6));
		System.out.println("0^6="+pow(0,6));
	}

	public static int pow(int base, int exponet)
	{
		//special case when exp is zero
		if (exponet==0) return 1;

		int result  = base;

		//multiplying repeadily is same thing as x^y = x*....*x ytimes
		for (int i=1; i< exponet; i++)
			result = mult(result,base); //start at i = 1 because it has done two multiplications
		return result;
	}

	public static int  mult(int num1, int num2)
	{
		int prod = 0;
		for (int i=0; i<num2; i++)
			prod=prod+num1;

		return prod;
	}	
}
