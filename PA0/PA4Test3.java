/**
* PA4Test3
* If A is clicked, 0,0 is set to Green. If B is clicked, 0,0 is set to Red.
* PlayToneByPixel reads pixel 0,0 and plays one of two melodies (three consecutive 100ms tones) based on whether
* the pixel is green or red
*/
import meggy.Meggy;

class PA4Test3{
    public static void main(String[] whatever){
        while(true){
            if (Meggy.checkButton(Meggy.Button.A)){
                Meggy.setPixel((byte)0,(byte)0,Meggy.Color.GREEN);
                new ToneTest().PlayToneByPixel();
                Meggy.setPixel((byte)0,(byte)0, Meggy.Color.DARK);
            }
            else{
                if (Meggy.checkButton(Meggy.Button.B)){
                    Meggy.setPixel((byte)0,(byte)0, Meggy.Color.RED);
                    new ToneTest().PlayToneByPixel();
                    Meggy.setPixel((byte)0,(byte)0, Meggy.Color.DARK);
                }
            }
            Meggy.delay(100); 
        }
    }
}

class ToneTest{
    public void PlayToneByPixel(){
        if (Meggy.getPixel((byte)0,(byte)0) == Meggy.Color.GREEN){
            this.PlayTone(0);
        }
        if (Meggy.getPixel((byte)0,(byte)0) == Meggy.Color.RED){
            this.PlayTone(1);
        }
        
    }
    public void PlayTone(int x){
        if (x == 0){
            Meggy.toneStart(Meggy.Tone.Cs3, 100);
            Meggy.toneStart(Meggy.Tone.D3, 100);
            Meggy.toneStart(Meggy.Tone.Ds3, 100);
        }
        if (x == 1){
            Meggy.toneStart(Meggy.Tone.F3, 100);
            Meggy.toneStart(Meggy.Tone.Fs3, 100);
            Meggy.toneStart(Meggy.Tone.G3, 100);
        }
    }
}