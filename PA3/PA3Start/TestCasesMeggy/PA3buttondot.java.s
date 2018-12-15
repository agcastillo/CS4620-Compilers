    .file  "main.java"
__SREG__ = 0x3f
__SP_H__ = 0x3e
__SP_L__ = 0x3d
__tmp_reg__ = 0
__zero_reg__ = 1
    .global __do_copy_data
    .global __do_clear_bss
    .text
.global main
    .type   main, @function
main:
    push r29
    push r28
    in r28,__SP_L__
    in r29,__SP_H__
/* prologue: function */
    call _Z18MeggyJrSimpleSetupv 
    /* Need to call this so that the meggy library gets set up */
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 2
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
MJ_L0:


ldi 	 r22, 1
push 	 r22
pop 	 r24
ldi 	 r25, 0
cp 	 r24, r25
brne 	 MJ_L1
jmp 	 MJ_L2


MJ_L1:
###In if Statement
call	 _Z16CheckButtonsDownv

lds 	 r24, Button_A
# if button value is zero, push 0 else push 1
tst 	 r24
breq 	 MJ_L4
MJ_L3:
ldi 	 r24, 1
jmp MJ_L5
MJ_L4:
MJ_L5:
push 	 r24
pop 	 r24
ldi 	 r25, 0
cp 	 r24,r25
### True goes to the first label
brne 	 MJ_L6
jmp 	 MJ_L7
### Then label for if statement
MJ_L6:
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 6
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 0
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 0
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
jmp 	 MJ_L8
MJ_L7:
###In if Statement
call	 _Z16CheckButtonsDownv

lds 	 r24, Button_B
# if button value is zero, push 0 else push 1
tst 	 r24
breq 	 MJ_L10
MJ_L9:
ldi 	 r24, 1
jmp MJ_L11
MJ_L10:
MJ_L11:
push 	 r24
pop 	 r24
ldi 	 r25, 0
cp 	 r24,r25
### True goes to the first label
brne 	 MJ_L12
jmp 	 MJ_L13
### Then label for if statement
MJ_L12:
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 4
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 0
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 0
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
jmp 	 MJ_L14
MJ_L13:
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 2
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
### Load constant int 7
ldi 	 r24, lo8(7)
ldi 	 r25, hi8(7)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 0
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Load constant int 0
ldi 	 r24, lo8(0)
ldi 	 r25, hi8(0)
push 	 r25
push 	 r24
### Byte Cast Operation
pop 	 r24
pop 	 r25
push 	 r24
### Color expression
ldi 	 r22, 0
push 	 r22
#Load bytes
pop 	 r20
pop 	 r22
pop 	 r24
call _Z6DrawPxhhh
call _Z12DisplaySlatev
MJ_L14:
### End of If Statement
MJ_L8:
### End of If Statement
### Load constant int 100
ldi 	 r24, lo8(100)
ldi 	 r25, hi8(100)
push 	 r25
push 	 r24
pop 	 r24
pop 	 r25
call 	 _Z8delay_msj
jmp 	 MJ_L0


MJ_L2:


/* epilogue start */
    endLabel:
    jmp endLabel
    ret
    .size   main, .-main
