/**
* PA3Test2
* Includes else statements
* How do I incorporate the ( "&&" | "==" | "+" | "-" | "*" ) Expression grammar?
* Include !, false
*/

import meggy.Meggy;

class PA3Test2{

    public static void main(String[] whatever){

        while (true){

            if (Meggy.checkButton(Meggy.Button.A) && Meggy.checkButton(Meggy.Button.B)){
                // Both A & B correspond to a mix of the two colors (GREEN) on 0,0 and 0,1
                Meggy.setPixel((byte)0, (byte)0, Meggy.Color.GREEN);
                Meggy.setPixel((byte)0, (byte)1, Meggy.Color.GREEN);
            }
            else {
                if (Meggy.checkButton(Meggy.Button.A)){ // Just A corresponds to a blue light on 0,0
                    Meggy.setPixel((byte)0, (byte)0, Meggy.Color.BLUE);
                    Meggy.setPixel((byte)0, (byte)1, Meggy.Color.DARK);
                }
                else {
                    if (Meggy.checkButton(Meggy.Button.B)){ // Just B corresponds to a yellow light on 0,1
                        Meggy.setPixel((byte)0, (byte)1, Meggy.Color.YELLOW);
                        Meggy.setPixel((byte)0, (byte)0, Meggy.Color.DARK);

                    }
                }
            }
            Meggy.delay(2000); // Delay 2 seconds
            Meggy.setPixel((byte)0, (byte)0, Meggy.Color.DARK);
            Meggy.setPixel((byte)0, (byte)1, Meggy.Color.DARK);
            Meggy.delay(2000); // Delay 2 seconds
        }
    }
}