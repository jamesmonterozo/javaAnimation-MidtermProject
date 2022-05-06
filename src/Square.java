import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws a square using the Rectangle2D.Double.
 * It accepts x and y values that specify the coordinates of the upper left corner of
 * the shape along with the dimension for the side of the square and the
 * thickness of its border. The border color and fill color also need to be specified.
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

public class Square extends DrawingObject {
    private double x;
    private double y;
    private double size;
    private float borderThickness;
    private Color fillColor;
    private Color borderColor;

    /**
     * This creates a Circle object with the parameters specified.
     * @param x                 The x value of the top left corner of the square.
     * @param y                 The y value of the top left corner of the square.
     * @param size              The side length of the square.
     * @param borderThickness   The thickness of the border
     * @param fillColor         The color inside the square
     * @param borderColor       The color of the square's border
     */
    public Square(double x, double y, double size, float borderThickness, Color fillColor, Color borderColor) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.borderThickness = borderThickness;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    /**
     * This method draws the square using the Rectangle2D.double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // Used to draw the square
        Rectangle2D.Double square = new Rectangle2D.Double(x, y, size, size);

        // Draws the shape with the appropriate colors and border
        g2d.setColor(fillColor);
        g2d.fill(square);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.draw(square);
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
