/**
* PA3Test3
* If button A is clicked, it initiates a series of blue lights which appear and then disappear 0.1 seconds later
* in order on row 1 
* ! , false both included in while() condition
*/
import meggy.Meggy;

class PA3Test3{

    public static void main(String[] whatever){

        while(!false)
        {

            if (Meggy.checkButton(Meggy.Button.A)){ // If A is clicked
                Meggy.setPixel((byte)0,(byte)0, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)0, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)1, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)1, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)2, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)2, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)3, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)3, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)4, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)4, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)5, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)5, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)6, Meggy.Color.BLUE);
                Meggy.delay(100);
                Meggy.setPixel((byte)0,(byte)6, Meggy.Color.DARK);
                Meggy.setPixel((byte)0,(byte)7, Meggy.Color.BLUE);
            }
            else {
                Meggy.setPixel((byte)0, (byte)0, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)1, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)2, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)3, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)4, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)5, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)6, Meggy.Color.RED);
                Meggy.setPixel((byte)0, (byte)7, Meggy.Color.RED);
                Meggy.delay(100);
                Meggy.setPixel((byte)0, (byte)0, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)1, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)2, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)3, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)4, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)5, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)6, Meggy.Color.DARK);
                Meggy.setPixel((byte)0, (byte)7, Meggy.Color.DARK);
            }
            Meggy.delay(1000);

        }
    }
}
