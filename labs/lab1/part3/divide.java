import java.util.*; 

public class divide{ 

	public static void main(String []args){
		int upper = 2, lower = 10;
		int div = 65536;

		System.out.println("log2(256)=" + log2(256));
		//System.out.println("lower = " + divide(upper,lower,div).lower);
	}
	//return x/y
	public static twoInts divide (int upper, int lower, int divisor ) {
		// x*z = x+x+...+x_ztimes
		//if (divisor == 0) return NULL;

		// int is 32 bits so
		int result_upper = upper, result_lower = lower;

		int masked_LSB = 0;
		for (int i = 0; i< log2(divisor); i++)
		{

			masked_LSB  = result_upper & 1; //mask one to get LSB before shifting 

			result_upper = result_upper >> 1 ; //then shift right to the by one

			result_lower = result_lower >> 1; //the lower shift right 
			
			masked_LSB = masked_LSB << 31; //shift masked to MSB to or with lower

			result_lower = result_lower | masked_LSB;
		}
		
		twoInts l = new twoInts();
		l.upper = result_upper;
		l.lower = result_lower;

		return l;
	}
	public static int log2(int num)
	{
		int result = 0;
		while(num != 1)
		{
			num = num >> 1;
			result++;
		}
		return result;
	}
	

}
