import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;

public class App extends PApplet {
    ArrayList<redBullets> redbull = new ArrayList<>();
    ArrayList<blueBullets> blueberry = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();

    PImage red;
    PImage blue;

    float redX = 100;
    float redY = 250;
    float blueX = 700;
    float blueY = 250;
    float puY = random(30,470);

    boolean blueUp = false;
    boolean blueDown = false;
    boolean redUp = false;
    boolean redDown = false;

    boolean bluehit = false;
    boolean redhit = false;

    boolean powerRed = false;
    boolean powerBlue = false;

    boolean powerused = false;

    int scene = 0;
    int size = 30;

    int redscore = 0;
    int bluescore = 0;
    int totalbluescore = 0;
    int totalredscore = 0;

    int healthB=4;
    int healthR=4;

    double start = 0;
    double delay = 250;

    double speed = 2;
    double timerR = 0;
    double timerB = 0;
    double basetime = 0;
    double bs = 0;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        background(0);

        try (Scanner scanner = new Scanner(Paths.get("output.txt"))) {
            int i = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (i == 0) {
                    totalbluescore = Integer.valueOf(row);
                } else if (i == 1) {
                    totalredscore = Integer.valueOf(row);
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        red= loadImage("src/Data/Red spaceship.jpg");

        blue=loadImage("src/Data/Blue Spaceship.jpg");
    }

    public void settings() {
        size(800, 500);
    }

    public void draw() {
        if (scene == 0) {
            startscreen();
        } else if (scene == 1) {
            activescreen();
            building();
            hit();
            powerup();

        } else if (scene == 2) {
            endscreen();
        }
    }

    public void keyPressed() {

        // BLUE
        if (keyCode == UP) {
            blueUp = true;
        } else if (keyCode == DOWN) {
            blueDown = true;
        }
        if (keyCode == LEFT) {
            if (millis() - start >= delay) {
                blueberry.add(new blueBullets(blueX - 25, blueY, this));
                start = millis();
            }
        }

        // RED
        if (keyCode == 'W') {
            redUp = true;
        } else if (keyCode == 'S') {
            redDown = true;
        }
        if (keyCode == 'D') {
            if (millis() - start >= delay) {
                redbull.add(new redBullets(redX + 25, redY, this));
                start = millis();
            }
        }
        if (keyCode == ' ') {
            scene = 1;
            redhit = false;
            bluehit = false;
        }
    }

    public void keyReleased() {

        // BLUE
        if (keyCode == UP) {
            blueUp = false;
        } else if (keyCode == DOWN) {
            blueDown = false;
        }

        // RED
        if (keyCode == 'W') {
            redUp = false;
        } else if (keyCode == 'S') {
            redDown = false;
        }

    }

    public void activescreen(){
        background(0);
        textSize(30);
        fill(255,0,0);
        text("Lives: ", 30, 35);
        if(healthR>=4){
            circle(125, 25, 30);
            circle(160, 25, 30);
            circle(195, 25, 30);
            circle(230, 25, 30);
        }
        if(healthR==3){
            circle(125, 25, 30);
            circle(160, 25, 30);
            circle(195, 25, 30);
        }
        if(healthR==2){
            circle(125, 25, 30);
            circle(160, 25, 30);
        }
        if(healthR==1){
            circle(125, 25, 30);
        }

        fill(0,0,255);
        text("Lives: ", 555, 35);
        if(healthB>=4){
            circle(660, 25, 30);
            circle(695, 25, 30);
            circle(730, 25, 30);
            circle(765, 25, 30);
        }
        if(healthB==3){
            circle(660, 25, 30);
            circle(695, 25, 30);
            circle(730, 25, 30);
        }
        if(healthB==2){
            circle(660, 25, 30);
            circle(695, 25, 30);
        }
        if(healthB==1){
            circle(660, 25, 30);
        }
    }

    public void building() {
        fill(255, 0, 0);
        circle(redX, redY, 50);
        for (redBullets r : redbull) {
            r.display();
            r.move();
        }
        if (redUp == true) {
            redY -= 3;
            if (redY < 25) {
                redY = 25;
            }
        }
        if (redDown == true) {
            redY += 3;
            if (redY >= 475) {
                redY = 475;
            }
        }

        fill(0, 0, 255);
        circle(blueX, blueY, 50);
        for (blueBullets b : blueberry) {
            b.display();
            b.move();
        }
        if (blueUp == true) {
            blueY -= 3;
            if (blueY < 25) {
                blueY = 25;
            }
        }
        if (blueDown == true) {
            blueY += 3;
            if (blueY > 475) {
                blueY = 475;
            }
        }
    }

    public void powerupball() {
        fill(0, 255, 0);
        circle(400, puY, size);
        puY += speed;
        if (puY > height || puY <= 0) {
            speed = -speed;
        }
    }

    public void powerup() {
        basetime = millis() - bs;

        if (basetime / 1000.0 >= 5&&basetime/1000.0<=20) {
            powerupball();

            for (int i = 0; i < redbull.size(); i++) {
                if (redbull.get(i).hitpower(400, puY, size) == true) {
                    redbull.remove(i);
                    powerRed = true;
                    powerused = true;
                    timerR = basetime / 1000.0;
                }
            }

            for (int i = 0; i < blueberry.size(); i++) {
                if (blueberry.get(i).hitpower(400, puY, size) == true) {
                    blueberry.remove(i);
                    powerBlue = true;
                    powerused = true;
                    timerB = basetime / 1000.0;
                }
            }

            if (powerused == true) {
                size = 0;
            } else {
                size = 30;
            }

            if (powerRed == true) {
                if (basetime / 1000.0 - timerR < 4) {
                    fill(0, 255, 0);
                    rect(redX + 35, redY - 30, 15, 60);

                    for (int i = 0; i < blueberry.size(); i++) {
                        if (blueberry.get(i).checkpowerred(redX + 50, redY - 30) == true) {
                            blueberry.remove(i);
                        }
                    }
                }
            }

            if (powerBlue == true) {
                if (basetime / 1000.0 - timerB < 4) {
                    fill(0, 255, 0);
                    rect(blueX - 50, blueY - 30, 15, 60);

                    for (int i = 0; i < redbull.size(); i++) {
                        if (redbull.get(i).checkpowerred(blueX - 65, blueY - 30) == true) {
                            redbull.remove(i);
                        }

                    }
                }
            }
        }
    }

    public void hit() {

        for (int i = 0; i < blueberry.size(); i++) {
            blueBullets m = blueberry.get(i);
            if (m.checkhit(redX, redY) < 30) {
                blueberry.remove(m);
                healthR--;
            }
        }
        if(healthR<=0){
            redhit=true;
        }

        for (int i = 0; i < redbull.size(); i++) {
            redBullets b = redbull.get(i);
            if (b.checkhit(blueX, blueY) < 30) {
                redbull.remove(b);
                healthB--;
            }
        }
        if(healthB<=0){
            bluehit=true;
        }

        if (redhit == true || bluehit == true) {
            scene++;
            redbull.clear();
            blueberry.clear();

            if (bluehit == true) {
                redscore++;
                totalredscore++;
            }
            if (redhit == true) {
                bluescore++;
                totalbluescore++;
            }
            totalscores(totalbluescore, totalredscore);
        }

    }

    public void totalscores(int blue, int red) {
        String filePath = "output.txt"; // Path to the text file

        if (scores.size() > 0) {
            scores.set(0, blue);
            scores.set(1, red);
        } else if (scores.size() == 0) {
            scores.add(blue);
            scores.add(red);
        }
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (int m : scores) {
                writer.println(m);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

    }

    public void startscreen() {
        background(0);
        fill(255);
        textSize(40);
        text("Welcome!", 310, 100);
        text("Press SPACE to start", 250, 400);
        textSize(25);
        text("Red:  Press 'W' to move up. Press 'S' to move down. D to fire.", 130, 160);
        text("Total red score: " + totalredscore, 130, 200);
        text("Blue:  Press 'UP' to move up. Press 'DOWN' to move down. LEFT to fire.", 50, 250);
        text("Total red score: " + totalbluescore, 130, 300);
        basetime -= millis();
    }

    public void endscreen() {
        background(0);
        fill(255);
        text("Current red score: " + redscore, 275, 100);
        text("Current blue score: " + bluescore, 275, 300);
        redY = 250;
        blueY = 250;
        bs = millis();
        puY = 30;
        powerRed = false;
        powerBlue = false;
        timerR =0;
        timerB =0;
        powerused = false;
        healthR=4;
        healthB=4;
    }

}
