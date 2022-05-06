import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * This is a composite shape that draws the cloud using Circles.
 * This cloud can also be transitioned between its day and night modes, and
 * the generation of the cloud could be randomized.
 * The cloud can also be moved from left to right.
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

public class Cloud extends DrawingObject implements DayNightObject {
    private double x;
    private double y;
    private double baseSize;

    private Color cloudColor;
    private Color curCloudColor;
    private double darkness;

    private long seed;
    private double cloudSpeed;

    private Random randomizer;

    /**
     * This creates a Cloud object with the parameters specified.
     * @param x             x value of the center
     * @param y             y value of the center
     * @param baseSize      size of each circle in the cloud
     * @param cloudColor    color of the cloud
     * @param darkness      how dark the cloud can get
     * @param seed          used to randomize the cloud creation
     * @param cloudSpeed    how fast the cloud can get
     */
    public Cloud(double x, double y, double baseSize, Color cloudColor, double darkness, long seed, double cloudSpeed) {
        this.x = x;
        this.y = y;
        this.baseSize = baseSize;

        this.cloudColor = cloudColor;
        this.curCloudColor = cloudColor;

        this.seed = seed;
        this.darkness = darkness;

        this.cloudSpeed = cloudSpeed;
    }

    /**
     * This method draws the cloud using methods that generate the circles.
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        randomizer = new Random(seed);
        generateCloud(g2d); // Generated every draw since it uses angle adjustments

    }

    /**
     * This creates the cloud using circles drawn at angles with certain variations
     * @param g2d   The Graphics2D object that will be used to draw the object.
     */
    private void generateCloud(Graphics2D g2d)
    {
        AffineTransform ogAT = g2d.getTransform();

        // The centermost part of the cloud
        Circle base = new Circle(x - baseSize / 2, y - baseSize / 2, baseSize, 0, curCloudColor, curCloudColor);
        base.draw(g2d);

        // draws 4 circles around the centermost part of the cloud
        for (int i = 0; i <= 4; i ++)
        {
            int angle = (i == 0) ? 0 : -45;
            double addX = baseSize / 8;

            // more variation allowed for the base circles
            if (i == 0 || i == 4)
            {
                addX *= (2 + randomizer.nextDouble(-1, 1));
            }
            else
            {
                addX *= (-1 + randomizer.nextDouble(-1, 1));
            }

            g2d.rotate(Math.toRadians(angle), x, y);
            // Randomized size and x value for the circle
            Circle circle = new Circle (x + addX, y - baseSize / 2,
                    baseSize + baseSize * randomizer.nextDouble(-0.1, 0.1),
                    0, curCloudColor, curCloudColor);
            circle.draw(g2d);
        }
        g2d.setTransform(ogAT);
    }

    /**
     * Moves the cloud across the sky
     * @param x1        the x value of left most corner of the sky
     * @param x2        the x value of the right most corner of the sky
     * @param densityX  the density of the clouds in the sky
     */
    public void moveCloud(double x1, double x2, double densityX)
    {
        Random r = new Random(seed);
        if (x >= x2 + (baseSize * 1.5))
        {
            // returns the cloud the leftmost part of the sky
            x = x1 - densityX * r.nextDouble(0, 1);
        }
        else
        {
            // moves the cloud left
            adjustX(cloudSpeed * r.nextDouble(0, 1.5));
        }
    }

    /**
     * This moves the object HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the object will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x += distance;
    }

    /**
     * Transitions the object to its night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        curCloudColor = curColor(isDay, progress, cloudColor, darkness);
    }

}
