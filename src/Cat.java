import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * This is a composite shape that draws the cat using the Circle, Parallelogram and Line.
 * This cat can also be transitioned between its day and night modes, and
 * the generation of the cloud could be randomized.
 * The cat can also be animated and a randomizer determines the cat's
 * different animation states and movements.
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

public class Cat extends DrawingObject implements DayNightObject {

    private double x;
    private double y;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private double size;
    private float borderThickness;

    private double bodyLength;
    private double bodyWidth;
    private double legWidth;
    private double legHeight;

    private double legMove;
    private int legDir;

    private Color baseColor;
    private Color borderColor;
    private Color eyeColor;

    private Color curBaseColor;
    private Color curEyeColor;
    private double darkness;


    private Random randomizer;
    private long seed;
    private boolean isLeft;
    private boolean isUp;
    private int animationState;
    private double animationSpeed;
    private double cooldown;
    private double curCooldown;

    /**
     * This creates a Cat object with the parameters specified.
     * @param x             x position of the cat
     * @param y             y position of the cat
     * @param minX          minimum x that the cat can go to
     * @param maxX          maximum x that the cat can go to
     * @param minY          minimum y that the cat can go to
     * @param maxY          maximum y that the cat can go to
     * @param size          size of the cat
     * @param baseColor     the cat's fur color
     * @param borderColor   the cat's border color
     * @param eyeColor      the cats eye color
     * @param cooldown      the cooldown of the cat's change in animation state
     * @param seed          used to randomize cat movement
     */
    public Cat (double x, double y, double minX, double maxX, double minY, double maxY, double size, Color baseColor, Color borderColor, Color eyeColor,  int cooldown, long seed)
    {
        this.size = size;
        legMove = 0;
        legDir = -1;
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
        borderThickness = (float) (size * 0.025);

        this.baseColor = baseColor;
        this.borderColor = borderColor;
        this.eyeColor = eyeColor;
        darkness = 0.2;

        curBaseColor = baseColor;
        curEyeColor = eyeColor;

        bodyLength = size;
        bodyWidth = size / 3;
        legWidth = size / 4;
        legHeight = size / 2;

        isLeft = true;
        isUp = true;
        animationSpeed = 0;
        animationState = 0;

        this.seed = seed;
        randomizer = new Random(seed);
        this.cooldown = cooldown;
        curCooldown = 0;
    }

    /**
     * This generates a stationary sitting cat with no movement.
     * @param x             x position of the cat
     * @param y             y position of the cat
     * @param size          size of the cat
     * @param baseColor     cat's fur color
     * @param borderColor   cat's border color
     * @param eyeColor      cat's eye color
     * @param isLeft        direction the cat is facing
     */
    public Cat (double x, double y, double size, Color baseColor, Color borderColor, Color eyeColor, boolean isLeft)
    {
        this.size = size;
        legMove = 0;
        legDir = -1;
        this.x = x;
        this.y = y;
        borderThickness = (float) (size * 0.025);

        this.baseColor = baseColor;
        this.borderColor = borderColor;
        this.eyeColor = eyeColor;
        darkness = 0.2;

        curBaseColor = baseColor;
        curEyeColor = eyeColor;

        bodyLength = size;
        bodyWidth = size / 3;
        legWidth = size / 4;
        legHeight = size / 2;

        this.isLeft = isLeft;
    }

