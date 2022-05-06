import java.awt.*;
import java.util.*;

/**
 * This is a system that generates the cats for the canvas.
 * The cats are generated with varying colors (from a predetermined list),
 * sizes, and cooldowns. This system handles animating the cats,
 * adjusting their horizontal placement when the canvas is shifted,
 * and ensuring all cats update to their day/night mode.
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

public class CatSystem extends DrawingObject implements DayNightObject {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private int count;
    private double baseSize;

    private Color[] catColors;
    private Color borderColor;

    private int baseCooldown;
    private long seed;
    private double speed;

    private Random randomizer;

    private ArrayList<Cat> cats;

    /**
     * This creates a Cat System object with the parameters specified.
     * The cats are also generated here.
     * @param minX          minimum x that the cats can go to
     * @param maxX          maximum x that the cats can go to
     * @param minY          minimum y that the cats can go to
     * @param maxY          maximum y that the cats can go to
     * @param baseSize      base size of the cat
     * @param count         the number of cats to generate
     * @param catColors     the fur colors to choose from
     * @param borderColor   the cats' border and eye color
     * @param baseCooldown  the base cooldown of the cats' change in animation state
     * @param speed         the speed of the cats in animation
     * @param seed          used to randomize cat generation
     */
    public CatSystem(double minX, double maxX, double minY, double maxY, double baseSize, int count, Color[] catColors, Color borderColor, int baseCooldown, double speed, long seed) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.baseSize = baseSize;
        this.catColors = catColors;
        this.borderColor = borderColor;
        this.baseCooldown = baseCooldown;
        this.seed = seed;
        this.count = count;
        this.speed = speed;

        cats = generateCats(); // Generates the cats
    }

    /**
     * This method draws the cats generated and ensures that the cats are sorted according to their y values.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {
        /*
        Sort cats by Y value before drawing, draw lowest Y value first
        References used:
        https://stackoverflow.com/questions/13434143/sorting-a-double-value-of-an-object-within-an-arraylist
        https://mkyong.com/java/java-object-sorting-example-comparable-and-comparator/#:~:text=To%20sort%20an%20Object%20by,the%20new%20Fruit%20class%20again.&text=The%20new%20Fruit%20class%20implemented,quantity%20property%20in%20ascending%20order.
         */
        Collections.sort(cats, new Comparator<Cat>() {
            @Override
            public int compare(Cat o1, Cat o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        });

        // Animates and draws each cat
        for (Cat cat : cats)
        {
            cat.animate(speed);
            cat.draw(g2d);
        }
    }

    /**
     * This method generates the cats  with their randomized attributes.
     * @return the cats generated
     */
    private ArrayList<Cat> generateCats ()
    {
        randomizer = new Random(seed);
        ArrayList<Cat> cats = new ArrayList<Cat>();

        // Creates
        for (double x = minX; x < maxX; x += (maxX - minX) / (count - 1))
        {
            cats.add(
              new Cat (
                      x, randomizer.nextDouble(minY, maxY),
                      minX, maxX, minY, maxY,
                      baseSize * randomizer.nextDouble(0.5, 1.5),
                      catColors[randomizer.nextInt(0, catColors.length)],
                      borderColor, borderColor,
                      (int) Math.round(baseCooldown * randomizer.nextDouble(0.7, 1.3)),
                      seed + (long) x
              )
            );
        }

        return cats;
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        for (Cat cat : cats)
        {
            cat.adjustX(distance);
        }
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        for (Cat cat : cats)
        {
            cat.timeTransition(isDay, progress);
        }
    }

}
