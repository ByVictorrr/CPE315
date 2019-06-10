addi $a0, $zero, 3
addi $a1, $zero, 3

beq $a1, $a0, hi


hi:
	bne $a1, $a0, b
b:
	add $a0, $a1, $a0

g:

	beq $a0, $a1, g


	



