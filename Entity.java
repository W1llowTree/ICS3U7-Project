/*

 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Entity extends Sprite {
    protected int imgWidth;
    protected int imgHeight;

    protected double speed;
    protected double accel;
    protected int maxSpeed;

    protected double KBSpeed = 0.4;

    protected int gravity = 1;
    protected int jumpHeight = 20;

    protected int jumpCount = 2;

    protected Vector2 dir = new Vector2();
    protected ArrayList<Tile> platMap;
    protected ArrayList<Tile> stageMap;
    protected ArrayList<Tile> allMap = new ArrayList<Tile>();

    protected char orientation = 'l';

    protected int xShiftR;
    protected int xShiftL;
    protected int yShift;

    protected Camera c;

    protected int drawX = 0, drawY = 0;

    protected double percent = 0;
    protected boolean isHit = false;

    public Entity(int x, int y, int width, int height, int imgWidth, int imgHeight, ArrayList<Tile> platMap,
            ArrayList<Tile> stageMap, Camera c) {
        super(x, y, width, height, c);
        this.platMap = platMap;
        this.stageMap = stageMap;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.c = c;
        allMap.addAll(platMap);
        allMap.addAll(stageMap);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDrawX() {
        return drawX;
    }

    public int getDrawY() {
        return drawY;
    }

    public Vector2 getDir() {
        return dir;
    }

    private void verticalCollisions() {
        for (Tile tile : allMap) {
            if (getBounds().intersects(tile.getBounds())) {
                if (dir.getY() > 0 && y + height - tile.getTTop() < dir.getY() + 1) {
                    y = tile.getTTop() - height;
                    dir.setY(0);
                }
            }
        }

        for (Tile tile : stageMap) {
            if (getBounds().intersects(tile.getBounds())) {
                if (dir.getY() < 0) {
                    y = tile.getTBot();
                    dir.setY(0);
                }
            }
        }
    }

    private void horizontalCollisions() {
        for (Tile tile : stageMap) {
            if (getBounds().intersects(tile.getBounds())) {
                if (dir.getX() < 0) {
                    x = tile.getTRight();
                } else if (dir.getX() > 0) {
                    x = tile.getTLeft() - width;
                }
            }
        }
    }

    protected boolean onGround() {
        for (Tile tile : allMap) {
            if (y == tile.getTTop() - height) {
                jumpCount = 2;
                return true;
            }
        }
        return false;
    }

    protected void applyGravity() {
        dir.incrementY(gravity);
        y += dir.getY();
    }

    public char getOrientation() {
        return orientation;
    }

    // Calculating Knockback Distance
    public double calcKB(int damage, int weight, int scalingKB, int baseKB) {
        double distance = ((((percent / 10 + percent * damage / 20) * 200 / (weight + 100) * 1.4) + 18) * scalingKB)
                + baseKB;
        return distance;
    }

    // Applying Knockback
    public void applyKB(Hitbox h, char side) {
        isHit = true;

        int baseKB = h.getData()[3];
        int scalingKB = h.getData()[4];
        int angle = h.getData()[5];
        int damage = h.getData()[6];
        int weight = 100;

        double xRatio = 0;
        double yRatio = 0;
        double dist = calcKB(damage, weight, scalingKB, baseKB);
        xRatio = dist * Math.cos(angle);
        yRatio = dist * Math.sin(angle);
        if (side == 'r')
            dir.setX(xRatio);
        else
            dir.setX(-xRatio);
        dir.setY(-yRatio);
    }

    public void update(Graphics g) {
        if (isHit) {
            x += dir.getX() * KBSpeed;
            y += dir.getY() * KBSpeed;
        }

        drawX = x + c.getPosShiftX();
        drawY = y + c.getPosShiftY();

        // Only allows for 1 midair jump
        if (!onGround() && jumpCount == 2)
            jumpCount--;

        horizontalCollisions();
        applyGravity();
        verticalCollisions();

        drawSprite(g);
    }

    public void drawSprite(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(drawX, drawY, width, height);
        if (orientation == 'r')
            g.drawImage(image, drawX - xShiftR, drawY - yShift, imgWidth, imgHeight, null);
        else
            g.drawImage(image, drawX + 125 - xShiftL, drawY - yShift, -imgWidth, imgHeight, null);
    }
}