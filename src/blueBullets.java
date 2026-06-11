import processing.core.PApplet;

public class blueBullets {
    private float x;
    private float y;
    private PApplet canvas;

    //it's the same thing as the other class... just different directions and colors
    public blueBullets(float xx, float yy, PApplet c){
        x=xx;
        y=yy;
        canvas=c;
        
    }

    public void display() {
        canvas.fill(0,0,255);
        canvas.circle(x, y, 10);
    }
    
    public void move(){
        x-=7;
    }

    public float checkhit(float redX, float redY){
        float d=canvas.dist(x,y,redX,redY);
        return d;
    }

    public boolean checkpowerred(float wallX, float wallY){
        boolean d=false;
        if(x+5<=wallX&&y>=wallY&&y<=wallY+60){
            d=true;
        }
        return d;
    }

    public boolean hitpower(float powerupX, float powerupY, int size) {
        boolean p = false;
        float d = canvas.dist(x, y, powerupX, powerupY);
        if (size > 20) {
            if (d <= 5 + (size / 2)) {
                p = true;
            }
        }
        return p;
    }
}
