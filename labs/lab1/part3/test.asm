#########################################################################
# CPE 315: Lab - part 3
# Author: Victor Delaplaine 
# Date: 2019-04-13
# Description :  Takes in three numbers higher, lower, divisor
#		 prints out the higher/divisor and lower/divisor
#		
#
# Assumptions : (1) inputs are positive number
#
# Java Code : 
#
#import java.util.*; 
#
#public class divide{ 
#
#	public static void main(String []args){
#		int upper = 2, lower = 10;
#		int div = 65536;
#		System.out.println("higher = " + divide(upper,lower,div).upper);
#		System.out.println("lower = " + divide(upper,lower,div).lower);
#	}
#	//return x/y
#	public static twoInts divide (int upper, int lower, int divisor ) {
#		// x*z = x+x+...+x_ztimes
#		//if (divisor == 0) return NULL;
#
#		// int is 32 bits so
#		int result_upper = upper, result_lower = lower;
#
#		int masked_LSB = 0;
#		for (int i = 0; i< log2(divisor); i++)
#		{
#
#			masked_LSB  = result_upper & 1; //mask one to get LSB before shifting 
#
#			result_upper = result_upper >> 1 ; //then shift right to the by one
#
#			result_lower = result_lower >> 1; //the lower shift right 
#			
#			masked_LSB = masked_LSB << 31; //shift masked to MSB to or with lower
#
#			result_lower = result_lower | masked_LSB;
#		}
#		
#		twoInts l = new twoInts();
#		l.upper = result_upper;
#		l.lower = result_lower;
#
#		return l;
#	}
#	public static double log2(double num)
#	{
#		return (Math.log(num)/Math.log(2));
#	}
#
#}
#######################################################
	
.data

promptHigher:
	.asciiz "Enter higher \n\n"
promptLower:
	.asciiz "Enter lower \n\n"
promptDivisor:
	.asciiz "Enter the divisor = \n\n"
outputZeroDiv:
	.asciiz "Cant divide by 0, this will give infinity\n\n"
output1:
	.asciiz "higher/divisor = \n\n"
output2:
	.asciiz "lower/divisor = \n\n"


.text
########################################################################
# main subroutine: prints out high/divisor and low/divsor
#
# Parameters : n/a
# Return : n/a
#		 
# Local :
#	
#	Saved : n/a
#
#	
#	Temp  : $t0 - high
#	      : $t1 - lower
#	      : $t2 - divisor
###########################################################################
main:

#==================I/O=================
	# Output to the console 
	la $a0, promptHigher
	addi $v0, $zero, 4 	
	syscall 	
	
	# input to the console higherj
	addi $v0, $zero, 5	
	syscall 	
			
	# $t0 - higher
	add $t0, $v0, $zero 

	# Ask user to enter lower
	la $a0, promptLower
	addi $v0, $zero, 4 	
	syscall 	

	# input to the console
	addi $v0, $zero, 5	
	syscall 	

	# $t1 = lower
	add $t1, $v0, $zero 


	# Ask user to enter divisor
	la $a0, promptDivisor
	addi $v0, $zero, 4 	
	syscall 	

	# input to the console
	addi $v0, $zero, 5	
	syscall 	

	# $t2 = divisor
	add $t2, $v0, $zero 	


	#pass arguments
	add $a0, $t0, $zero
	add $a1, $t1, $zero
	add $a2, $t2, $zero
	
#==================================================	
	# call function divide(higher,lower,divisor)
	jal divide
	
	# Output the result
	add $a0, $v0, $zero

	addi $v0, $zero, 1
	syscall
	
	#end program 
	li $v0, 10
	syscall

