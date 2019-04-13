########################################################
# CPE 315: Lab - part 
# Author: Victor Delaplaine 
# Date: 2019-04-13
# Description : 
#		
#
# Assumptions : (1) both x and y are positive 
#		(2) 
#
# Java Code : 
#
#		public static void main(String args[])
#		{	
#			
#		}
#		public int exponet(int x, int y)
#		{
			 
#			return num - (nium/div)*div;
#		}
#		public int mutliply(int x, int y)
#		{		
			int prod = 0;
			int exp = 1;	
			int temp = x;
			while(y != 0){	
				temp = x;		
				while(x != 0) //essentially x*x = prod
				{
					prod = x + prod;
					x--;
				}
				y--;
#			}

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
	
	
