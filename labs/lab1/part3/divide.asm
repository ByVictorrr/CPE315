########################################################
# CPE 315: Lab - part 
# Author: Victor Delaplaine 
# Date: 2019-04-13
# Description : 
#		
#
# Assumptions : (1) 
#		(2) 
#
# Java Code : 
#
# 	public class divide {
#		public static void main(String args[])
#		{	
#			
#		}
#		public int divide(int num, int div)
#		{
#			return num - (num/div)*div;
#		}
#	}
#######################################################
	
.data

promptNum:
	.asciiz "Enter num \n\n"
promptDiv:
	.asciiz "Enter div \n\n"
output:
	.asciiz "\n Remainder = "


.text
############################################################
# main subroutine: prints out remainder of num/div
# Parameters : n/a
# Return : n/a
# Tweaked Parmeter : $a1 = num
#		     $a2 = div
############################################################
main:
	
	
############################################################
# mod subroutine: returns the remainder num/div
# Parameters : $a1 and $a2
# Return : $v1 - num % div
# Tweaked Parmeter : $t1 - result
#		   : $t2 - n = 2^i
#		   : $t3 - used for constant 1 
############################################################
mod: 
	
	
import java.util.*; 

public class divide{ 

	public static void main(String []args){
		int upper = 2, lower = 10;
		int div = 65536;
		System.out.println("higher = " + divide(upper,lower,div).upper);
		System.out.println("lower = " + divide(upper,lower,div).lower);
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
	public static double log2(double num)
	{
		return (Math.log(num)/Math.log(2));
	}

}
