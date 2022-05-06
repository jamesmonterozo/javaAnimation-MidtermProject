import java.awt.*;
import java.awt.geom.*;

/**
 * This is a composite shape that draws the Sun using the Circle and Triangle.
 * The x and y values specify the center of the sun.
 * The sun can move up and down but not left and right.
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

public class Sun extends DrawingObject {
    private double x;
    private double y;
    private double radius;
    private Color rayColor;
    private Color coronaColor;

    private double raySide;
    private double height;

    /**
     * Creates a new sun using the required parameters
     * @param x             x value of the center of the sun
     * @param y             y value of the center of the sun
     * @param radius        the sun's radius
     * @param rayColor      the color of its rays and its center
     * @param coronaColor   the color of the surface
     */
    public Sun(double x, double y, double radius, Color rayColor, Color coronaColor) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rayColor = rayColor;
        this.coronaColor = coronaColor;

        // dimensions of the sun's rays
        raySide = radius * 0.9;
        height = Math.sqrt( Math.pow(raySide, 2) - Math.pow(raySide/2, 2) );
    }

    /**
     * This method draws the sun.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform ogAT = g2d.getTransform();

        // Generates the inner and outer parts of the sun
        Circle outer =
                new Circle(x-radius, y-radius, radius*2, 0,
                            coronaColor, coronaColor);
        double innerRadius = radius * 2 / 3;
        Circle inner =
                new Circle(x-innerRadius, y-innerRadius, innerRadius*2, 0,
                        rayColor, rayColor);
        // Generates and draws the rays of the sun
        Triangle ray =
                new Triangle(x - raySide / 2, y - radius - height, raySide, height,
                            0, rayColor, rayColor);
        for (int i = 0; i < 360; i += 45)
        {
            g2d.rotate(Math.toRadians(i), x, y);
            ray.draw(g2d);
        }
        g2d.setTransform(ogAT);

        // draws the center of the sun
        outer.draw(g2d);
        inner.draw(g2d);

    }

    /**
     * Although this was supposed to move the object left and right,
     * the sun being an object in the sky needs to stay stationary horizontally
     * @param distance  The horizontal distance the object should be moved by.
     */
    @Override
    public void adjustX(double distance) {
        //Do nothing, the sky will be stationary
    }

    /**
     * This moves the object vertically given a specified distance.
     * @param distance  The vertical distance the object will be moved by.
     */
    public void adjustY(double distance) {
        y += distance;
    }

    /**
     * This obtains the bounding box of the sun (where the sun is clickable)
     * @return  the x and y coordinates of the sun's bounding box.
     */
    public double[] getBoundingBox () {
        double dist = (radius + height);
        double[] values = new double[]{
            x - dist * 1.4, y - dist * 1.4,
            x + dist * 1.4, y + dist * 1.4
        };

        return values;
    }

    /**
     * This obtains the y value of the sun.
     * @return  the y value of the sun
     */
    public double getY() {
        return y;
    }
}
