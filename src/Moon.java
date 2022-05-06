import java.awt.*;
import java.awt.geom.*;

/**
 * This is a basic shape that draws a moon shape using the Path2D.Double.
 * It accepts x and y values that specify the coordinates of the center of
 * the shape along with the size of the moon.
 * The fill color also needs to be specified.
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

public class Moon extends DrawingObject{
    private double x;
    private double y;
    private double size;
    private Color color;

    // Values for the different corners of the moon
    private double x0, y0;
    private double x1, y1;
    private double x2, y2;
    private double x3, y3;

    /**
     * Creates a new moon using the required parameters
     * @param x         The x value of the center
     * @param y         The y value of the center
     * @param size      The size of the moon
     * @param color     The fill color of the moon
     */
    public Moon(double x, double y, double size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;

    }

    /**
     * This method draws the moon using the Path2D.Double.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        Path2D.Double crescent = new Path2D.Double();

        // The top tip of the moon
        x0 = x;
        y0 = y - size * 0.7;

        // The leftmost part of the moon
        x1 = x - size * 0.9;
        y1 = y;

        // The bottom tip of the moon
        x2 = x0;
        y2 = y + size * 0.7;

        // The part of the moon closest to its center
        x3 = x - size * 0.6;
        y3 = y;

        // Curves to each part of the moon until it reaches the start
        crescent.moveTo(x0, y0);
        crescent.curveTo(
            x0 - size / 2, y0,
            x1, y1 - size / 2,
                x1, y1
        );

        crescent.curveTo(
                x1, y1 + size/2,
                x2 - size / 2, y2,
                x2, y2
        );

        crescent.curveTo(
                x2 - size / 4, y2,
                x3, y3 + size / 2,
                x3, y3
        );

        crescent.curveTo(
                x3, y3 - size / 2,
                x0 - size / 4, y0,
                x0, y0
        );

        // Draws the moon with the right fill
        crescent.closePath();
        g2d.setColor(color);
        g2d.fill(crescent);
    }

    /**
     * Although this was supposed to move the object left and right,
     * the moon being an object in the sky needs to stay stationary horizontally
     * @param distance  The horizontal distance the object should be moved by.
     */
    @Override
    public void adjustX(double distance) {
        // Do Nothing, sky is stationary
    }

    /**
     * This moves the object vertically given a specified distance.
     * @param distance  The vertical distance the object will be moved by.
     */
    public void adjustY(double distance) {
        y += distance;
    }

    /**
     * This obtains the bounding box of the moon (where the moon is clickable)
     * @return  the x and y coordinates of the moon's bounding box.
     */
    public double[] getBoundingBox () {
        double[] values = new double[]{
                x1 - size * 0.4, y0 - size * 0.4,
                x2 + size * 0.4, y2 + size * 0.4
        };

        return values;
    }

    /**
     * This obtains the y value of the moon.
     * @return  the y value of the moon
     */
    public double getY() {
        return y;
    }
}
