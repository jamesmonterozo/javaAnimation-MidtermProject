import java.awt.*;
import java.util.*;

/**
 * This is a composite shape that draws the Eiffel tower using the basic shapes.
 * This tower can also be transitioned between its day and night modes.
 * The x and y values indicate the topmost part of the tower.
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

/*
Proportions of the Eiffel Tower (https://datagenetics.com/blog/april22016/index.html ):
height = 325 = size
Base = 57.63 = size * (5763 / 32500)
Middle = 115.73 - Base = size * (581/3250)
Top = 276.13 - Base - Middle = size * (802 / 1625)
Tip = 325 - Base - Middle - Top = size * (4887 / 32500)

Base_Width = 124.90 = size * (1249/3250)
Base_Inner = 74.24 = size * (1856/8125)

Middle_Width = Base_Width
Middle_Inner = Base_Width / Base_Width * Base_Inner

Top_Width = Middle_Inner
Top_Inner = Top_Width / Middle_Width * Middle_Inner

Tip_Width = Top_Inner
 */
public class EiffelTower extends DrawingObject implements DayNightObject {
    private double x;
    private double y;
    private double size;
    private float borderThickness;
    private Color scaffoldColor;
    private Color baseColor;
    private Color borderColor;
    private Color lightColor;
    private Color curBorderColor;

    private double bHeight, bWidth, bInner;
    private double mHeight, mWidth, mInner;
    private double tHeight, tWidth, tInner;
    private double tipWidth, tipHeight;

    /**
     * This creates a Eiffel Tower object with the parameters specified.
     * The proportions of the tower as specified are followed.
     * @param x                 the x value of the top
     * @param y                 the y value of the top
     * @param size              the size of the tower
     * @param borderThickness   the thickness of the borders
     * @param scaffoldColor     the fill color of the tower
     * @param baseColor         the color of the bases at each level
     * @param borderColor       the daytime border color
     * @param lightColor        the nighttime light color
     */
    public EiffelTower(double x, double y, double size, float borderThickness, Color scaffoldColor, Color baseColor, Color borderColor, Color lightColor) {
        this.x = x - (size * (1249.0 / 3250.0) / 2);
        this.y = y;
        this.size = size;
        this.borderThickness = borderThickness;
        this.scaffoldColor = scaffoldColor;
        this.baseColor = baseColor;
        this.borderColor = borderColor;
        this.lightColor = lightColor;

        curBorderColor = borderColor;
        bHeight = size  * (5763.0 / 32500.0);
        bWidth = size * (1249.0 / 3250.0);
        bInner = size * (1856.0 / 8125.0);

        mHeight = size * (581.0 / 3250.0);
        mWidth = bInner;
        mInner = mWidth / bWidth * bInner;

        tHeight = size * (802.0 / 1625.0);
        tWidth = mInner;
        tInner = tWidth / mWidth * mInner;

        tipHeight = size * (4887.0 / 32500.0);
        tipWidth = tInner;
    }

    /**
     * This method draws the tower using methods that generate its parts.
     * @param g2d   The Graphics2D object that will be used to draw the object.
     */
    @Override
    public void draw(Graphics2D g2d) {
        ArrayList<DrawingObject> components = new ArrayList<DrawingObject>();
        components.addAll(Arrays.asList(drawBase()));
        components.addAll(Arrays.asList(drawMiddle()));
        components.addAll(Arrays.asList(drawTop()));
        components.addAll(Arrays.asList(drawTip()));
        for (DrawingObject drawing : components)
        {
            drawing.draw(g2d);
        }
    }

    /**
     * This generates the base of the tower
     * @return the list of objects that compose the tower base
     */
    private DrawingObject[] drawBase()
    {
        return new DrawingObject[]{
                new Arc(x + (bWidth - bInner) / 2.65, y + size - bHeight * (3.0 / 4),
                        bHeight / 2, bInner + (bWidth - bInner) / 4,
                        borderThickness, scaffoldColor, curBorderColor),
                new Parallelogram(x, y + size - bHeight,
                                    bHeight, (bWidth - bInner) / 2,
                            (bWidth - bInner) / 2, borderThickness,
                                    scaffoldColor, curBorderColor),
                new Parallelogram(x + bInner + ((bWidth - bInner) / 2), y + size - bHeight,
                                    bHeight, (bWidth - bInner) / 2,
                        -(bWidth - bInner) / 2, borderThickness,
                                    scaffoldColor, curBorderColor),
                new Parallelogram(x + (bWidth - bInner) / 2.65, y + size - bHeight,
                        bHeight / 4, bInner + (bWidth - bInner) / 4,
                        0, borderThickness,
                        baseColor, curBorderColor)
        };

    }

