########################################################
# CPE 315: Lab - part 4
# Author: Victor Delaplaine 
# Date: 2019-04-13
# Description : Input two numbers x and y,
#		This program takes in x and y and returns
#		x^y. Using no Mult operations
#		
#
# Assumptions : (1) both x and y are positive
#
# Java Code : 
#
#import java.util.*;
#
#public class exponet{
#	public static void main(String [] args)
#	{
#		System.out.println("2^8="+pow(2,8));
#		System.out.println("8^2="+pow(8,2));
#		System.out.println("5^6="+pow(5,6));
#		System.out.println("1^6="+pow(1,6));
#		System.out.println("0^6="+pow(0,6));
#	}
#
#	public static int pow(int base, int exponet)
#	{
#		//special case when exp is zero
#		if (exponet==0) return 1;
#
#		int result  = base;
#
#		//multiplyiplying repeadily is same thing as x^y = x*....*x ytimes
#		for (int i=1; i< exponet; i++)
#			result = multiply(result,base); //start at i = 1 because it has done two multiplyiplications
#		return result;
#	}
#
#	public static int  multiply(int num1, int num2)
#	{
#		int prod = 0;
#		for (int i=0; i<num2; i++)
#			prod=prod+num1;
#
#		return prod;
#	}	
#}
########################################################

.data

promptBase:
	.asciiz "Enter the base \n\n"
promptExp:
	.asciiz "Enter exponet \n\n"
output:
	.asciiz "\n result = "


.text
############################################################
# main subroutine: gets user input of base, exponet, prints
#	           out base^exponet
# Parameters : n/a
# Return : n/a
# Locals : $t0 - base
#        : $t1 - exponet
############################################################
main:
#==================I/O=================
	# Output to the console input base
	la $a0, promptBase
	addi $v0, $zero, 4 	
	syscall 	
	
	# input to the console
	addi $v0, $zero, 5	
	syscall 	
			
	# $t0 - base
	add $t0, $v0, $zero 

	# Ask user to enter exponet
	la $a0, promptExp
	addi $v0, $zero, 4 	
	syscall 	

	# input to the console
	addi $v0, $zero, 5	
	syscall 	

	# $t1 = exponet
	add $t1, $v0, $zero 

	#pass arguments
	add $a0, $t0, $zero
	add $a1, $t1, $zero
#=============================================	
	# call function pow(base,exponet)
	jal multiply
	
	# Output the result
	add $a0, $v0, $zero

	addi $v0, $zero, 1
	syscall
	
	#end program 
	li $v0, 10
	syscall
	
############################################################
# multiply subroutine: returns the multiplyiplication of a and b
# Parameters : $a0 - num1 
#	     : $a1 - num2
# Return : $v0 - num1 * num2
# Tweaked Parmeter : $t0 - product
#		   : $t1 - i
#		   : $t2 - for condition check
############################################################
## Java Code:
#	public static int  multiply(int num1, int num2)
#	{
#		int prod = 0;
#		for (int i=0; i<num2; i++)
#			prod=prod+num1;
#
#		return prod;
#	}	
#}
########################################################

multiply:
	add $t0, $zero $zero # prod = 0	
	add $t1, $zero $zero # i = 0
	
loop_multiply: 
	slt $t2, $t1, $a1 #$t2 = 1, if (num2 > i)
			  #$t2 = 0, if (i >= num2)
	beq $t2, $zero, end_multiply #goto end_multiply	
	add $t0, $t0, $a0 #prod = prod + num1
	addi $t1, $t1, 1 # i++	
	j loop_multiply

end_multiply: 
	add $v0, $t0, $zero
	jr $ra #return 
		
	
	
	 
