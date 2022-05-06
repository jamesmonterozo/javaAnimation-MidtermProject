import java.awt.*;
import java.util.*;

/**
 * This is a system that generates the clouds in the sky for the canvas.
 * The clouds are randomly generated with positions, speeds, and sizes
 * This also handles the clouds transitioning to their day and night modes.
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

public class CloudSystem extends DrawingObject implements DayNightObject {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double baseSize;
    private Color baseCloudColor;
    private double densityX; // 1 cloud every (density) units
    private double densityY; // 1 cloud every (density) units
    private long seed;
    private double sizeChaos;
    private double darkness;
    private double baseCloudSpeed;

    private Random randomizer;
    private ArrayList<Cloud> clouds;

    /**
     * This creates a CloudSystem object with the parameters specified.
     * The clouds are also generated after.
     * @param x1                the leftmost possible x value of clouds
     * @param x2                the rightmost possible x value of clouds
     * @param y1                the topmost possible y value of clouds
     * @param y2                the bottommost possible y value of clouds
     * @param baseSize          the base size of the clouds
     * @param baseCloudColor    the base color of the clouds
     * @param darkness          how dark the clouds can get
     * @param densityX          the horizontal density of the clouds (1 cloud every [density] units)
     * @param densityY          the vertical density of the clouds (1 cloud every [density] units)
     * @param seed              used to randomize cloud generation
     * @param sizeChaos         how varied the sizes should be (a smaller value means more variation)
     * @param baseCloudSpeed    the base speed of the clouds
     */
    public CloudSystem(double x1, double x2, double y1, double y2, double baseSize, Color baseCloudColor, double darkness, double densityX, double densityY, long seed, double sizeChaos, double baseCloudSpeed) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.baseSize = baseSize;
        this.densityX = densityX;
        this.densityY = densityY;
        this.seed = seed;
        this.baseCloudSpeed = baseCloudSpeed;
        this.darkness = darkness;
        this.sizeChaos = sizeChaos;

        this.baseCloudColor = baseCloudColor;

        clouds = generateClouds(); // Generation of clouds
    }

    /**
     * This method draws and moves the clouds generated.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {

        for (Cloud cloud : clouds)
        {
            cloud.moveCloud(x1, x2, densityX);
            cloud.draw(g2d);
        }

    }

    /**
     * This method generates the clouds with their randomized attributes.
     * @return the clouds generated
     */
    private ArrayList<Cloud> generateClouds ()
    {
        randomizer = new Random(seed);

        ArrayList<Cloud> cloudsToGen = new ArrayList<Cloud> ();

        // Generates clouds following the density parameters
        for(double x = x1; x < x2 * 2; x += densityX * randomizer.nextInt(0, 101) / 100)
        {
            for(double y = y1; y < y2; y += densityY * randomizer.nextInt(0, 101) / 100) {
                // varies the sizes of the clouds
                double sizeAdj = baseSize + baseSize * randomizer.nextDouble() / sizeChaos;
               // varies the transparency of the clouds
                baseCloudColor = new Color (
                        (float) baseCloudColor.getRed() / 255,
                        (float) baseCloudColor.getGreen() / 255,
                        (float) baseCloudColor.getBlue() / 255,
                        (float) randomizer.nextDouble(0.75, 1.0)
                );
                // varies the x value of the clouds
                double curX = x + densityX * randomizer.nextDouble(-0.5, 0.5);
                if (!(curX >= x2)) // ensures clouds aren't generated beyond the rightmost limit
                {
                    // generates cloud
                    cloudsToGen.add(
                            new Cloud(curX,
                                    y + densityY * randomizer.nextDouble(-0.5, 0.5),
                                    sizeAdj, baseCloudColor, darkness, seed, baseCloudSpeed * randomizer.nextDouble(0.75, 1.25))
                    );
                }

            }

        }
        return cloudsToGen;
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x1 += distance;
        x2 += distance;
        for (Cloud cloud : clouds)
        {
            cloud.adjustX(distance);
        }
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {

        for (Cloud cloud : clouds)
            cloud.timeTransition(isDay, progress);

    }
}