    /**
     * This generates the middle section of the tower
     * @return the list of objects that compose the middle of the tower
     */
    private DrawingObject[] drawMiddle()
    {
        double mX = x + (bWidth - bInner) / 2;
        double mY = y + size - bHeight - mHeight;
        return new DrawingObject[]{
                new Arc(mX + (mWidth - mInner) / 2.65, mY + mHeight * (1.0 / 4),
                        mHeight / 2, mInner + (mWidth - mInner) / 4,
                        borderThickness, scaffoldColor, curBorderColor),
                new Parallelogram(mX, mY,
                        mHeight, (mWidth - mInner) / 2,
                        (mWidth - mInner) / 2, borderThickness,
                        scaffoldColor, curBorderColor),
                new Parallelogram(mX + mInner + ((mWidth - mInner) / 2), mY,
                        mHeight, (mWidth - mInner) / 2,
                        -(mWidth - mInner) / 2, borderThickness,
                        scaffoldColor, curBorderColor),
                new Parallelogram(mX + (mWidth - mInner) / 2.65, mY,
                        mHeight / 4, mInner + (mWidth - mInner) / 4,
                        0, borderThickness,
                        baseColor, curBorderColor)
        };
    }

    /**
     * This generates the top section of the tower
     * @return the list of objects that compose the top of the tower
     */
    private DrawingObject[] drawTop()
    {
        double tX = x + ((bWidth - bInner) / 2) + ((mWidth - mInner) / 2);
        double tY = y + size - bHeight - mHeight - tHeight;
        return new DrawingObject[]{
            // Lower Trapezoid
            new Trapezoid(tX, tY + tHeight * 2 / 3, tHeight / 3, tWidth,
                    tipWidth / 4, borderThickness, scaffoldColor, curBorderColor),
            // Upper Trapezoid
            new Trapezoid(tX + tipWidth / 4, tY, tHeight * 2 / 3, tWidth - tipWidth / 2,
                    tipWidth / 4, borderThickness, scaffoldColor, curBorderColor),
            new Line(tX + tWidth / 2, tY,
                tX + tWidth / 2, tY + tHeight,
                borderThickness * 1.25f, curBorderColor),
            new Triangle(tX + 3 * tWidth / 8, tY + 2 * tHeight / 3,
                tHeight / 3, tWidth / 4, 0,
                        baseColor, curBorderColor)
        };
    }

    /**
     * This generates the tip of the tower
     * @return the list of objects that compose the tip of the tower
     */
    private DrawingObject[] drawTip()
    {
        double tipX = x + (bWidth / 2) - (tipWidth / 2);
        return new DrawingObject[]{
                new Trapezoid(tipX + (tipWidth / 8), y + tipHeight,
                                tipHeight / 4, tipWidth * 3 / 4,
                            -tipWidth * 1 / 16, borderThickness,
                                baseColor, curBorderColor),
                new Line(tipX + (tipWidth / 2), y, tipX + (tipWidth / 2), y + tipHeight * 1 / 2,
                            borderThickness * 5 * (float) size / 650,
                        curBorderColor),
                new Triangle(tipX + (tipWidth / 8), y + tipHeight * 4 / 8,
                        tipHeight * 3 / 16, tipWidth * 3 / 4, borderThickness,
                        baseColor, curBorderColor),
                new Square (tipX + (tipWidth / 8), y + tipHeight * 2 / 3,
                        tipWidth * 3 / 4, borderThickness * 7 * (float) size / 650,
                        scaffoldColor, curBorderColor)
        };
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
        /*
         * The eiffel tower's night mode is its border being lit
         * and this happens once the sun is 60% down.
         * It's turned off once the sun is 40% up.
         */
        if (isDay)
        {
            if (progress >= 0.6)
                curBorderColor = lightColor;
        }
        else
        {
            if (progress >= 0.4)
                curBorderColor = borderColor;
        }
    }
}
