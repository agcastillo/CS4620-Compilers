/** 
* PA2Test1
* Sets four diagonal rows of 5 pixels to different colors
*/
import meggy.Meggy;

class PA2Test1
{
    public static void main(String[] whatever)
    {
        // Setting first diagonal line of 5 pixels to green
        Meggy.setPixel((byte)0, (byte)0, Meggy.Color.GREEN);
        Meggy.setPixel((byte)1, (byte)1, Meggy.Color.GREEN);
        Meggy.setPixel((byte)2, (byte)2, Meggy.Color.GREEN);
        Meggy.setPixel((byte)3, (byte)3, Meggy.Color.GREEN);
        Meggy.setPixel((byte)4, (byte)4, Meggy.Color.GREEN);

        // Setting second diagonal line of 5 pixels to blue
        Meggy.setPixel((byte)0, (byte)1, Meggy.Color.BLUE);
        Meggy.setPixel((byte)1, (byte)2, Meggy.Color.BLUE);
        Meggy.setPixel((byte)2, (byte)3, Meggy.Color.BLUE);
        Meggy.setPixel((byte)3, (byte)4, Meggy.Color.BLUE);
        Meggy.setPixel((byte)4, (byte)5, Meggy.Color.BLUE);

        // Setting third diagonal line of 5 pixels to yellow
        Meggy.setPixel((byte)0, (byte)2, Meggy.Color.YELLOW);
        Meggy.setPixel((byte)1, (byte)3, Meggy.Color.YELLOW);
        Meggy.setPixel((byte)2, (byte)4, Meggy.Color.YELLOW);
        Meggy.setPixel((byte)3, (byte)5, Meggy.Color.YELLOW);
        Meggy.setPixel((byte)4, (byte)6, Meggy.Color.YELLOW);

        // Setting fourth diagonal line of 5 pixels to violet
        Meggy.setPixel((byte)0, (byte)3, Meggy.Color.VIOLET);
        Meggy.setPixel((byte)1, (byte)4, Meggy.Color.VIOLET);
        Meggy.setPixel((byte)2, (byte)5, Meggy.Color.VIOLET);
        Meggy.setPixel((byte)3, (byte)6, Meggy.Color.VIOLET);
        Meggy.setPixel((byte)4, (byte)7, Meggy.Color.VIOLET);
    }

}