/**
* Sets pixels 0,(0-5) to different colors based on whether or not a specific button was clicked
* After 1 second delay, it resets all the pixels back to black
* Covers:
* IF, WHILE, setPixel(), delay(), checkButton()
*/
import meggy.Meggy;

class PA3Test1
{
    public static void main(String[] whatever)
    {
        
        while (true)
        {
            if (Meggy.checkButton(Meggy.Button.A))
            {
                Meggy.setPixel((byte)0,(byte)0, Meggy.Color.RED);
            }
            if (Meggy.checkButton(Meggy.Button.B))
            {
                Meggy.setPixel((byte)0,(byte)1, Meggy.Color.ORANGE);
            }
            if (Meggy.checkButton(Meggy.Button.Up))
            {
                Meggy.setPixel((byte)0,(byte)2, Meggy.Color.YELLOW);
            }
            if (Meggy.checkButton(Meggy.Button.Down))
            {
                Meggy.setPixel((byte)0,(byte)3, Meggy.Color.GREEN);
            }
            if (Meggy.checkButton(Meggy.Button.Left))
            {
                Meggy.setPixel((byte)0,(byte)4, Meggy.Color.BLUE);
            }
            if (Meggy.checkButton(Meggy.Button.Right))
            {
                Meggy.setPixel((byte)0,(byte)5, Meggy.Color.VIOLET);
            }
            
            Meggy.delay(1000);

            Meggy.setPixel((byte)0,(byte)0, Meggy.Color.DARK);
            Meggy.setPixel((byte)0,(byte)1, Meggy.Color.DARK);
            Meggy.setPixel((byte)0,(byte)2, Meggy.Color.DARK);
            Meggy.setPixel((byte)0,(byte)3, Meggy.Color.DARK);
            Meggy.setPixel((byte)0,(byte)4, Meggy.Color.DARK);
            Meggy.setPixel((byte)0,(byte)5, Meggy.Color.DARK);


        }

    }
}
