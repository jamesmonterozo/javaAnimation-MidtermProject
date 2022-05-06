import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws a trapezoid using the Path2D.double class.
 * It accepts x and y values that specify the upper left corner of the shape
 * along with the dimension for the shape's base, height, border thickness,
 * and how much the top portion of the shape should be offset inwards.
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

public class Trapezoid extends DrawingObject {
    private double x;
    private double y;
    private double height;
    private double base;
    private double adjustment;
    private float borderThickness;
    private Color fillColor;
    private Color borderColor;

    /**
     * This creates a Trapezoid object with the parameters specified.
     * @param x                 the x value of the top left corner
     * @param y                 the y value of the top left corner
     * @param height            the height of the shape
     * @param base              the base length of the shape
     * @param adjustment        how much the top portion should be offset inwards
     * @param borderThickness   the border thickness of the shape
     * @param fillColor         the fill color of the shape
     * @param borderColor       the border color of the shape
     */
    public Trapezoid(double x, double y, double height, double base, double adjustment, float borderThickness, Color fillColor, Color borderColor) {
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
     * This method draws the circle using the Path2D.double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // Used to draw the trapezoid
        Path2D.Double trapezoid = new Path2D.Double();

        // starts at the bottom left corner
        trapezoid.moveTo(x, y + height);
        // generates the bottom base
        trapezoid.lineTo(x + base, y + height);

        // generates the top base
        trapezoid.lineTo(x + base - adjustment, y);
        trapezoid.lineTo(x + adjustment, y);
        trapezoid.closePath();

        // Draws the shape
        g2d.setColor(fillColor);
        g2d.fill(trapezoid);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.setColor(borderColor);
        g2d.draw(trapezoid);
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
