import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;

public class App extends PApplet {
    ArrayList<redBullets> redbull = new ArrayList<>();
    ArrayList<blueBullets> blueberry = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();

    float redX = 100;
    float redY = 250;
    float blueX = 700;
    float blueY = 250;
    float puY = 30;

    boolean blueUp = false;
    boolean blueDown = false;
    boolean redUp = false;
    boolean redDown = false;

    boolean bluehit = false;
    boolean redhit = false;

    boolean powerRed = false;
    boolean powerBlue = false;
    boolean powerredtwo = false;

    int scene = 0;
    int size = 40;

    int redscore = 0;
    int bluescore = 0;
    int totalbluescore = 0;
    int totalredscore = 0;

    double startR = 0;
    double delayR = 250;
    double startB = 0;
    double delayB = 250;

    double speed = 1.5;
    double timerR = 0;
    double timerB=0;
    double basetime=0;
    double bs=0;

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

    }

    public void settings() {
        size(800, 500);
    }

    public void draw() {
        if (scene == 0) {
            startscreen();
        } else if (scene == 1) {
            background(0);
            buildingblue();
            hitred();
            buildingred();
            hitblue();
            powerupRed();
            // powerupblue();

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
            if (millis() - startB >= delayB) {
                blueberry.add(new blueBullets(blueX - 25, blueY, this));
                startB = millis();
            }
        }

        // RED
        if (keyCode == 'W') {
            redUp = true;
        } else if (keyCode == 'S') {
            redDown = true;
        }

        if (keyCode == 'D') {
            if (millis() - startR >= delayR) {
                redbull.add(new redBullets(redX + 25, redY, this));

                startR = millis();
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

    public void hitred() {
        int p = 0;
        while (p < blueberry.size()) {
            blueBullets m = blueberry.get(p);
            if (m.checkhit(redX, redY) < 30) {
                blueberry.remove(m);
                redhit = true;
            }
            p++;
        }

        if (redhit == true) {
            scene++;
            bluescore++;
            totalbluescore++;
            redbull.clear();
            blueberry.clear();
            totalscores(totalbluescore, totalredscore);
        }

    }

    public void hitblue() {
        int i = 0;
        while (i < redbull.size()) {
            redBullets b = redbull.get(i);
            if (b.checkhit(blueX, blueY) < 30) {
                redbull.remove(b);
                bluehit = true;
            }
            i++;
        }
        if (bluehit == true) {
            scene++;
            redscore++;
            totalredscore++;
            redbull.clear();
            blueberry.clear();
            totalscores(totalbluescore, totalredscore);
        }

    }

    public void buildingred() {
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
    }

    public void buildingblue() {
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
    }

    public void endscreen() {
        background(0);
        fill(255);
        text("Current red score: " + redscore, 275, 100);
        text("Current blue score: " + bluescore, 275, 300);
        redY = 250;
        blueY = 250;
        bs=millis()/1000.0;
        puY=30;
        powerRed=false;
        powerBlue=false;
        timerR-=millis()/1000.0;
        timerB-=millis()/1000.0;
    }

    public void powerupRed() {
        basetime=millis()-bs;
        // System.out.println(basetime);
        double starttime = millis()/1000.0-bs;


        if (starttime >= 2) {
            // System.out.println("Yes");
            fill(0, 255, 0);

            circle(400, puY, size);
            puY += speed;
            if (puY > height || puY <= 0) {
                speed = -speed;
            }

            int p = 0;
            while (p < redbull.size()) {
                if (redbull.get(p).hitpower(400, puY, size) == true) {
                    redbull.remove(p);
                    powerRed = true;
                    size = 0;
                    timerR = basetime / 1000.0;
                }
                p++;

            }

            int q = 0;
            while (q < blueberry.size()) {
                if (blueberry.get(q).hitpower(400, puY, size) == true) {
                    blueberry.remove(q);
                    powerBlue = true;
                    size = 0;
                    timerB = basetime / 1000.0;
                }
                q++;
            }

            if (powerRed == true) {
                if (millis() / 1000.0 - timerR < 4) {
                    fill(0, 255, 0);
                    rect(redX + 35, redY - 30, 15, 60);

                    int i = 0;
                    while (i < blueberry.size()) {
                        if (blueberry.get(i).checkpowerred(redX + 50, redY - 30) == true) {
                            blueberry.remove(i);
                        }
                        i++;
                    }
                }
            }

            if (powerBlue == true) {
                if (millis() / 1000.0 - timerB < 4) {
                    fill(0, 255, 0);
                    rect(blueX - 50, blueY - 30, 15, 60);

                    int i = 0;
                    while (i < redbull.size()) {
                        if (redbull.get(i).checkpowerred(blueX-65, blueY - 30) == true) {
                            redbull.remove(i);
                        }
                        i++;
                    }
                }
            }
        }
    }

    // public void powerupblue(){
    //     double starttime = basetime() / 1000.0;

    //     if (starttime >= 2) {
    //         fill(0, 255, 0);

    //         circle(400, puY, size);
    //         puY += speed;
    //         if (puY > height || puY <= 0) {
    //             speed = -speed;
    //         }

    //         int p = 0;
    //         while (p < blueberry.size()) {
    //             if (blueberry.get(p).hitpower(400, puY) == true) {
    //                 blueberry.remove(p);
    //                 powerBlue = true;
    //                 size = 0;
    //                 timerB = basetime() / 1000.0;
    //             }
    //             p++;

    //         }

    //         if (powerBlue == true) {
    //             if (basetime() / 1000.0 - timerB < 4) {
    //                 fill(0, 255, 0);
    //                 rect(blueX - 50, blueY - 30, 15, 60);
    //                 int i = 0;
    //                 while (i < redbull.size()) {
    //                     if (redbull.get(i).checkpowerred(redX + 50, redY - 30) == true) {
    //                         redbull.remove(i);
    //                     }
    //                     i++;
    //                 }
    //             }
    //         }
    //     }
    // }
}
