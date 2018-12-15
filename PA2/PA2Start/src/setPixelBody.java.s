	# We want to call setPixel((byte) INT_1, (byte)INT_2, Meggy.Color.X) ;
	# First we load  INT_1 
	ldi	r24, lo8(INT_1)
	ldi	r25, hi8(INT_1)

	push	r25
	push	r24


	pop	r24
	pop	r25
	push	r24

	# Here we load INT_2
	ldi	r24,lo8(INT_2)
	ldi	r25,hi8(INT_2)
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
	# The X.Value that we load below is based on the Symbol value assigned in the terminals file from resources
	# Red == 1, Orange == 2, Yellow == 3, Green == 4, Blue == 5, Violet == 6, White == 7
	ldi	r22,X.Value 
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
