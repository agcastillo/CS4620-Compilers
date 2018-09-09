/** 
* PA5Test2
* Simple script, ball goes up and down between x = 1 and x = 7 inclusive, paddle can be moved left or right 
* based on the left and right buttons on the row x = 0, could be fleshed out further to be a simple paddle game
*/
import meggy.Meggy;

class PA5Test2{
    public static void main(String[] whatever){
        
        while (true){
            new Game().run();
        }
    }
}

class Game{
    boolean BallUp;
    public void run(){
        Paddle thePaddle;
        Ball theBall;
        thePaddle = new Paddle();
        theBall = new Ball();
        thePaddle.run();
        theBall.run();
        while (true){
            if (Meggy.checkButton(Meggy.Button.Left)){
                thePaddle.moveLeft();
            }
            if (Meggy.checkButton(Meggy.Button.Right)){
                thePaddle.moveRight();
            }

            if (theBall.getCurrX() == 1){ // Ball has to move back up
                BallUp = true;
            }
            if (theBall.getCurrX() == 7){ // Ball has to move back down
                BallUp = false;
            }
            if (BallUp){
                theBall.moveUp();
            }
            else{
                theBall.moveDown();
            }
            Meggy.delay(100);

        }
    }
}
class Paddle{
    int currY;
    public void moveRight(){
        if (currY + 1 < 8){ // Check if moving left would take you off the board
            currY = currY + 1;
            Meggy.setPixel((byte)0, (byte)currY, Meggy.Color.BLUE);
            Meggy.setPixel((byte)0,(byte)(currY - 1), Meggy.Color.DARK);
        }
    }
    public void moveLeft(){
        if (0 < (currY - 1)){ // Check if moving right would take you off the board
            currY = currY - 1;
            Meggy.setPixel((byte)0, (byte)currY, Meggy.Color.BLUE);
            Meggy.setPixel((byte)0,(byte)(currY + 1), Meggy.Color.DARK);
        }
    }
    public void run(){
        currY = 0;
        Meggy.setPixel((byte)0, (byte)0, Meggy.Color.BLUE);
    }
    public int getCurrY(){
        return currY;
    }
}

class Ball{
    int currX;
    public void moveUp(){
        if (currX + 1 < 8){
            currX = currX + 1;
            Meggy.setPixel((byte)currX, (byte)0, Meggy.Color.ORANGE);
            Meggy.setPixel((byte)(currX-1), (byte)0, Meggy.Color.DARK);
        }
    }
    public void moveDown(){
        if (0 < currX - 1){
            currX = currX - 1;
            Meggy.setPixel((byte)currX, (byte)0, Meggy.Color.ORANGE);
            Meggy.setPixel((byte)(currX+1), (byte)0, Meggy.Color.DARK);
        }
    }
    public void run(){
        currX = 7;
        Meggy.setPixel((byte)7, (byte)0, Meggy.Color.ORANGE);
    }
    public int getCurrX(){
        return currX;
    }
    
}