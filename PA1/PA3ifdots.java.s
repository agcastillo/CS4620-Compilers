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
	push 	r29
	push 	r28
	in 	r28,__SP_L__
	in 	r29,__SP_H__

	/* prologue: function */
	call 	_Z18MeggyJrSimpleSetupv

	call	_Z16CheckButtonsDownv
	lds	r24, Button_Up
	# if button value is zero, push 0 else push 1
	tst	r24
	breq	UPNOTCLICKED
	
	# here we should call the blue light at (3,4)
	ldi	r24, lo8(3)
	ldi	r25, hi8(3)

	push	r25
	push	r24


	pop	r24
	pop	r25
	push	r24

	# Load constant int 3
	ldi	r24,lo8(7)
	ldi	r25,hi8(7)
	#push two byte expression to stack
	push	r25
	push	r24

	# Casting int to byte by popping
	# 2 byte off stack and only pushing lower bits back on
	# lower order bits are on top of stack
	pop 	r24
	pop 	r25
	push 	r24

	#Color expression Meggy.Color.BLUE
	ldi	r22,5
	#push one byte expression onto stack
	push	r22

	#Meggy.setPixel(x,y,color) call
	#load a one byte expression off the stack
	pop	r20
	#load a one byte expression off stack
	pop	r22
	pop	r24
	call 	_Z6DrawPxhhh
	call 	_Z12DisplaySlatev
	
UPNOTCLICKED:
	call 	_Z16CheckButtonsDownv
	lds 	r24, Button_Down
	# if button value is zero, push 0 else push 1
	tst r24
	breq endLabel
	
	# here we should call the red light at (3,0) 
	# here we should call the blue light at (3,4)
	# load constant int 3
	ldi	r24, lo8(3)
	ldi	r25, hi8(3)

	push	r25
	push	r24


	pop	r24
	pop	r25
	push	r24

	# Load constant int 10
	ldi	r24,lo8(0)
	ldi	r25,hi8(0)
	#push two byte expression to stack
	push	r25
	push	r24

	# Casting int to byte by popping
	# 2 byte off stack and only pushing lower bits back on
	# lower order bits are on top of stack
	pop 	r24
	pop 	r25
	push 	r24

	#Color expression Meggy.Color.RED
	ldi	r22,1
	#push one byte expression onto stack
	push	r22

	#Meggy.setPixel(x,y,color) call
	#load a one byte expression off the stack
	pop	r20
	#load a one byte expression off stack
	pop	r22
	pop	r24
	call 	_Z6DrawPxhhh
	call 	_Z12DisplaySlatev

endLabel:
	jmp endLabel
	ret
	.size   main, .-main
	
