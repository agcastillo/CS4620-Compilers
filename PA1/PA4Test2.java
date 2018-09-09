/** 
* PA4Test2
* Uses toneStart function, the Meggy.Tone type, 
*/

import meggy.Meggy;

class PA4Test2{
    public static void main(String[] whatever){
        while(true){
            if (Meggy.checkButton(Meggy.Button.A) && Meggy.checkButton(Meggy.Button.B)){
                new Stripes().PixelLine((byte)5, (byte)0, false);
            }
            else{
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
}
class Stripes{
    public void PixelLine(int x, int y, boolean rowMajor){
        if (( x < 7) && (rowMajor == true)){
            Meggy.setPixel((byte)x,(byte)y, Meggy.Color.ORANGE);
            Meggy.delay(100);
            if (0 < x){
                Meggy.setPixel((byte)(x-1), (byte)y, Meggy.Color.DARK);
            }
            this.PixelLine(x+1, y, rowMajor);
        }
        if ((y < 7) && (rowMajor == false)){
            Meggy.setPixel((byte)x,(byte)y,Meggy.Color.ORANGE);
            if (0 < y){
                Meggy.setPixel((byte)x, (byte)(y-1), Meggy.Color.DARK);
            }
            this.PixelLine(x,y+1,rowMajor);
        }
    }
    public void SetStripe(byte x){
        Meggy.toneStart(Meggy.Tone.Cs3, 800);
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


