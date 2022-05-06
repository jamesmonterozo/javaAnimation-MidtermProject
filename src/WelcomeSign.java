import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

/**
 * This draws the "Welcome to France" sign using the text features of java.awt.
 * The colors of the text are based on the French flag colors to be provided
 * upon creating the object.
 * Credits to the following two sources for how the text was drawn along with the
 * borders:
 * https://examples.javacodegeeks.com/desktop-java/awt/draw-text-example/
 * https://stackoverflow.com/questions/51329346/any-better-way-to-draw-string-with-outline-in-java
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

public class WelcomeSign extends DrawingObject implements DayNightObject{

    private double x;
    private double y;

    private int welcomeFontSize;
    private int franceFontSize;

    private Font welcomeFont;
    private Font franceFont;

    private Color welcomeColor;
    private Color[] flagTriColor;
    private Color borderColor;
    private Color litBorderColor;
    private Color curBorderColor;

    /**
     * This creates a Welcome Sign object with the parameters specified.
     * @param x                 x value of the top left corner
     * @param y                 y value of the top left corner
     * @param size              size of the text
     * @param flagTriColor      colors for the text based on the French flag
     * @param welcomeColor      color of the welcome part of the text
     * @param borderColor       color of the borders at day
     * @param litBorderColor    color of the borders at night
     */
    public WelcomeSign (double x, double y, double size, Color[] flagTriColor, Color welcomeColor, Color borderColor, Color litBorderColor)
    {
        this.x = x;
        this.y = y;

        this.welcomeColor = welcomeColor;
        this.flagTriColor = flagTriColor;

        this.borderColor = borderColor;
        this.litBorderColor = litBorderColor;

        this.curBorderColor = borderColor;

        this.welcomeFontSize = (int) Math.round(size * 0.25);
        this.franceFontSize = (int) Math.round(size - welcomeFontSize);

        this.welcomeFont = new Font("Arial", Font.BOLD, welcomeFontSize);
        this.franceFont = new Font("Arial", Font.BOLD, franceFontSize);
    }

    /**
     * This method draws the welcome sign by first generating its parts.
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        /*
        The text drawing was based on code form these two sources:
        https://examples.javacodegeeks.com/desktop-java/awt/draw-text-example/
        https://stackoverflow.com/questions/51329346/any-better-way-to-draw-string-with-outline-in-java
         */
        AffineTransform ogAT = g2d.getTransform();

        // Generating the "WELCOME TO"
        g2d.translate(x, y);
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout welcome = new TextLayout("WELCOME TO", welcomeFont, frc);
        Shape shape = welcome.getOutline(null);
        // Drawing the border and fill of the "WELCOME TO"
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.setColor(curBorderColor);
        g2d.draw(shape);

        g2d.setColor(welcomeColor);
        g2d.fill(shape);

        // Generating the "FRANCE" part of the text
        double yTrans = g2d.getFontMetrics(franceFont).getAscent();
        g2d.translate(0, yTrans);
        String[] franceLetters = new String[] {"F", "R", "A", "N", "C", "E"};
        for (int i = 0; i < franceLetters.length; i++)
        {
            // Draws each letter with the corresponding border
            TextLayout letter = new TextLayout(franceLetters[i], franceFont, frc);
            Shape letterShape = letter.getOutline(null);
            g2d.setColor(curBorderColor);
            g2d.draw(letterShape);

            g2d.setColor(flagTriColor[i % 3]);
            g2d.fill(letterShape);
            g2d.translate(g2d.getFontMetrics(franceFont).stringWidth(franceLetters[i]), 0);
        }

        g2d.setTransform(ogAT);
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
        if (isDay)
        {
            if (progress >= 0.6)
                curBorderColor = litBorderColor;
        }
        else
        {
            if (progress >= 0.4)
                curBorderColor = borderColor;
        }
    }
}
