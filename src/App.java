import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;

public class App extends PApplet {
    //this is just a longggg list of variables
    ArrayList<redBullets> redbull = new ArrayList<>();
    ArrayList<blueBullets> blueberry = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();

    float redX = 100;
    float redY = 250;
    float blueX = 700;
    float blueY = 250;
    float puY = random(30,470);
    float putY = random(30, 470);
    float puthY=random(30, 470);

    boolean blueUp = false;
    boolean blueDown = false;
    boolean redUp = false;
    boolean redDown = false;

    boolean bluehit = false;
    boolean redhit = false;

    boolean powerRed = false;
    boolean powerBlue = false;
    boolean powertwoRed = false;
    boolean powertwoBlue = false;
    boolean powerthreeRed=false;
    boolean powerthreeBlue=false;

    boolean powerused = false;
    boolean powertwoused=false;
    boolean powerthreeused=false;

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
    double timerR = 0;
    double timerB = 0;

    double speed = 2;

    double basetime = 0;
    double bs = 0;
    double timeatstart=0;
    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        background(0);
        //this is taking the amount of wins each team had in total
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

    }

    public void settings() {
        size(800, 500);
    }

    public void draw() {
        //this is actually building everything. The scenes lets me change screens
        if (scene == 0) {
            startscreen();
        } else if (scene == 1) {
            activescreen();
            building();
            hit();
            powerup();
            poweruptwo();

        } else if (scene == 2) {
            endscreen();
        }
    }

    public void keyPressed() {
        //this is so everything can move and shoot by pressing keys. 
        // RED
        if (keyCode == 'W') {
            redUp = true;
        } else if (keyCode == 'S') {
            redDown = true;
        }
        //so RED can shoot
        if (keyCode == 'D') {
            if (millis() - start >= delay) {
                redbull.add(new redBullets(redX + 25, redY, this));
                start = millis();
            }
        }

         // BLUE
        if (keyCode == UP) {
            blueUp = true;
        } else if (keyCode == DOWN) {
            blueDown = true;
        }
        //so BLUE can shoot:
        if (keyCode == LEFT) {
            if (millis() - start >= delay) {
                blueberry.add(new blueBullets(blueX - 25, blueY, this));
                start = millis();
            }
        }

        //So you can start the game
        if (keyCode == ' ') {
            scene = 1;
            redhit = false;
            bluehit = false;
            timeatstart=millis()/1000.0-bs/1000.0;
        }
    }

    public void keyReleased() {
        //So things can stop moving
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
        //this is establishing the active screen during play.
        background(0);
        textSize(30);
        fill(255,0,0);
        text("Lives: ", 30, 35);

         //I know there are a lot of circles. I don't know how to fix that. They are different and in different spots so...
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

        //this builds the RED circle
        fill(255, 0, 0);
        circle(redX, redY, 50);
        //this creates a new RED bullet
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

        //So RED knows how to move
        if (redDown == true) {
            redY += 3;
            if (redY >= 475) {
                redY = 475;
            }
        }

        //this builds the BLUE circle
        fill(0, 0, 255);
        circle(blueX, blueY, 50);
        //this creates BLUE bullets
        for (blueBullets b : blueberry) {
            b.display();
            b.move();
        }
        //so BLUE can move
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
        //This builds the small ball to hit for the powerup
        fill(0, 255, 0);
        circle(400, puY, size);
        puY += speed;
        if (puY > height-15 || puY <= 15) {
            speed = -speed;
        }
    }

    public void powerupballtwo(){
        //this builds another power up
        fill(0, 255, 0);
        circle(400, putY, size);
        putY += speed;
        if (putY > height-15 || putY <= 15) {
            speed = -speed;
        }
    }

    public void powerup() {

        basetime = millis() - bs;
        if (basetime/1000.0-timeatstart>= 5&&basetime/1000.0-timeatstart<=20) {
            powerupball();
            //to establish when RED hits the powerup
            for (int i = 0; i < redbull.size(); i++) {
                if (redbull.get(i).hitpower(400, puY, size) == true) {
                    redbull.remove(i);
                    powerRed = true;
                    powerused = true;
                    timerR = basetime / 1000.0;
                }
            }
            //same thing for BLUE
            for (int i = 0; i < blueberry.size(); i++) {
                if (blueberry.get(i).hitpower(400, puY, size) == true) {
                    blueberry.remove(i);
                    powerBlue = true;
                    powerused = true;
                    timerB = basetime / 1000.0;
                }
            }

            //this makes sure the ball stays gone
             if (powerused == true||powertwoused==true||powerthreeused==true) {
                size = 0;
            } else {
                size = 30;
            }

            //if RED has the power up
            if (powerRed == true) {
                if (basetime / 1000.0 - timerR < 7) {
                    fill(0, 255, 0);
                    rect(redX + 35, redY - 30, 15, 60);

                    for (int i = 0; i < blueberry.size(); i++) {
                        if (blueberry.get(i).checkpowerred(redX + 50, redY - 30) == true) {
                            blueberry.remove(i);
                        }
                    }
                }
            }

            //if BLUE has the powerup
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
    
    public void poweruptwo(){
        //this is another power up. It was sooo much easier to make...
        basetime=millis()-bs;
        if(basetime/1000.0-timeatstart>= 25&&basetime/1000.0-timeatstart<= 40){
            powerupballtwo();

            for (int i = 0; i < redbull.size(); i++) {
                if (redbull.get(i).hitpower(400, putY, size) == true) {
                    redbull.remove(i);
                    powertwoRed = true;
                    powertwoused = true;
                    timerR = basetime / 1000.0;
                }
            }
            //same thing for BLUE
            for (int i = 0; i < blueberry.size(); i++) {
                if (blueberry.get(i).hitpower(400, putY, size) == true) {
                    blueberry.remove(i);
                    powertwoBlue = true;
                    powertwoused = true;
                    timerB = basetime / 1000.0;
                }
            }

            //this makes sure the ball stays gone
             if (powerused == true||powertwoused==true||powerthreeused==true) {
                size = 0;
            } else {
                size = 30;
            }

            if(powertwoBlue==true){
                healthB++;
            }

            if(powertwoRed==true){
                healthR++;
            }

        }
    }
    
    public void hit() {
        //check if RED is hit
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

        //check if BLUE is hit
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

        //if either are hit, this happens
        if (redhit == true || bluehit == true) {
            scene++;
            redbull.clear();
            blueberry.clear();

            // adds the scores
            if (bluehit == true) {
                redscore++;
                totalredscore++;
            }
            if (redhit == true) {
                bluescore++;
                totalbluescore++;
            }
            //and puts them into the other file
            totalscores(totalbluescore, totalredscore);
        }

    }

    public void startscreen() {
        //start screen. Wow, I would never have guessed.
        background(0);

        fill(255);
        textSize(40);
        text("Welcome!", 310, 100);
        text("Press SPACE to start", 250, 400);

        textSize(25);
        fill(255,0,0);
        text("Red:  Press 'W' to move up. Press 'S' to move down. D to fire.", 50, 160);
        text("Total red score: " + totalredscore, 130, 200);

        fill(0,0,255);
        text("Blue:  Press 'UP' to move up. Press 'DOWN' to move down. LEFT to fire.", 50, 250);
        text("Total red score: " + totalbluescore, 130, 300);

        basetime -= millis();
    }

    public void endscreen() {
        //end screen... crazy
        background(0);
        fill(255,0,0);
        text("Current red score: " + redscore, 275, 100);
        fill(0,0,255);
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

    public void totalscores(int blue, int red) {
        // but this does the heavy lifting of actually establishing all time scores

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


}
