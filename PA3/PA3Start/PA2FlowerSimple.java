/**
 * PA2FlowerSimple.java
 * 
 * Canonical example for PA2.  Displays a static picture of a flower.
 * 
 * Language features tested:
 * 	-constant integer expressions, simple version does not have these
 *  -casting to the byte type
 *  -calling the MeggyJava built-in setPixel function
 *  -inline comments
 *  -block comments
 * 
 * Requirements for the example:
 * 	-At least two colors.
 *  -At least 10 setPixel calls.
 *   Be careful to stay within the bounds!
 *
 * MMS, 1/18/11
 * MMS, 1/20/13, modified to PA2 canonical example
 */

import meggy.Meggy;

class PA2FlowerSimple {
	public static void main(String[] whatever){
	        // Lower left petal, clockwise  
	        Meggy.setPixel( (byte)3, (byte)4, Meggy.Color.VIOLET );
	        
    }
}
