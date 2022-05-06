import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws a line Line2D.double class.
 * It accepts x and y values that specify the start and end of the line
 * along with the length and thickness of the line.
 * The line color also needs to be specified.
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

public class Line extends DrawingObject {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private float thickness;
    private Color fillColor;

    /**
     * This creates a line object with the specified parameters.
     * @param x1            The x value of the starting point
     * @param y1            The y value of the starting point
     * @param x2            The x value of the ending point
     * @param y2            The y value of the ending point
     * @param thickness     The thickness of the line
     * @param fillColor     The color of the line
     */
    public Line(double x1, double y1, double x2, double y2, float thickness, Color fillColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.thickness = thickness;
        this.fillColor = fillColor;
    }

    /**
     * This method draws the line using the Line2D.double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // Used to draw the line
        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);

        // Draws the line with the appropriate thickness and color
        g2d.setColor(fillColor);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(line);
    }

    /**
     * This moves the object HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the object will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x1 += distance;
        x2 += distance;
    }

}
