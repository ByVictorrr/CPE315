#
# public class mod{
#	public static void main(String args[])
#	{	
#			modulo(2,4);
#	}
#	public int modulo(int num, int div)
#	{
#		return num - (num/div)*div;
#	}
#}

.globl prompt
	
.data

prompt1:
	.asciiz " Enter num \n\n"
prompt2:
	.asciiz " Enter div \n\n"
output:
	.asciiz "\n Remainder = "

.text

main:
	# Displays the welcome message ( load 4 into $v0 to display it)
	ori	$v0, $0, 4
	
	#This gnerates the starting address for enter number
	#(assume the regs contain zero first)
	lui 	$a0, 0x1001
	syscall 

	#read 1st integer from user (num)
	ori 	$v0, $0, 5
	syscall
	
	# clear 
		
