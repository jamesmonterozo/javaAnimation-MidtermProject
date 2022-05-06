import java.awt.*;
import java.util.*;

/**
 * This is a composite shape that draws the tree using Circles and the parallelogram.
 * This tree can also be transitioned between its day and night modes, and
 * the generation of the tree could be randomized.
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

public class Tree extends DrawingObject implements DayNightObject {
    private double x;
    private double y;
    private double size;
    private Color leafColor;
    private Color trunkColor;

    private Color curLeafColor;
    private Color curTrunkColor;
    private double darkness;

    private long seed;

    private Random randomizer;

    /**
     * This creates a Tree object with the parameters specified.
     * @param x             x value of the base of the tree
     * @param y             y value of the base of the tree
     * @param size          size of the tree
     * @param leafColor     color of the tree's leaves
     * @param trunkColor    color of the tree's trunk
     * @param darkness      how dark the tree can get
     * @param seed          used to randomize the tree creation
     */
    public Tree(double x, double y, double size, Color leafColor, Color trunkColor, double darkness, long seed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.leafColor = leafColor;
        this.trunkColor = trunkColor;

        this.curLeafColor = leafColor;
        this.curTrunkColor = trunkColor;
        this.darkness = darkness;

        this.seed = seed;
    }

    /**
     * This draws the tree by generating its parts
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d) {
        // draws the trunk of the tree
        Parallelogram trunk =
                new Parallelogram(x - (size / 4) / 2, y, size,
                                size / 4, 0,
                                0, curTrunkColor, curTrunkColor);
        trunk.draw(g2d);

        // generates and draws the tree leaves
        for (Circle leaf : generateLeaves())
        {
            leaf.draw(g2d);
        }
    }

    /**
     * This creates the leaves of the tree using circles.
     * @return the leaves created
     */
    private ArrayList<Circle> generateLeaves ()
    {
        randomizer = new Random(seed);

        // These are the base leaves
        ArrayList<Circle> leaves = new ArrayList<Circle> (
                Arrays.asList(
                    new Circle(x - size / 3, y, size / 3, 0, curLeafColor, curLeafColor),
                    new Circle(x, y, size / 3, 0, curLeafColor, curLeafColor),

                    new Circle(x - size / 2, y - size / 6, size / 3, 0, curLeafColor, curLeafColor),
                    new Circle(x + size / 6, y - size / 6, size / 3, 0, curLeafColor, curLeafColor),

                    new Circle(x - size * 1 / 3, y - size / 3, size / 3, 0, curLeafColor, curLeafColor),
                    new Circle(x, y - size / 3, size / 3, 0, curLeafColor, curLeafColor)
                )
        );

        // the leaves are then adjusted randomly
        for (Circle leaf : leaves)
        {
            double adjX = size / 6 * randomizer.nextDouble();
            double adjY = size / 6 * randomizer.nextDouble();
            if(randomizer.nextInt(2) == 0)
            {
                adjX = -adjX;
            }
            if(randomizer.nextInt(2) == 0)
            {
                adjY = -adjY;
            }

            leaf.adjustX(adjX);
            leaf.adjustY(adjY);
        }

        // these additional leaves ensure that there are no gaps at the center
        leaves.addAll(
                Arrays.asList(
                        new Circle(x, y - size / 6, size / 2, 0, curLeafColor, curLeafColor),
                        new Circle(x - size * 1 / 2, y - size / 6, size / 2, 0, curLeafColor, curLeafColor),
                        new Circle(x - size / 4, y - size / 3, size / 2, 0, curLeafColor, curLeafColor)
                )
        );

        return leaves;
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
        curLeafColor = curColor(isDay, progress, leafColor, darkness);
        curTrunkColor = curColor(isDay, progress, trunkColor, darkness);
    }
}
