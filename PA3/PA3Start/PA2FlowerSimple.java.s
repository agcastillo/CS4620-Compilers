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

	# load int 3
	ldi r24,lo8(3)
	ldi r25,hi8(3)
	# push int 3 onto stack
	push r25
	push r24

	/* cast to byte */
	pop r25
	pop r24
	push r24

	# load int 4
	ldi r24,lo8(4)
	ldi r25,hi8(4)
	# push int 4 onto stack
	push r25
	push r24

	/* cast to byte */
	pop r25
	pop r24
	push r24

	/* load 6 for Meggy.Color.VIOLET */
	ldi r22, 6
	push r22

	/*MeggySetPixel: pop args off of stack */
	pop r20
	pop r22
	pop r24
	/* Draw pixels, display slate */
	call _Z6DrawPxhhh
	call _Z12DisplaySlatev
/* epilogue start */
	endLabel:
	jmp endLabel
	ret
	.size   main, .-main
