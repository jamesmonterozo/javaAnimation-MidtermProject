import java.awt.*;
import java.util.*;

/**
 * This is a system that generates the background trees for the canvas.
 * The trees are randomly generated with varying heights, leaf configurations,
 * and colors. This also handles the trees transitioning to their day and night modes.
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

public class BackgroundTrees extends DrawingObject implements DayNightObject {
    private double x1;
    private double x2;
    private double y;
    private double baseSize;
    private Color baseTrunkColor;
    private Color baseLeafColor;
    private double density; // 1 tree every (density) units
    private long seed;
    private double darkness;

    private ArrayList<Tree> trees;

    private Random randomizer;

    /**
     * This creates a Background Trees object with the parameters specified.
     * @param x1                the leftmost x value
     * @param x2                the right most x value
     * @param y                 the base y value of all trees
     * @param baseSize          the base size of the trees
     * @param baseLeafColor     the base leaf color
     * @param baseTrunkColor    the base trunk color
     * @param darkness          how dark the trees can get
     * @param density           the density of the tree (1 tree every [<]density] units)
     * @param seed              used to randomize the tree creation
     */
    public BackgroundTrees(double x1, double x2, double y, double baseSize, Color baseLeafColor, Color baseTrunkColor, double darkness, double density,  long seed) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
        this.baseSize = baseSize;
        this.baseLeafColor = baseLeafColor;
        this.baseTrunkColor = baseTrunkColor;
        this.density = density;
        this.seed = seed;
        this.darkness = darkness;

        this.trees = generateTrees(); // generates the trees
    }

    /**
     * This method draws the trees generated.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {
        for (Tree tree : trees)
        {
            tree.draw(g2d);
        }
    }

    /**
     * This method generates the trees with their randomized attributes.
     * @return the trees generated
     */
    private ArrayList<Tree> generateTrees ()
    {
        randomizer = new Random(seed);
        int adjustChaos = 10; // Lower values are usually more chaotic

        // Length of the entire stretch
        int bgLength = Math.toIntExact(Math.round(Math.sqrt( Math.pow((x1 - x2), 2) )));

        ArrayList<Tree> trees = new ArrayList<Tree> ();

        // Generates trees to fill the background
        for(int i = 0; i < bgLength; i += density * randomizer.nextInt(75, 101) / 100)
        {
            // Randmizes the leaf color
            Color rLeafColor = new Color(
                    baseLeafColor.getRed() / 255f,
                    (float) (baseLeafColor.getGreen() + baseLeafColor.getGreen() * randomizer.nextDouble(-0.1,0.5)) / 255,
                    baseLeafColor.getBlue() / 255f);
            // Randomizes the trunk color
            Color rTrunkColor = new Color(
                    (float) (baseTrunkColor.getRed() + baseTrunkColor.getRed() * randomizer.nextDouble(-0.1,0.1)) / 255f,
                    (float) (baseTrunkColor.getGreen() + baseTrunkColor.getGreen() * randomizer.nextDouble(-0.1,0.1)) / 255,
                    baseTrunkColor.getBlue() / 255f);
            // adjusts the size and y values
            double sizeAdj = baseSize + baseSize * randomizer.nextDouble() / adjustChaos;
            double yAdj = baseSize * randomizer.nextDouble() / adjustChaos;
            if (randomizer.nextInt(2) == 0)
            {
                yAdj = -yAdj; // turns some y adjustments negative
            }
            // adds randomized tree
            trees.add(
                    new Tree (x1 + i, y + yAdj, sizeAdj, rLeafColor, rTrunkColor, darkness,seed + i)
            );
        }
        // shuffles the trees so that some will be drawn first
        Collections.shuffle(trees, randomizer);
        return trees;
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        for (Tree tree : trees)
            tree.adjustX(distance);
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {

        for (Tree tree : trees)
            tree.timeTransition(isDay, progress);

    }
}
