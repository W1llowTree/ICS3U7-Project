/*
* David Zhai
* 1/18/2023
* For ICS3U7 Ms.Strelkovska
* Class used for slashing hitboxes
 */

import java.awt.*;
import java.util.*;

public class Hitbox {
    private int x, y;
    private int drawX, drawY;
    private int xShift, yShift, radius, baseKB, scalingKB, angle, damage;
    private int[] data;

    public Hitbox(int[] data) {
        this.data = data;
        xShift = data[0];
        yShift = data[1];
        radius = data[2];
    }

    // Circle Collision with rectangles. This involves finding the closest point on
    // the rectangle to the closest point on the circle
    // Then, it checks to see if the distance is less than the circle radius
    // Source: https://yal.cc/rectangle-circle-intersection-test/comment-page-1/
    public boolean intersects(Rectangle r) {
        int deltaX = x - (int) Math.max(r.getX(), Math.min(x, r.getX() + r.getWidth()));
        int deltaY = y - (int) Math.max(r.getY(), Math.min(y, r.getY() + r.getHeight()));
        return (deltaX * deltaX + deltaY * deltaY) < (radius * radius);
    }

    // Gets Hitbox Data
    public int[] getData() {
        return data;
    }

    public void setX(int val, char c) {
        if (c == 'l')
            x = xShift + val;
        else
            x = -xShift + 45 + val;
    }

    public void setY(int val) {
        y = yShift + val;
    }

    // Updates hitboxes draw coordinates
    public void setDrawX(int val, char c) {
        if (c == 'l')
            drawX = xShift + val;
        else
            drawX = -xShift + 45 + val;
    }

    public void setDrawY(int val) {
        drawY = yShift + val;
    }

    // test function, for future development
    /*
     * public void draw(Graphics g) {
     * g.setColor(Color.cyan);
     * g.fillOval(drawX, drawY, radius, radius);
     * }
     */
}