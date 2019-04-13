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
	
	
