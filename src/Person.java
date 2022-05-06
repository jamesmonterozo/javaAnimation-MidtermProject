import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * This is a composite shape that draws a person in a chair using the basic shapes.
 * This person can also be transitioned between its day and night modes, and
 * the person can face either left or right.
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

public class Person extends DrawingObject implements DayNightObject {

    private double x;
    private double y;

    private boolean isLeft;

    private double chairHeight;
    private double chairLegHeight;
    private double chairThickness;
    private double chairWidth;

    private double legLength;
    private double legWidth;
    private double torsoLength;
    private double torsoWidth;
    private double armLength;
    private double armWidth;
    private double faceSize;
    private float borderThickness;

    private Color faceColor;
    private Color shirtColor;
    private Color pantsColor;
    private Color chairColor;
    private Color borderColor;

    private Color curFaceColor;
    private Color curShirtColor;
    private Color curPantsColor;
    private Color curChairColor;

    private double darkness;

    /**
     * This creates a Person object with the parameters specified.
     * @param x             x value of the bottom left corner
     * @param y             y value of the bottom left corner
     * @param size          size of the person
     * @param faceColor     color of the face
     * @param shirtColor    color of the shirt
     * @param pantsColor    color of the pants
     * @param chairColor    color of the chair
     * @param borderColor   color of the borders
     * @param darkness      adjusts the lighting of the person at night
     * @param isLeft        the direction the person is facing
     */
    public Person (double x, double y, double size, Color faceColor, Color shirtColor, Color pantsColor, Color chairColor, Color borderColor, double darkness, boolean isLeft)
    {
        this.x = x;
        this.y = y;

        this.faceColor = faceColor;
        this.shirtColor = shirtColor;
        this.pantsColor = pantsColor;
        this.chairColor = chairColor;
        this.borderColor = borderColor;

        this.darkness = darkness;

        this.isLeft = isLeft;

        this.curFaceColor = this.faceColor;
        this.curShirtColor = this.shirtColor;
        this.curPantsColor = this.pantsColor;
        this.curChairColor = this.chairColor;

        // determines the proportions of the person
        this.borderThickness = (float) (size * 1 / 60);

        this.chairHeight = size * 4 / 5;

        this.chairThickness = size * 1 / 20;
        this.chairWidth = size * 2 / 5;
        this.chairLegHeight = this.chairWidth * 0.9;

        this.torsoLength = size * 3 / 5;
        this.torsoWidth = size * 0.75 / 5;

        this.legWidth = size * 1 / 10;
        this.legLength = this.chairWidth + this.chairThickness;

        this.armWidth = size * 1 / 14;
        this.armLength = size * 3 / 10;

        this.faceSize = size * 1 / 4;
    }

    /**
     * This method draws the person by first generating its parts.
     * @param g2d   The Graphics2D object that will be used to draw the object.
     */
    @Override
    public void draw(Graphics2D g2d) {

        // used to adjust values if the person is facing left or right
        int mult = isLeft ? -1 : 1;
        double armX = x + mult * (chairThickness + torsoWidth / 4);
        // arm of the person
        Parallelogram arm =
                new Parallelogram(armX, y - chairLegHeight - torsoLength * 0.75,
                        armLength, mult * armWidth, 0, borderThickness, curShirtColor, borderColor);

        // the person's chair
        ArrayList<DrawingObject> chair = new ArrayList<DrawingObject>(
                Arrays.asList(
                        new Parallelogram(x, y - chairHeight, chairHeight, mult * chairThickness, 0, borderThickness, curChairColor, borderColor),
                        new Parallelogram(x, y - chairLegHeight, chairThickness, mult * chairWidth, 0, borderThickness, curChairColor, borderColor),
                        new Parallelogram(x + mult * (chairWidth - chairThickness), y - chairLegHeight, chairLegHeight, mult * chairThickness, 0, borderThickness, curChairColor, borderColor)

                )
        );

        // the person themself
        ArrayList<DrawingObject> person = new ArrayList<DrawingObject>(
                Arrays.asList(
                        new Parallelogram(x + mult * chairThickness, y - chairLegHeight - torsoLength, torsoLength, mult * torsoWidth, 0, borderThickness, curShirtColor, borderColor),
                        new Parallelogram(x + mult * chairThickness, y - chairLegHeight - legWidth, legWidth, mult * legLength, 0, borderThickness, curPantsColor, borderColor),
                        new Parallelogram(x + mult * (legLength + chairThickness - legWidth), y - chairLegHeight - legWidth, legLength, mult * legWidth, 0, borderThickness, curPantsColor, borderColor),
                        new Circle (x + mult * (chairThickness + torsoWidth / 2) - faceSize/2, y - chairLegHeight - torsoLength - faceSize * 0.7,
                                faceSize, borderThickness, curFaceColor, borderColor)
                )
        );

        // draws the person, their chair and their arm
        for (DrawingObject object : chair)
            object.draw(g2d);
        for (DrawingObject object : person)
            object.draw(g2d);

        AffineTransform ogAT = g2d.getTransform();
        g2d.rotate(mult * -45, armX, y - chairLegHeight - torsoLength * 0.75);
        arm.draw(g2d);
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
        curChairColor = curColor(isDay, progress, chairColor, darkness);
        curShirtColor = curColor(isDay, progress, shirtColor, darkness * 3);
        curPantsColor = curColor(isDay, progress, pantsColor, darkness * 3);
        curFaceColor = curColor(isDay, progress, faceColor, darkness * 3);
    }
}
