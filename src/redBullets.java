import processing.core.PApplet;

public class redBullets {
    private float x;
    private float y;
    private PApplet canvas;
    // private int track = canvas.millis();

    public redBullets(float xx, float yy, PApplet c) {
        x = xx;
        y = yy;
        canvas = c;
    }

    public void display() {

        canvas.fill(255, 0, 0);
        canvas.circle(x, y, 10);

    }

    public void move() {
        x += 7;
    }

    public float checkhit(float blueX, float blueY) {
        float d = canvas.dist(x, y, blueX, blueY);
        return d;
    }

    public boolean checkpowerred(float wallX, float wallY) {
        boolean d = false;
        if (x - 5 >= wallX && y >= wallY && y <= wallY + 60) {
            d = true;
        }
        return d;
    }

    public boolean hitpower(float powerupX, float powerupY, int size) {
        boolean p = false;
        float d = canvas.dist(x, y, powerupX, powerupY);
        if (size > 20) {
            if (d <= 5 + (size / 2)) {
                p = true;
                System.out.println("Red accept");
            }
        }
        return p;
    }
}
