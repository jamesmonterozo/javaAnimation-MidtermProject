import java.awt.*;
import java.util.*;

/**
 * This is a system that generates the stars in the sky for the canvas.
 * The stars are randomly generated with randomized positions, and sizes
 * This also handles the stars transitioning to their day and night modes.
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

public class StarSystem extends DrawingObject implements DayNightObject {
    private double width;
    private double height;
    private double baseSize;
    private Color starColor;
    private double density; // 1 star every density square units
    private long seed;
    private double sizeChaos; // Lower values are usually more chaotic

    private Random randomizer;

    /**
     * This creates a new Star System object with the necessary parameters
     * @param width         the width of the sky
     * @param height        the height of the sky
     * @param baseSize      the base size of the stars
     * @param starColor     the color of the stars
     * @param density       the density of the stars
     * @param seed          used to randomize star generation
     * @param sizeChaos     determines how varied the sizes will be (a lower number means more variation)
     */
    public StarSystem(double width, double height, double baseSize, Color starColor, double density, long seed, double sizeChaos) {
        this.width = width;
        this.height = height;
        this.baseSize = baseSize;
        this.starColor = new Color(
                (float) starColor.getRed() / 255,
                (float) starColor.getGreen() / 255,
                (float) starColor.getBlue() / 255,
                0f
        ); // ensures that the star's color is transparent at the start
        this.density = density;
        this.sizeChaos = sizeChaos;
        this.seed = seed;
    }

    /**
     * This method draws the stars generated.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {

        for (Circle star : generateStars())
        {
            star.draw(g2d);
        }

    }

    /**
     * This method generates the clouds with their randomized attributes.
     * @return the clouds generated
     */
    private ArrayList<Circle> generateStars ()
    {
        randomizer = new Random(seed);

        ArrayList<Circle> stars = new ArrayList<Circle> ();
        for(double x = 0; x < width; x += density * randomizer.nextInt(0, 101) / 100)
        {
            for(double y = 0; y < height; y += density * randomizer.nextInt(0, 101) / 100) {
                double sizeAdj = baseSize + baseSize * randomizer.nextDouble() / sizeChaos;
                stars.add(
                        new Circle(x + density * randomizer.nextInt(-100, 101) / 100,
                                    y + density * randomizer.nextInt(-100, 101) / 100,
                                    sizeAdj, 0, starColor, starColor)
                );
            }

        }
        return stars;
    }

    /**
     * Although this was supposed to move the object left and right,
     * the stars being an object in the sky needs to stay stationary horizontally
     * @param distance  The horizontal distance the object should be moved by.
     */
    @Override
    public void adjustX(double distance) {
        // do nothing
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        if (!isDay)
            progress = 1 - progress;

        // This should be fully opaque at night and fully transparent at day
        starColor = new Color(
                (float) starColor.getRed() / 255,
                (float) starColor.getGreen() / 255,
                (float) starColor.getBlue() / 255,
                (float) progress
        );

    }
}
