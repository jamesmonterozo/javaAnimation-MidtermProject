import java.awt.*;

/**
 * This is a complex object that creates a French Flag Pole using other basic shapes
 * (circle, Parallelogram, and Trapezoid) and the French Flag complex shape.
 * The flag consists of a trapezoid shape, the parallelogram in rectangular form
 * acting as the pole, and a ball at the top of the pole. The animated flag
 * is fixed near the ball. This also handles the day-night transition of the
 * objects involved along with moving them as the canvas shifts.
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

public class FlagPole extends DrawingObject implements DayNightObject {

    private double x;
    private double y;
    private double size;
    private double baseSize;
    private double ballSize;
    private double poleWidth;
    private float borderThickness;

    private Color basePoleColor;
    private Color baseBorderColor;
    private Color litBorderColor;
    private Color curPoleColor;
    private Color curBorderColor;

    private double darkness;
    private FrenchFlag flag;

    private double animationSpeed;

    /**
     * This creates a new FlagPole object.
     * @param x                 the x position of the flagpole base
     * @param y                 the y position of the flagpole base
     * @param size              the size of the flagpole
     * @param flagTriColor      the colors of the French Flag
     * @param basePoleColor     the color of tha pole
     * @param baseBorderColor   the color of the border of the flagpole
     * @param litBorderColor    the lit color of flagpole color
     * @param darkness          how dark the flagpole can get
     * @param animationSpeed    the speed of the flag animation
     */
    public FlagPole (double x, double y, double size, Color[] flagTriColor, Color basePoleColor, Color baseBorderColor, Color litBorderColor, double darkness, double animationSpeed)
    {
        this.x = x;
        this.y = y;
        this.size = size;

        this.borderThickness = (float) (size * 0.007);
        this.baseSize = size / 5;
        this.poleWidth = size / 20;
        this.ballSize = this.poleWidth * 1.5;

        this.basePoleColor = basePoleColor;
        this.baseBorderColor = baseBorderColor;
        this.litBorderColor = litBorderColor;
        this.curPoleColor = basePoleColor;
        this.curBorderColor = baseBorderColor;
        this.darkness = darkness;

        // creation of the French Flag
        this.flag = new FrenchFlag(x + poleWidth / 2, y - baseSize / 2 - size, size * 0.3, darkness * 3, flagTriColor);
        this.animationSpeed = animationSpeed;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // creation of base components
        Trapezoid base = new Trapezoid(
            x - baseSize / 2, y - baseSize / 2,
                baseSize / 2, baseSize, (baseSize - poleWidth) / 2,
                borderThickness, curPoleColor, curBorderColor
        );
        Parallelogram pole = new Parallelogram(
                x - poleWidth / 2, y - baseSize / 2 - size,
                size, poleWidth, 0,
                borderThickness, curPoleColor, curBorderColor
        );
        Circle ball = new Circle(
                x - ballSize / 2, y - baseSize / 2 - size - ballSize / 2,
                ballSize, borderThickness,
                curBorderColor, curBorderColor
        );
        flag.animate(animationSpeed); // animation of flag

        // components are drawn
        flag.draw(g2d);
        pole.draw(g2d);
        base.draw(g2d);
        ball.draw(g2d);
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x += distance;
        flag.adjustX(distance);
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        flag.timeTransition(isDay, progress);
        curPoleColor = curColor(isDay, progress,basePoleColor, darkness);

        // the border color is lit at night
        if (isDay)
        {
            if (progress >= 0.6)
                curBorderColor = litBorderColor;
        }
        else
        {
            if (progress >= 0.4)
                curBorderColor = baseBorderColor;
        }
    }

}
