import java.awt.*;

/**
 * This is an abstract class for all drawing objects. It requires drawing objects
 * to implement a public draw method and a public method to adjust the horizontal
 * location of the object. This also contains a shared method that allows objects
 * to transition their colors smoothly.
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

public abstract class DrawingObject {

    /** This abstract method is called when the frame is drawn / redrawn. **/
    public abstract void draw(Graphics2D g2d);
    /** This abstract method is called when the object should be moved horizontally. **/
    public abstract void adjustX(double distance);

    /**
     * This provides objects that implement this abstract class with a way of darkening/lightening
     *  their color smoothly.
     */
    protected Color curColor (boolean isDay, double progress, Color baseColor, double darkness)
    {

        Color curColor;
        // Determines the difference between the base shade and darker shade
        double baseR = baseColor.getRed() - baseColor.getRed() * darkness;
        double baseG = baseColor.getGreen() - baseColor.getGreen() * darkness;
        double baseB = baseColor.getBlue() - baseColor.getBlue() * darkness;

        // Adjusts the color to add or subtract the difference calculated.
        // The quantity added/subtracted is tied to how far the sun has gone down or up.
        if (isDay)
        {
            curColor = new Color(
                    (float) (baseColor.getRed() - baseR * progress) / 255,
                    (float) (baseColor.getGreen() - baseG * progress) / 255,
                    (float) (baseColor.getBlue() - baseB * progress) / 255,
                    (float) baseColor.getAlpha() / 255 // No transparency changes
            );
        }
        else
        {
            curColor = new Color(
                    (float) (baseColor.getRed() * darkness + baseR * progress) / 255,
                    (float) (baseColor.getGreen() * darkness + baseG * progress) / 255,
                    (float) (baseColor.getBlue() * darkness + baseB * progress) / 255,
                    (float) baseColor.getAlpha() / 255 // No transparency changes
            );
        }

        return curColor; // Returns the color that the object should take on
    }
}
