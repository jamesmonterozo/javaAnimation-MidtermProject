import java.awt.*;
import java.util.*;

/**
 * This is a composite shape that draws a table using parallelograms.
 * The table can transition between its day and night modes.
 * The table also moves on its own with the candlelight getting
 * larger and smaller.
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

public class TableSet extends DrawingObject implements DayNightObject{

    private double x;
    private double y;
    private double size;

    private double tableThickness;
    private double tableHeight;
    private double candleHeight;
    private double candleThickness;
    private double wickHeight;
    private double lightSize;
    private double curLightSize;
    private float borderThickness;

    private Color legColor;
    private Color coverColor;
    private Color lightColor;
    private Color candleColor;
    private Color borderColor;

    private Color curLegColor;
    private Color curCoverColor;
    private double darkness;
    private double animationSpeed;
    private Random randomizer;

    /**
     * This creates a TableSet object with the specified parameters.
     * @param x                 the x value at the base
     * @param y                 the y value at the base
     * @param size              the size of the set
     * @param legColor          the color of the table legs
     * @param coverColor        the table cover color
     * @param lightColor        the color of the candle's light
     * @param candleColor       the color of the candle itself
     * @param borderColor       the color of the borders
     * @param darkness          how dark the table set can get
     * @param animationSpeed    the speed of the light animation
     * @param seed              used to randomize the light animation
     */
    public TableSet (double x, double y, double size, Color legColor, Color coverColor, Color lightColor, Color candleColor, Color borderColor, double darkness, double animationSpeed, long seed)
    {
        this.size = size; // 36
        this.x = x;
        this.y = y;
        this.tableThickness = size * 2 / 9;
        this.tableHeight = size * 0.8;
        this.candleHeight = size / 3;
        this.candleThickness = size * 0.1;
        this.wickHeight = this.candleHeight / 4;
        this.lightSize = size * 0.75;
        this.borderThickness = (float) (size * 0.01);

        this.legColor = legColor;
        this.coverColor = coverColor;
        this.lightColor = lightColor;
        this.borderColor = borderColor;
        this.candleColor = candleColor;

        this.curLegColor = legColor;
        this.curCoverColor = this.coverColor;

        this.darkness = darkness;
        this.randomizer = new Random(seed);
        this.animationSpeed = animationSpeed;
        this.curLightSize = this.lightSize;
    }

    /**
     * This method draws the table set and adjusts the light size automatically
     * @param g2d   The Graphics2D object that will be used to draw the object.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // If the light size reaches its minimum or maximum size, it should begin growing or shrinking instead
        if (curLightSize > lightSize + lightSize * 0.1 || curLightSize < lightSize - lightSize * 0.1)
            animationSpeed = -animationSpeed;
        curLightSize = curLightSize + animationSpeed * randomizer.nextDouble(0.7, 1);
        ArrayList<DrawingObject> tableSet = new ArrayList<DrawingObject>(
                Arrays.asList(
                        // Table Legs
                        new Parallelogram(x - size / 2, y - tableHeight, tableHeight, tableThickness, 0, borderThickness, curLegColor, borderColor),
                        new Parallelogram(x + size / 2 - tableThickness, y - tableHeight, tableHeight, tableThickness, 0, borderThickness, curLegColor, borderColor),
                        // Table cover
                        new Parallelogram(x - size / 2, y - tableHeight, tableThickness, size, 0, borderThickness, curCoverColor, borderColor),

                        // Creates the candle
                        new Parallelogram(x - candleThickness / 2, y - tableHeight - candleHeight, candleHeight, candleThickness, 0, borderThickness, candleColor, borderColor),
                        new Line(x, y - tableHeight - candleHeight, x, y - tableHeight - candleHeight - wickHeight, borderThickness * 2, borderColor),
                        new Circle(x - curLightSize / 2, y - tableHeight - candleHeight - wickHeight / 2 - curLightSize / 2, curLightSize, 0, lightColor, lightColor)
                )
        );

        // draws the object
        for (DrawingObject part : tableSet)
            part.draw(g2d);
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
        curCoverColor = curColor(isDay, progress, coverColor, darkness);
        curLegColor = curColor(isDay, progress, legColor, darkness);
    }
}
