import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws a parallelogram using the Path2D.double class.
 * It accepts x and y values that specify the upper left corner of the shape
 * along with the dimension for the parallelogram's base, height, and the thickness of its border.
 * The offset of the top base from the upper left corner needs to be specified as well,
 * and an offset of 0 will generate a rectangle shape.
 * The border color and fill color also need to be specified.
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

public class Parallelogram extends DrawingObject {
    private double x;
    private double y;
    private double height;
    private double base;
    private double adjustment;
    private float borderThickness;
    private Color fillColor;
    private Color borderColor;

    /**
     * This creates a Parallelogram object with the parameters specified.
     * @param x                 the x value of the top left corner
     * @param y                 the y value of the top left corner
     * @param height            the height of the shape
     * @param base              the base length of the shape
     * @param adjustment        how much the top portion should be offset from the top left corner
     * @param borderThickness   the border thickness of the shape
     * @param fillColor         the fill color of the shape
     * @param borderColor       the border color of the shape
     */
    public Parallelogram(double x, double y, double height, double base, double adjustment, float borderThickness, Color fillColor, Color borderColor) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.base = base;
        this.adjustment = adjustment;
        this.borderThickness = borderThickness;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    /**
     * This method draws the triangle using the Path2D.double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // Used to draw the parallelogram
        Path2D.Double parallelogram = new Path2D.Double();

        // begins with the bottom base
        parallelogram.moveTo(x, y + height);
        // generates the bottom base
        parallelogram.lineTo(x + base, y + height);
        // generates the top base
        parallelogram.lineTo(x + base + adjustment, y);
        parallelogram.lineTo(x + adjustment, y);
        parallelogram.closePath();

        // draws the shape
        g2d.setColor(fillColor);
        g2d.fill(parallelogram);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.setColor(borderColor);
        g2d.draw(parallelogram);
    }

    /**
     * This moves the object HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the object will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x += distance;
    }

}
