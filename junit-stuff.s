.data
nl:  .asciiz "\n"
inputStr: .space 80
klAls35: .asciiz "Sie solltens doch wisse, diese Zahl ist nicht 3!"
thx35: .asciiz "Vielen Dank für die korrekte Eingabe."
i34: .word 0
l35: .asciiz "Bitte geben sie eine 3 ein:"
grAls35: .asciiz "Ihre Zahl ist grösser als 3"
.text
main:
la $a0, 0
la $t9, i34
sw $a0, 0($t9)
while55:
lw $a1, i34
la $a2, 3
sne $a0, $a1, $a2
beqz $a0, endwhile55
la $a0, l35
li $v0, 4
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
li $v0, 5
la $a0, inputStr
syscall
move $a0, $v0
la $t9, i34
sw $a0, 0($t9)
lw $a1, i34
la $a2, 3
slt $a0, $a1, $a2
beq $a0, 1,if76
lw $a1, i34
la $a2, 8
sgt $a0, $a1, $a2
beq $a0, 1,if88
lw $a1, i34
la $a2, 3
sgt $a0, $a1, $a2
beq $a0, 1,if101
cont76:
jal while55
endwhile55:
la $a0, thx35
li $v0, 4
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
li $v0, 10
syscall

nichtDrei:
la $a0, klAls35
li $v0, 4
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
jr $ra

if76:
jal nichtDrei
jal cont76
if88:
jal nichtDrei
jal cont76
if101:
la $a0, grAls35
li $v0, 4
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
jal cont76
