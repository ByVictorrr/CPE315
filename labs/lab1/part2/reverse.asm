########################################################
# CPE 315: Lab 1 - part 2
# Author: Victor Delaplaine 
# Date: 4/8/19
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
#			for (pos=7; pos>0; pos--)
#			{
#				revB=revB+(num & 1)<< pos; //first mask num in lsb, then shift that bit to left by pos bits 
#				num = num >> 1; // then get the next bit in lsb and repeat at pos= pos-1 
#			}
#			return revB;
#	}
#
#}
#######################################################
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
	
	ori	$v0, $0, 4
	la 	$a0, promptNum
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


	#print out result (v1)
	add $a0, $v0, $zero
	li $v0, 36
	syscall 

	#end program 
	li $v0, 10
	syscall
	
############################################################
# reverse subroutine: returns the reversed bits of num
# Parameters : $a0 - x
# Return : $v0 - reverse(num)
# Tweaked Parmeter : $t1 - revB
#		   : $t2 - pos
#		   : $t3 - mask then used for left shift by pos
#		   : $t4 - test is pos > 0
###########################################################
reverse: 
	add $t1, $zero, $zero #revB=0
	addi $t2, $zero, 31 #pos = 32-1
	
loop:	#first check if pos <= 0 if so goto end
	slt $t4, $zero, $t2 # $t4 = 1, if 0 < pos
	beq $t2, $zero, return  # if pos <= 0 goto return
	andi $t3, $a0, 1 # $t3 = x & 1
	sllv $t3, $t3, $t2 # $t3 = $t3 << pos
	add $t1, $t1, $t3 #revB = revB + $t3
	srl $a0, $a0, 1 # num = num >> 1
	addi $t2, $t2, -1 # pos = pos -1
	j loop #}

return: add $v0, $t1, $zero
	jr $ra
	
	
