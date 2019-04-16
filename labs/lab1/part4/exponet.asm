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
	jal pow
	
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
	#check if num2 == 0 first
	beq $a2, $zero, end_multiply
	
loop_multiply: 
	#check general case
	slt $t2, $t1, $a1 #$t2 = 1, if (num2 > i)
			  #$t2 = 0, if (i >= num2)
	beq $t2, $zero, end_multiply #goto end_multiply	
	add $t0, $t0, $a0 #prod = prod + num1
	addi $t1, $t1, 1 # i++	
	j loop_multiply

end_multiply: 
	add $v0, $t0, $zero
	jr $ra #return 
		
	
	
############################################################
# pow subroutine: returns the exponet of base^exponet
# Parameters : $a0 - base
#	     : $a1 - exponet
# Return  : $v0 - base^exponet
# locals	   : $t0 - result
#		   : $t2 - for condition check
#		   : $t3 - hold base
#		   : $t4 - holds exponet
# saved : $s0 : i
############################################################
## Java Code:
#public static int pow(int base, int exponet)
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
#}
########################################################
pow:
	bne $a1, $zero, pow_init #if exp != 0 then jump to loop else return 1
	addi $v0, $zero, 1 #return 1
	jr $ra #return

pow_init:
	#initalize result = base
	add $t0, $a0, $zero #$t0 = result 
	addi $s0, $zero, 1 #$s0 = i = 1
	add $t3, $zero, $a0 # $t3 = base
	add $t4, $zero, $a1 # $t4 = exponet

pow_loop:
	#check if i < exponet
	slt $t2, $s0, $t4 # $t2 = 1 if (i < exponet)
			  # $t2 = 0 if (i >= exponet)
	beq $t2, $zero, end_pow # goto end_pow

	# save return address before calling
	addi $sp, $sp, -4
	sw $ra, 0($sp)

	# calling function	
	add $a0, $t0, $zero #$a0 = result 
	add $a1, $t3, $zero #$a1 = base 
	jal multiply # call the function

	#mov return of mult to result
	add $t0, $v0, $zero #result = mult(result, base)
		
	# pop return address
	lw $ra, 0($sp)
	addi $sp, $sp, 4

	addi $s0, $s0, 1 #i++	
	j pow_loop

end_pow: 
	add $v0, $t0, $zero 
	jr $ra #return to address
		
		
	
	
	
