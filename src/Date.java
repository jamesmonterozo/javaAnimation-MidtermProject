import java.awt.*;
import java.util.*;

/**
 * This is a complex object that creates a date scene using other complex objects
 * namely the Person, Cat, and Table Set objects. The scene consists of two Persons
 * facing each other with one Cat beside each Person. In between the two is a Table Set.
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

public class Date extends DrawingObject implements DayNightObject {

    private ArrayList<DrawingObject> components;

    /**
     * This creates a new Date object with the parameters specified.
     * The complex object components of the Date object are generated as well.
     * @param x                 the x position of the Date
     * @param y                 the y position of the Date
     * @param size              the size of the Date
     * @param lpColors          the colors of the Person on the left
     * @param rpColors          the colors of the person on the Right
     * @param tsColors          the colors of the Table Set
     * @param lcColors          the colors of the Cat on the left
     * @param rcColors          the colors of the Cat on the right
     * @param darkness          how dark all objects can get
     * @param animationSpeed    the speed of the candle animation of the Table Set
     * @param seed              used to randomize the candle animation
     */
    public Date(double x, double y, double size, Color[] lpColors, Color[] rpColors, Color[] tsColors, Color[] lcColors, Color[] rcColors, double darkness, double animationSpeed, long seed) {

        // Creation of complex object components
        components = new ArrayList<DrawingObject>(
                Arrays.asList(
                    new Person(
                            x - size * 3 / 5, y, size,
                            lpColors[0], lpColors[1], lpColors[2], lpColors[3], lpColors[4],
                            darkness, false
                    ),
                    new Person(
                            x + size * 3 / 5, y, size,
                            rpColors[0], rpColors[1], rpColors[2], rpColors[3], rpColors[4],
                            darkness, true
                    ),
                    new TableSet(
                            x, y, size * 0.8,
                            tsColors[0], tsColors[1], tsColors[2], tsColors[3], tsColors[4],
                            darkness, animationSpeed, seed
                    ),
                    new Cat (x - size, y, size / 2, lcColors[0], lcColors[1], lcColors[2], false),
                    new Cat (x + size, y, size / 2, rcColors[0], rcColors[1], rcColors[2], true)
                )
        );
    }

    /**
     * This method draws the components generated.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {
        for (DrawingObject component : components)
            component.draw(g2d);
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        for (DrawingObject component : components)
            component.adjustX(distance);
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        for (DrawingObject component : components)
            ((DayNightObject) component).timeTransition(isDay, progress);
    }
}
