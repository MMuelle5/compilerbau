.data
nl:  .asciiz "\n"
inputStr: .space 80
thx35: .asciiz "Vielen Dank für die korrekte Eingabe."
i34: .word 0
l35: .asciiz "Bitte geben sie eine 3 ein:"
grAls35: .asciiz "Ihre Zahl ist grösser als 3"
klAls35: .asciiz "Ihre Zahl ist kleiner als 3"
.text
main:
la $a0, 0
la $t9, i34
sw $a0, 0($t9)
while60:
lw $a0, i34
beqz $a0, endwhile60
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
beq $a0, 1,if81
lw $a1, i34
la $a2, 3
sgt $a0, $a1, $a2
beq $a0, 1,if94
cont81:
jal while60
endwhile60:
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

if81:
la $a0, klAls35
li $v0, 4
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
jal cont81
if94:
la $a0, grAls35
li $v0, 4
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
jal cont81
