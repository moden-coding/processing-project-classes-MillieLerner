import processing.core.PApplet;

public class redBullets {
    private float x;
    private float y;
    private PApplet canvas;

// I know you wanted me to combine the classes, but I didn't. I don't really have a good reason other than I didn't want to.

    public redBullets(float xx, float yy, PApplet c) {
        x = xx;
        y = yy;
        canvas = c;
    }

    public void display() {
        //so you can see the bullet.
        canvas.fill(255, 0, 0);
        canvas.circle(x, y, 10);

    }

    public void move() {
        // bullets go
        x += 7;
    }

    public float checkhit(float blueX, float blueY) {
        //bullets check if they were successful
        float d = canvas.dist(x, y, blueX, blueY);
        return d;
    }

    public boolean checkpowerred(float wallX, float wallY) {
        //or did they hit the powerup ball?
        boolean d = false;
        if (x - 5 >= wallX && y >= wallY && y <= wallY + 60) {
            d = true;
        }
        return d;
    }

    public boolean hitpower(float powerupX, float powerupY, int size) {
        //Nope. But did they hit the power up shield?
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
