import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws a French flag using the Path2D.double class.
 * This flag can also be animated and transitioned between its day and
 * night modes.
 *
 * @author Clarence Monterozo (---)
 * @version April 03, 2022
 */

/*
	I have not discussed the Java language code in my program
	with anyone other than my instructor or the teaching assistants
	assigned to this course.

	I have not used Java language code obtained from another student,
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program
	was obtained from another source, such as a textbook or website,
	that has been clearly noted with a proper citation in the comments
	of my program.
*/

public class FrenchFlag extends DrawingObject implements DayNightObject {

    private double x;
    private double y;
    private double size;

    private double maxWave;
    private int waveDir;

    private double colorLength;
    private double height;

    private double x1;
    private double x2;
    private double x3;
    private double y1;
    private double y2;
    private double y3;

    private Color curBlue;
    private Color curWhite;
    private Color curRed;

    private Color baseBlue;
    private Color baseWhite;
    private Color baseRed;

    private Color borderColor;
    private float borderThickness;
    private double darkness;

    private double bezHandleLength;

    /**
     * This creates a French Flag object with the parameters specified.
     * @param x             the x value of top left corner
     * @param y             the y value of the top left corner
     * @param size          the size of the flag
     * @param darkness      how dark the flag can get
     * @param flagTriColor  the three colors of the flag
     */
    public FrenchFlag (double x, double y, double size, double darkness, Color[] flagTriColor)
    {
        this.x = x;
        this.y = y;

        this.size = size;
        this.height = size * 2 / 3;
        this.colorLength = size / 3;

        // x and y values of the edges of the flag
        this.x1 = x + colorLength;
        this.x2 = x + 2 * colorLength;
        this.x3 = x + 3 * colorLength;
        this.y1 = y;
        this.y2 = y;
        this.y3 = y;

        // how "curvy" it should be
        this.bezHandleLength = size / 6;

        // maximum wave of the flag and the direction
        this.maxWave = height / 6;
        this.waveDir = 1;

        // Flag Colors
        this.baseBlue = flagTriColor[0];
        this.baseWhite = flagTriColor[1];
        this.baseRed = flagTriColor[2];
        this.borderColor = Color.BLACK;

        // Track day/night colors
        this.curBlue = baseBlue;
        this.curWhite = baseWhite;
        this.curRed = baseRed;
        this.darkness = darkness;

        this.borderThickness = (float) (size / 30);

    }

    /**
     * This method draws the flag using the Path2D.double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {


        // BLUE
        Path2D.Double blue =
                new Path2D.Double();
        blue.moveTo(x, y);
        blue.curveTo(x, y, x1 - bezHandleLength, y1, x1, y1);
        blue.lineTo(x1, y1 + height);
        blue.curveTo(x1 - bezHandleLength, y1 + height, x, y + height, x, y + height);
        blue.closePath();

        // WHITE
        Path2D.Double white =
                new Path2D.Double();
        white.moveTo(x1, y1);
        white.curveTo(x1 + bezHandleLength, y1, x2 - bezHandleLength, y2, x2, y2);
        white.lineTo(x2, y2 + height);
        white.curveTo(x2 - bezHandleLength, y2 + height, x1 + bezHandleLength, y1 + height, x1, y1 + height);
        white.closePath();

        // RED
        Path2D.Double red =
                new Path2D.Double();
        red.moveTo(x2, y2);
        red.curveTo(x2 + bezHandleLength, y2, x3, y3, x3, y3);
        red.lineTo(x3, y3 + height);
        red.curveTo(x3, y3 + height, x2 + bezHandleLength, y2 + height, x2, y2 + height);
        red.closePath();

        // Draw Outlines
        g2d.setColor(borderColor);
        Stroke s = g2d.getStroke();
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.draw(blue);
        g2d.draw(white);
        g2d.draw(red);
        g2d.setStroke(s);

        // Fill Shapes
        g2d.setColor(curBlue);
        g2d.fill(blue);
        g2d.setColor(curWhite);
        g2d.fill(white);
        g2d.setColor(curRed);
        g2d.fill(red);

    }

    /**
     * This animates the flag waving by adjusting the y values
     * @param animationSpeed
     */
    public void animate(double animationSpeed)
    {
        if (y1 >= y + maxWave)
            waveDir = -1;
        else if (y1 <= y - maxWave)
            waveDir = 1;
        y1 += waveDir * animationSpeed;
        y2 += -waveDir * animationSpeed;
        y3 = y1;
    }

    /**
     * This moves the object HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the object will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x += distance;
        x1 += distance;
        x2 += distance;
        x3 += distance;
    }

    /**
     * Transitions the object to its night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        curBlue = curColor(isDay, progress, baseBlue, darkness);
        curWhite = curColor(isDay, progress, baseWhite, darkness);
        curRed = curColor(isDay, progress, baseRed, darkness);
    }

}