    /**
     * This method draws the cat based on its animation state.
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        // An animation state of 0 means the cat is sitting.
        // otherwise, the cat is standing
        if (animationState == 0)
            drawSitting(g2d);
        else
            drawUpright(g2d);

        /*
        For visualizing limits of the cats' X movements
        Line l1 = new Line(minX, 0, minX, 768, 2, Color.RED);
        Line l2 = new Line(maxX, 0, maxX, 768, 2, Color.RED);
        l1.draw(g2d);
        l2.draw(g2d);
         */
    }

    /**
     * This moves the cat based on a random number generator and the animation speed
     * @param animationSpeed
     */
    public void animate (double animationSpeed)
    {
        // Change Animation State
        if (curCooldown <= 0)
        {
            //System.out.println(this);
            // More likely to keep the current direction
            if(randomizer.nextDouble() > 0.7)
                isLeft = !isLeft;

            if(randomizer.nextDouble() > 0.7)
                isUp = !isUp;
            // If moving, more likely to stay moving.
            // If standing, more likely to stay standing or move.
            // If sitting, more likely to sit.
            double r = randomizer.nextDouble();

            if(this.animationSpeed > 0)
            {
                // this will make the cat stop if the animation speed is not 0
                // ie if the cat is moving
                if(r > 0.7)
                    this.animationSpeed = 0;
            }
            else
            {
                if(animationState == 1)
                {
                    // if the cat is upright it will either sit or move
                    if (r > 0.8)
                        animationState = 0;
                    else if (r > 0.5)
                        this.animationSpeed = animationSpeed;
                }
                else
                {
                    // if the cat is sitting, it will stand
                    if(r > 0.5)
                    {
                        animationState = 1;

                    }
                }
            }
            curCooldown = cooldown;
        }
        else
        {
            // Need to cool down so the cat isn't jittering all the time
            curCooldown -= animationSpeed;
        }

        // Move Cat
        if (animationState == 1 && this.animationSpeed > 0)
        {
            // this.animationSpeed will NOT be randomized so as not to mess with the rotation of the legs
            animationSpeed += animationSpeed * randomizer.nextDouble(-0.3, 0.3);
            // Makes sure the cat doesn't go over the max distances specified
            if (x >= maxX)
            {
                isLeft = true;
                x = maxX;
            }
            else if (x <= minX)
            {
                isLeft = false;
                x = minX;
            }

            if (y >= maxY)
            {
                isUp = false;
                y = maxY;
            }
            else if (y <= minY)
            {
                isUp = true;
                y = minY;
            }


            double multX = isLeft ? -1 : 1;
            double multY = isUp ? 1 : -1;

            // moves the cat according to the animation speed
            x += multX * animationSpeed; // dont use public adjustX since that's reserved for the HMove
            if(randomizer.nextDouble() > 0.6)
                adjustY(multY * animationSpeed);

        }
    }

    /**
     * This draws the cat's face.
     * @param g2d       The Graphics2D object to draw the object with.
     * @param faceX     The x value of the cat's face
     * @param faceY     The y value of the cat's face
     * @param faceSize  The size of the cat's face
     */
    private void drawFace (Graphics2D g2d, double faceX, double faceY, double faceSize) {

        // Creates the different parts of the face of the cat
        double radius = faceSize / 2;
        Circle head = new Circle(
            faceX - radius, faceY - radius, faceSize, borderThickness,
                curBaseColor, borderColor
        );
        double earSize = faceSize / 3;
        double earHeight = Math.sqrt(Math.pow(earSize, 2) - Math.pow(earSize/2, 2));
        Triangle ear =
                new Triangle(faceX - earSize / 2, faceY - radius - earHeight * 0.89, earHeight, earSize,
                        borderThickness, borderColor, borderColor);
        double whiskerLen = radius / 2;
        Line whisker =
                new Line(faceX - whiskerLen, faceY + whiskerLen / 2, faceX + whiskerLen, faceY + whiskerLen / 2, borderThickness, borderColor);
        double noseSize = radius / 4;
        Circle nose =
                new Circle (faceX - noseSize / 2, faceY + noseSize / 2, noseSize, borderThickness, borderColor, borderColor);
        double eyeSize = radius / 3;
        Circle eye =
                new Circle (faceX - radius / 4 - eyeSize / 2, faceY - radius / 3 - eyeSize / 2, eyeSize, borderThickness / 2, curEyeColor, borderColor);

        // Draws the ears of the cat
        AffineTransform ogAT = g2d.getTransform();
        g2d.rotate(Math.toRadians(40), faceX, faceY);
        ear.draw(g2d);
        g2d.rotate(Math.toRadians(-80), faceX, faceY);
        ear.draw(g2d);
        g2d.setTransform(ogAT);

        // draws the head
        head.draw(g2d);

        // draws the whiskers
        whisker.draw(g2d);
        g2d.rotate(Math.toRadians(20), faceX, faceY);
        whisker.draw(g2d);
        g2d.rotate(Math.toRadians(-40), faceX, faceY);
        whisker.draw(g2d);
        g2d.setTransform(ogAT);

        // draws the eyes and nose
        nose.draw(g2d);
        eye.draw(g2d);
        eye.adjustX(radius / 2);
        eye.draw(g2d);

    }

    /**
     * This draws the cat upright/standing.
     * @param g2d       The Graphics2D object to draw the object with.
     */
    public void drawUpright (Graphics2D g2d)
    {
        // moves the leg if the cat is moving and not if otherwise
        if (animationSpeed == 0)
        {
            legMove = 0;
        }
        else
        {
            if (Math.abs(legMove) >= 45)
            {
                legDir = -legDir;
            }
            legMove += legDir * animationSpeed;
        }

        // generates the different parts of the cat's body upright
        Parallelogram body =
                new Parallelogram(x - bodyLength / 2, y - legHeight - bodyWidth, bodyWidth, bodyLength, 0, borderThickness, curBaseColor, borderColor);
        Parallelogram leg1 =
                new Parallelogram(x - bodyLength / 2, y - legHeight, legHeight, legWidth, 0, borderThickness, curBaseColor, borderColor);
        Parallelogram leg2 =
                new Parallelogram(x + bodyLength / 2 - legWidth, y - legHeight, legHeight, legWidth, 0, borderThickness, curBaseColor, borderColor);


        // Draws the legs (they are rotated if the cat is moving)
        AffineTransform ogAT = g2d.getTransform();
        // draws the back set of legs
        g2d.rotate(Math.toRadians(legMove), x - bodyLength / 2 + legWidth / 2, y - legHeight);
        leg1.draw(g2d);
        g2d.setTransform(ogAT);
        g2d.rotate(Math.toRadians(legMove), x + bodyLength / 2 - legWidth / 2, y - legHeight);
        leg2.draw(g2d);
        g2d.setTransform(ogAT);

        leg1.adjustX(bodyLength - legWidth);
        leg2.adjustX(-bodyLength + legWidth);
        body.draw(g2d);
        // draws the front set of legs
        g2d.rotate(Math.toRadians(-legMove), x + bodyLength / 2 - legWidth / 2, y - legHeight);
        leg1.draw(g2d);
        g2d.setTransform(ogAT);
        g2d.rotate(Math.toRadians(-legMove), x - bodyLength / 2 + legWidth / 2, y - legHeight);
        leg2.draw(g2d);
        g2d.setTransform(ogAT);

        g2d.setTransform(ogAT);

        // draws the tail of the cat and its face facing the proper directions
        double mult;
        if(isLeft)
            mult = 1;
        else
            mult = -1;
        double tailAdj = size / 3;
        Line tail =
                new Line(x + mult * size / 2, y - bodyWidth - legHeight, x + mult * (size / 2 + tailAdj), y - bodyWidth - legHeight - tailAdj, borderThickness * 3, borderColor);

        tail.draw(g2d);
        drawFace(g2d, x - mult * size / 2, y - size * 5 / 6, size / 2);

    }

    /**
     * This draws the cat sitting.
     * @param g2d       The Graphics2D object to draw the object with.
     */
    private void drawSitting (Graphics2D g2d)
    {
        // ensures that the cat faces the right direction
        double mult;
        if(!isLeft)
            mult = 1;
        else
            mult = -1;

        // generates the body and leg of the cat
        AffineTransform ogAT = g2d.getTransform();
        Parallelogram body =
                new Parallelogram (x, y - size, size, bodyWidth, mult*bodyWidth, borderThickness, curBaseColor, borderColor);
        Parallelogram leg =
                new Parallelogram (x - bodyWidth + legWidth * 0.8, y - legHeight * 1.5, legHeight * 1.5, legWidth / 2, 0, borderThickness, curBaseColor, borderColor);

        // performs the necessary adjustments
        if (!isLeft)
        {
            body.adjustX(-bodyWidth);
            leg.adjustX(bodyWidth - legWidth * 0.8);
        }
        // draws the body and leg (the leg is tilted a bit)
        body.draw(g2d);
        g2d.rotate(Math.toRadians(mult * -10), x, y - legHeight * 1.5);
        leg.draw(g2d);
        g2d.setTransform(ogAT);
        leg.adjustX(mult * legWidth / 2);
        g2d.rotate(Math.toRadians(mult * -10), x + mult * legWidth / 2, y - legHeight * 1.5);
        leg.draw(g2d);
        g2d.setTransform(ogAT);

        // draws the tail and the face of the cat
        double tailAdj = size / 3;
        Line tail =
                new Line(x - mult * bodyWidth, y, x - mult * (bodyWidth + tailAdj), y - tailAdj, borderThickness * 3, borderColor);
        tail.draw(g2d);
        drawFace(g2d, x + mult * bodyWidth, y - bodyLength, size / 2);

    }

    /**
     * This moves the object HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the object will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x += distance;
        maxX += distance;
        minX += distance;

    }

    /**
     * This moves the object VERTICALLY given a specified distance.
     * @param distance  The vertical distance the object will be moved by.
     */
    public void adjustY (double distance)
    {
        y += distance;
    }

    /**
     * Transitions the object to its night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        curBaseColor = curColor(isDay, progress, baseColor, darkness);

        // The eye color being in reverse requires a different set of calculations
        // The eyes turn lighter at night and return to normal at day
        double eyeR = eyeColor.getRed() + 255 - eyeColor.getRed();
        double eyeG = eyeColor.getGreen() + 255 - eyeColor.getGreen();
        double eyeB = eyeColor.getBlue() + 255 - eyeColor.getBlue();

        if (isDay)
        {
            curEyeColor = new Color(
                    (float) (eyeColor.getRed() + eyeR * progress) / 255,
                    (float) (eyeColor.getGreen() + eyeG * progress) / 255,
                    (float) (eyeColor.getBlue() + eyeB * progress) / 255
            );
        }
        else
        {
            curEyeColor = new Color(
                    (float) (eyeColor.getRed() + 255 - eyeR * progress) / 255,
                    (float) (eyeColor.getGreen() + 255 - eyeG * progress) / 255,
                    (float) (eyeColor.getBlue() + 255 - eyeB * progress) / 255
            );
        }

    }

    /**
     * This obtains the current y position of the cat.
     * @return the y position of the cat
     */
    public double getY(){
        return y;
    }
}
