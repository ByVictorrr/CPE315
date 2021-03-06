########################################################
# CPE 315: Lab 1 - part 1
# Name: Victor Delaplaine 
# Section: 03
# # Description : Takes in two numbers num, and div,
#		then it gets the remainder of num/div
#
# Assumptions : (1) Both num and div are divisable by two,
#		(2) Both num and div are positive.
#
# Java Code : 
#
# 	public class mod{
#		public static void main(String args[])
#		{	
#			modulo(2,4);
#		}
#		public int modulo(int num, int div)
#		{
#			return num - (num/div)*div;
#		}
#	}
#######################################################
.globl prompt
	
.data

promptNum:
	.asciiz "Enter num \n\n"
promptDiv:
	.asciiz "Enter div \n\n"
output:
	.asciiz "\nRemainder = "


.text
############################################################
# main subroutine: prints out remainder of num/div
# Parameters : 
# Return : n/a
# Locals : 
############################################################
main:
	#Prompt user to enter num
	#(assume the regs contain zero first)
	
	ori	$v0, $0, 4
	lui 	$a0, 0x1001
	
	syscall 

	#read 1st integer from user (num)
	ori 	$v0, $0, 5
	syscall
	
	# transfer num to $a1
	add $a1, $zero, $v0	
	
	#Prompt user to enter div
	#(assume the regs contain zero first)	
	ori	$v0, $0, 4
	lui     $a0, 0x1001
	ori     $a0, $a0, 13
	syscall 

	#read 2nd integer from user (div)
	ori 	$v0, $0, 5
	syscall
	
	# transfer div to $a2
	add $a2, $zero, $v0	
			
	# Pass num and div to function mod
	jal mod	
	add $t0, $v0, $zero # move result to temp

	#output result
	#(assume the regs contain zero first)
	
	ori	$v0, $0, 4
	lui     $a0, 0x1001
	ori     $a0, $a0, 26

	syscall 

	#print out result (v1)
	addi $v0, $zero, 1
	add $a0, $t0, $zero
	syscall 

	#end program 
	addi $v0, $zero, 10
	syscall
	
############################################################
# mod subroutine: returns the remainder num/div
# Parameters : $a1 - num
#              $a2 - div
#
#
# Return : $v0 - num % div
# Local:
#	Saved: n/a
#
#	Temp:
#		   : $t1 - result
#		   : $t2 - n = 2^i
#		   : $t3 - used for constant 1 
###########################################################################
# Java Code:
#		public int modulo(int num, int div)
#		{
#			return num - (num/div)*div;
#		}
#
##########################################################################
mod: 
	add $t1, $zero, $a1 #result = num
	add $t2, $zero, $a2 #n = div
	addi $t3, $zero, 1 # t3 = 1

loop1:	#first divide result = num/dir

	beq $t2, $t3, loop2_init # branch to loop 2_init when n=1=2^0, meaning it has shifted num i times if n=2^i
	# each iteration gives 2^(i-1)
	#else
	srl $t1, $t1, 1 # result = logical shift right (result)
	
	srl $t2, $t2, 1 # i = i/2 

	j loop1 #}

loop2_init: #then  multiply by div i.e result = result*div
	add $t2, $zero, $a2 #i = div , reset it
loop2:	
	beq $t2, $t3, end # # branch to loop 2_init when n=1=2^0, meaning it has shifted num i times if n=2^i

	#else
	sll $t1, $t1, 1 # result = logical shift left (result)

	srl $t2, $t2, 1 # n=2^(i-1)
	j loop2 #}


end: 	sub $v0, $a1, $t1 
	jr $ra
	
	
