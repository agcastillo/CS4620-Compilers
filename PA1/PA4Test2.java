/** 
* PA4Test2
* 
*/

import meggy.Meggy;

class PA4Test2{
    public static void main(String[] whatever){
        while(true){
            if (Meggy.checkButton(Meggy.Button.A)){
                new Stripes().SetStripe((byte)0);
            }
            if (Meggy.checkButton(Meggy.Button.B)){
                new Stripes().SetStripe((byte)1);
            }
            if (Meggy.checkButton(Meggy.Button.Up)){
                new Stripes().SetStripe((byte)2);
            }
            if (Meggy.checkButton(Meggy.Button.Down)){
                new Stripes().SetStripe((byte)3);
            }
            if (Meggy.checkButton(Meggy.Button.Left)){
                new Stripes().SetStripe((byte)4);
            }
            if (Meggy.checkButton(Meggy.Button.Right)){
                new Stripes().SetStripe((byte)5);
            }
        }
    }
}
class Stripes{
    public void SetStripe(byte x){
        Meggy.setPixel(x,(byte)0, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)0, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)1, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)1, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)2, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)2, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)3, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)3, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)4, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)4, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)5, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)5, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)6, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)6, Meggy.Color.DARK);
        Meggy.setPixel(x,(byte)7, Meggy.Color.VIOLET);
        Meggy.delay(100);
        Meggy.setPixel(x,(byte)7, Meggy.Color.DARK);
    }
}


