import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * This is a composite shape that draws grass using the Line shape.
 * A randomizer is used to randomize the generation of the grass.
 * This grass can also be transitioned between its day and night modes.
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

public class Grass extends DrawingObject implements DayNightObject {

    private double x;
    private double y;
    private double height;
    private float thickness;

    private Color grassColor;
    private Color curGrassColor;
    private double darkness;

    private long seed;

    private Random randomizer;

    /**
     * This creates a Grass object with the parameters specified.
     * @param x             x value of the base
     * @param y             y value of the base
     * @param height        length of each grass piece
     * @param thickness     thickness of the grass
     * @param grassColor    color of the grass
     * @param darkness      how dark the object can get
     * @param seed          used for the randomizer
     */
    public Grass (double x, double y, double height, float thickness, Color grassColor, double darkness, long seed)
    {
        this.x = x;
        this.y = y;

        this.height = height;
        this.thickness = thickness;

        this.grassColor = grassColor;
        this.curGrassColor = grassColor;
        this.darkness = darkness;

        this.seed = seed;
    }

    /**
     * This method draws the grass using Line Objects.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        randomizer = new Random(seed); // used to randomize the height

        AffineTransform ogAT = g2d.getTransform(); // stores the original transformation
        g2d.rotate(Math.toRadians(-106.875), x, y);

        for (int i = 0; i < 4; i++)
        {
            // The length of the line is randomized for each grass piece
            Line line = new Line(
                    x, y,
                    x + height + height * randomizer.nextDouble(0, 0.5),
                    y, thickness, curGrassColor
            );
            line.draw(g2d);
            g2d.rotate(Math.toRadians(11.25), x, y); // rotates to draw the next line
        }

        g2d.setTransform(ogAT); // restores the original transformation
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
        curGrassColor = curColor(isDay, progress, grassColor, darkness);
    }
}
