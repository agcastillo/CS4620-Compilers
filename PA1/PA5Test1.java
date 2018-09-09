/**
* PA5Test1
* Lights up a square starting at 0,0 with dimensions given
*/

import meggy.Meggy;

class PA5Test1{

    public static void main(String[] whatever){
        new SquareCreator().CreateSquare(5);
        Meggy.delay(200);
    }
}
class SquareCreator{
    int currX;
    int currY;
    public void CreateSquare(int len){
        if (len < 8){
            currX = 0;
            currY = 0;
            while (currX < len){
                Meggy.setPixel((byte)currX, (byte)0, Meggy.Color.BLUE);
                Meggy.setPixel((byte)currX, (byte)len, Meggy.Color.BLUE);
                currX = currX + 1;
            }
            while (currY < len){
                Meggy.setPixel((byte)0, (byte)currY, Meggy.Color.BLUE);
                Meggy.setPixel((byte)len, (byte)currY, Meggy.Color.BLUE);
                currY = currY + 1;
            }

        }
    }
    public void clearSquare(int len){
        if (len < 8){
            currX = 0;
            currY = 0;
            while (currX < len){
                Meggy.setPixel((byte)currX, (byte)0, Meggy.Color.DARK);
                Meggy.setPixel((byte)currX, (byte)len, Meggy.Color.DARK);
                currX = currX + 1;
            }
            while (currY < len){
                Meggy.setPixel((byte)0, (byte)currY, Meggy.Color.DARK);
                Meggy.setPixel((byte)len, (byte)currY, Meggy.Color.DARK);
                currY = currY + 1;
            }

        }
    }
}