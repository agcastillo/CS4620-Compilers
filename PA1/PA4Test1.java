/** 
* PATest1
* Lights up 3 consecutive dots in a row with colors blue, green, yellow
* Uses function definitions, function calls, and "new" object creation syntax
*/
import meggy.Meggy;

class PA4Test1{
    
    public static void main(String[] whatever){
       new Test().Run();
    }
}

class Test{
    public void Blue(byte x, byte y){
        Meggy.setPixel(x,y,Meggy.Color.BLUE);
    }
    public void Yellow(byte x, byte y){
        Meggy.setPixel(x,y,Meggy.Color.YELLOW);
    }
    public void Green(byte x, byte y){
        Meggy.setPixel(x,y,Meggy.Color.GREEN);
    }
    public void Run(){
        this.Blue((byte)0,(byte)0);
        this.Green((byte)0,(byte)1);
        this.Yellow((byte)0,(byte)2);
    }
}