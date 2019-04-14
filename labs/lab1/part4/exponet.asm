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
#		(2) 
#
# Java Code : 
#
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
	
	
