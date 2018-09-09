/**
* PA5Test3
* Code has some bugs, needs to be fixed
*/
import meggy.Meggy;

class PA5Test3{
    public static void main(String[] whatever){
        new Grid().run();
    }
}
class Grid{
    int x;
    Snake theSnake;
    public void setUpGrid(){
        x = 0;
        while (x < 8){
            Meggy.setPixel((byte)x, (byte)0, Meggy.Color.WHITE);
            Meggy.setPixel((byte)x, (byte)7, Meggy.Color.WHITE);
            Meggy.setPixel((byte)0, (byte)x, Meggy.Color.WHITE);
            Meggy.setPixel((byte)7, (byte)x, Meggy.Color.WHITE);
            x = x + 1;
        }

    }
    public void run(){
        this.setUpGrid();
        theSnake = new Snake();
        theSnake.run();
        while (true){
            if (Meggy.checkButton(Meggy.Button.Up)){
                theSnake.moveUp();
            }
            if (Meggy.checkButton(Meggy.Button.Down)){
                theSnake.moveDown();
            }
            if (Meggy.checkButton(Meggy.Button.Right)){
                theSnake.moveRight();
            }
            if (Meggy.checkButton(Meggy.Button.Left)){
                theSnake.moveLeft();
            }
        }
    }
}
class Snake{
    int headX;
    int headY;

    int tailX;
    int tailY;

    public void moveRight(){
        if (headX + 1 < 7){
            Meggy.setPixel((byte)(headX+1), (byte)headY, Meggy.Color.GREEN);
            Meggy.setPixel((byte)tailX, (byte)tailY, Meggy.Color.DARK);
            tailX = headX;
            tailY = headY;
            headX = headX + 1;
            headY = headY;
        }
    }
    public void moveLeft(){
        if (0 < headX-1){
            Meggy.setPixel((byte)(headX-1), (byte)headY, Meggy.Color.GREEN);
            Meggy.setPixel((byte)tailX, (byte)tailY, Meggy.Color.DARK);
            tailX = headX;
            tailY = headY;
            headX = headX - 1;
            headY = headY;
        }
    }
    public void moveDown(){
        if (0 < headY - 1){
            Meggy.setPixel((byte)headX, (byte)(headY-1), Meggy.Color.GREEN);
            Meggy.setPixel((byte)tailX, (byte)tailY, Meggy.Color.DARK);
            tailX = headX;
            tailY = headY;
            headX = headX;
            headY = headY-1;
        }
    }
    public void moveUp(){
        if (headY + 1 < 7){
            Meggy.setPixel((byte)headX, (byte)(headY+1), Meggy.Color.GREEN);
            Meggy.setPixel((byte)tailX, (byte)tailY, Meggy.Color.DARK);
            tailX = headX;
            tailY = headY;
            headX = headX;
            headY = headY+1;
        }
    }
    public void run(){
        Meggy.setPixel((byte)1,(byte)1, Meggy.Color.GREEN);
        tailX = 1;
        tailY = 1;
        Meggy.setPixel((byte)1,(byte)2, Meggy.Color.GREEN);
        headX = 1;
        tailY = 2;
    }
}