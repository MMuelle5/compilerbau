.data
nl:  .asciiz "\n"
inputStr: .space 80
ret35: .asciiz "die Zahl ist"
i34: .word 0
out35: .asciiz "bitte Zahl eingeben"
.text
main:
la $a0, out35
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
la $a0, ret35
li $v0, 4
syscall
lw $a0, i34
li $v0, 1
syscall
move $t9, $a0
la $a0, nl
li $v0, 4
syscall
move $a0, $t9
li $v0, 10
syscall
