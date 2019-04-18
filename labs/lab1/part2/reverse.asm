#################################################################################################
# CPE 315: Lab 1 - part 2
# Author: Victor Delaplaine 
# Section: 03
# Description : Reverses the bits of a 32 bit number,
# 		That is change the lsb to msb.
#
# Assumptions : (1) The number is positve
#
# Java Code : 
# 	import java.util.*;
#
#	public class reverse{
#
#	public static void main(String []args){
#		System.out.print(Long.toBinaryString(reverseBits(1)));
#	}
#	public static int reverseBits(int num)
#	{
#			int  revB = 0;
#			int pos ; //bit size -1 ;
#			int mask;
#			for (pos=7; pos>0; pos--)
#			{
#				mask = num & 1;
#				for (int i = 0; i< pos; i++)
#					mask = mask << 1; //first mask num in lsb, then shift that bit to left by pos bits 
#
#				revB = revB + mask;
#
#				num = num >> 1; // then get the next bit in lsb and repeat at pos= pos-1 
#
#			}
#			return revB;
#	}
#
#

#}
################################################################################################
.globl prompt
	
.data

promptNum:
	.asciiz "Enter num \n\n"
output:
	.asciiz "\n Reversed bits number = "


.text
############################################################
# main subroutine: prints out remainder of num/div
# Parameters : n/a
# Return : n/a
# Tweaked Parmeter : $a1 = num
#		     $a2 = div
############################################################
main:
	
	#Prompt user to enter num
	#(assume the regs contain zero first)
	
	addi 	$v0, $zero, 4
	lui	$a0, 0x1001
	syscall 

	#read 1st integer from user (num)
	ori 	$v0, $0, 5
	syscall
	
	# transfer num to $s0
	add $s0, $zero, $v0	
	
	# pass arugment to $a0
	add $a0, $zero, $s0	

	# Pass num and div to function mod
	jal reverse

	add $t0, $v0, $zero

	# print output text result
	addi 	$v0, $zero, 4
	lui	$a0, 0x1001
	ori 	$a0, $a0, 13
	syscall 


	#print out result (t0)
	add $a0, $t0, $zero
	addi $v0, $zero, 1
	syscall 

	#end program 
	addi $v0, $zero, 10
	syscall
		
############################################################################################################
# reverse subroutine: returns the reversed bits of num
# Parameters : $a0 - x
# Return : $v0 - reverse(num)
# Locals : 
#	Saved:n/a
#
#	Temp:	   : $t1 - revB
#		   : $t2 - pos
#		   : $t3 - mask then used for left shift by pos
#		   : $t4 - test is pos > 0
#		   : $t5 - i
##############################################################################################################
# Java Code:
#	public static int reverseBits(int num)
#	{
#			int  revB = 0;
#			int pos ; //bit size -1 ;
#			int mask;
#			for (pos=7; pos>0; pos--)
#			{
#				mask = num & 1;
#				for (int i = 0; i< pos; i++)
#					mask = mask << 1; //first mask num in lsb, then shift that bit to left by pos bits 
#
#				revB = revB + mask;
#
#				num = num >> 1; // then get the next bit in lsb and repeat at pos= pos-1 
#
#			}
#			return revB;
#	}
#
#
#
#
################################################################################################################
reverse: 
	add $t1, $zero, $zero #revB=0
	addi $t2, $zero, 31 #pos = 32-1
	
loop_outer:	#first check if pos <= 0 if so goto end
	slt $t4, $zero, $t2 # $t4 = 1, if 0 < pos
	beq $t2, $zero, return  # if pos <= 0 goto return
	andi $t3, $a0, 1 # $t3 = x & 1	
	add $t5, $zero, $zero # i=0

loop_inner: # for shifting variable amount
	slt $t4, $t5, $t2 #$t4 = 1 if i<pos
			  #$t4 = 0 if i>=pos
	beq $t4, $zero, end_inner
	
	sll $t3, $t3, 1 # mask = mask << 1
	addi $t5, $t5, 1 #i++
	j loop_inner 

end_inner:
	add $t1, $t1, $t3 #revB = revB + mask
	srl $a0, $a0, 1 # num = num >> 1
	addi $t2, $t2, -1 # pos = pos -1
	j loop_outer #}

return: add $v0, $t1, $zero
	jr $ra
	
	
