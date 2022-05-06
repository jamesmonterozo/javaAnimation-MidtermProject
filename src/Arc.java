import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws an arc with a curved bottom and a rectangle-like top portion.
 * This is drawn through a Path2D.Double with a curve for the bottom portion and lines to fill in
 * the rest. It accepts x and y values that specify the coordinates of the upper left corner of
 * the shape along with the dimensions for the arc's base, height and
 * the thickness of its border. The border color and fill color also need to be specified.
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

public class Arc extends DrawingObject {
    private double x;
    private double y;
    private double height;
    private double base;
    private float borderThickness;
    private Color fillColor;
    private Color borderColor;

    /**
     * This creates a new arc asking for the required fields.
     * @param x                 The x value of the upper left corner of the arc.
     * @param y                 The y value of the upper left corner of the arc.
     * @param height            The height of the arc
     * @param base              The length of the base of the arc
     * @param borderThickness   The thickness of the border
     * @param fillColor         The color inside the shape
     * @param borderColor       The color of the shape's border
     */
    public Arc(double x, double y, double height, double base, float borderThickness, Color fillColor, Color borderColor) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.base = base;
        this.borderThickness = borderThickness;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    /**
     * This method draws the arc using the Path2D.Double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // Used to draw the arc
        Path2D.Double arc = new Path2D.Double();

        // Begins with the curved bottom
        arc.moveTo(x, y + height);
        arc.curveTo(x + base / 3, y, x + base - base / 3, y, x + base, y + height);

        // Creates the straight lines to complete the rectangular part of the shape
        arc.lineTo(x + base, y);
        arc.lineTo(x, y);
        arc.closePath();

        // Draws the arc with the appropriate colors and border.
        g2d.setColor(fillColor);
        g2d.fill(arc);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.setColor(borderColor);
        g2d.draw(arc);
    }

    /**
     * This moves the object horizontally given a specified distance.
     * @param distance  The horizontal distance the object will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x += distance;
    }

}