########################################################################
# log_2 subroutine: Takes in a number and returns log_2(num)
#
# Parameters  : $a0 - num
# 
# Return : $v0 - log_2(num)
#
# Local :
#	
#	Saved : n/a
#
#	
#	Temp  : $t0 - result
#	      : $t1 - hold 1
###########################################################################
# Java Code:
#	public class int log_2(int num)
#	{
#		int result = 0;
#		while(num != 1)
#		{
#			num = num >> 1;
#			result++;
#		}
#		return result;
#	}
##########################################################################
log_2:
	add $t0, $zero, $zero # result = 0
	addi $t1, $zero, 1 #$t2 = 1
	#while num != 1
log_2_loop:
	beq $a0, $t1, end_log_2
	srl $a0, $a0, 1 #num=>>1
	addi $t0, $t0, 1 #result ++
	j log_2_loop
end_log_2:
	add $v0, $t0, $zero # v0 = result (return)
	jr $ra

########################################################################
# divide subroutine: Takes in three number upper, lower and divisor, and
#		     returns upper/divsior and lower/divisor
#
# Parameters  : $a0 - upper 
#	      : $a1 - lower
#	      : $a2 - divisor
# 
# Return : $v0 - upper/divisor
#	   $v1 - lower/divisor
#
# Local :
#	
#	Saved : n/a
#
#	
#	Temp  : $t0 - result_upper 
#	      : $t1 - result_lower
#	      : $t2 - masked_LSB
#	      : $t3 - i
#	      : $t4 - condition test
#	      : $t5 - log_2(divisor)
###########################################################################
# Java Code:
#	public static twoInts divide (int upper, int lower, int divisor ) {
#		// x*z = x+x+...+x_ztimes
#		if (divisor == 0){
#			System.out.println("result = inifinity");
#			return;
#		}
#
#		// int is 32 bits so
#		int result_upper = upper, result_lower = lower;
#
#		int masked_LSB = 0;
#		for (int i = 0; i< log2(divisor); i++)
#		{
#
#			masked_LSB  = result_upper & 1; //mask one to get LSB before shifting 
#
#			result_upper = result_upper >> 1 ; //then shift right to the by one
#
#			result_lower = result_lower >> 1; //the lower shift right 
#			
#			masked_LSB = masked_LSB << 31; //shift masked to MSB to or with lower
#
#			result_lower = result_lower | masked_LSB;
#		}
#		
#		twoInts l = new twoInts();
#		l.upper = result_upper;
#		l.lower = result_lower;
#
#		return l;
#	}
#############################################################################
divide: 
	# check first if $a2 - divisor is 0
	bne $a2, $zero, divide_init #goto divide_init if divisor != 0
	#print out cant divide by zero

	# Output the result
	la $a0, outputZeroDiv

	addi $v0, $zero, 1
	syscall

	jr $ra #return

divide_init:
	#get log2(divisor)
	addi $sp, $sp, -8
	sw $ra, 4($sp)
	sw $a0, 0($sp)

	add $a0, $a2, $zero
	jal log_2 # lets get log2(divisor)

	lw $ra, 4($sp)
	lw $a0, 0($sp)
	addi $sp, $sp, -8
	
	add $t5, $v0 , $zero #$t5 = log_2(divisor)

	add $t0, $a0, $zero #result_higher = higher
	add $t1, $a1, $zero #result_lower = lower
	add $t2, $zero, $zero #masked_LSB = 0
	add $t3, $zero, $zero #i=0


divide_loop:
	slt $t4, $t3, $a2 # $t4 = 1 if (i<divisor)
		          # $t4 = 0 if (i>=divisor)
	beq $t4, $zero, divide_end
	andi $t2, $t0, 1 # masked_LSB  = result_upper & 1
	srl $t0, $t0, 1	 # result_upper = result_upper >> 1
	srl $t1, $t1, 1	 #result_lower = result_lower >> 1;	
	sll $t2, $t2, 31 #masked_LSB = masked_LSB << 31
	or $t1, $t1, $t2 #result_lower = result_lower | masked_LSB;
	j divide_loop

divide_end: 
	add $v0, $t0, $zero
	add $v1, $t1, $zero
	jr $ra
