.data
nl:  .asciiz "\n"
inputStr: .space 80
ld35: .asciiz "Division: "
lm35: .asciiz "Multiplikation: "
ls35: .asciiz "Subtraktion: "
la35: .asciiz "Addition: "
zahl234: .word 0
zahl134: .word 0
.text
main:
la $a0, 150
la $t9, zahl134
sw $a0, 0($t9)
la $a0, 50
la $t9, zahl234
sw $a0, 0($t9)
la $a0, la35
li $v0, 4
syscall
lw $a1, zahl134
lw $a2, zahl234
add $a0, $a1, $a2
li $v0, 1
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
la $a0, ls35
li $v0, 4
syscall
lw $a1, zahl134
lw $a2, zahl234
neg $a2, $a2
add $a0, $a1, $a2
li $v0, 1
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
la $a0, lm35
li $v0, 4
syscall
lw $a1, zahl134
lw $a2, zahl234
mul $a0, $a1, $a2
li $v0, 1
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
la $a0, ld35
li $v0, 4
syscall
lw $a1, zahl134
lw $a2, zahl234
div $a0, $a1, $a2
li $v0, 1
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
li $v0, 10
syscall
