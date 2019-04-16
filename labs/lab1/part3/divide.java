import java.util.*; 

public class divide{ 

	public static void main(String []args){
		System.out.println("higher = " + divide(2,10,32).upper);
		System.out.println("lower = " + divide(80,1,32).lower);
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
			
			masked_LSB = result_upper << 31; //shift masked to MSB to or with lower

			result_lower = result_lower | masked_LSB;
		}
		
		twoInts l = new twoInts();
		l.upper = result_upper;
		l.lower = result_lower;

		return l;
	}
	public static int log2(int num)
	{
		return (Math.log(num)/Math.log(2));
	}

}
