import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * This is a system that generates the ground objects for the canvas.
 * This includes the pathway, the grass objects generated randomly, and
 * the base ground itself. This also handles the horizontal movement and day-night
 * transition of the objects.
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

public class Ground extends DrawingObject implements DayNightObject {

    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private Color groundColor;
    private Color curGroundColor;
    private Color grassColor;
    private double grassDensity;
    private double pathSize;
    private double darkness;
    private double grassSize;
    private long seed;

    private Pathway pathway;
    private ArrayList<Grass> grass;

    /**
     * This creates a new Ground object with the specified parameters.
     * The Grass and Pathway components are also generated.
     * @param x1                x value of the top left corner
     * @param x2                x value of the bottom right corner
     * @param y1                y value of the top left corner
     * @param y2                x value of the bottom right corner
     * @param pathWidth         width of the pathway
     * @param groundColor       color of the ground
     * @param pathColor         color of the pathway
     * @param pathLightColor    color of the pathway when lit
     * @param grassColor        color of the grass
     * @param darkness          how dark the components can get
     * @param grassDensity      the density of the grass
     * @param grassSize         base size of the grass
     * @param seed              used to randomize the grass creation
     */
    public Ground(double x1, double x2, double y1, double y2, double pathWidth, Color groundColor, Color pathColor, Color pathLightColor, Color grassColor, double darkness, double grassDensity, double grassSize, long seed) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.groundColor = groundColor;
        this.curGroundColor = groundColor;
        this.grassColor = grassColor;
        this.grassDensity = grassDensity;
        this.grassSize = grassSize;
        this.pathSize = pathWidth;
        this.darkness = darkness;
        this.seed = seed;

        // Path edge color is derived from the pathway color
        Color pathEdgeColor = new Color (
                (float) (pathColor.getRed() * darkness / 255),
                (float) (pathColor.getGreen() * darkness / 255),
                (float) (pathColor.getBlue() * darkness / 255)
        );

        // Pathway and Grass components are generated
        pathway = new Pathway((x1 + x2) / 2, y1, pathSize, y2 - y1,
                pathColor, pathEdgeColor, pathLightColor, darkness
        );
        grass = generateGrass();
    }

    /**
     * This method draws the ground components generated.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {
        // the base ground
        Parallelogram mainGround = new Parallelogram(x1, y1, (y2-y1), (x2-x1), 0, 0, curGroundColor, curGroundColor);
        mainGround.draw(g2d);

        // components on the ground are generated
        pathway.draw(g2d);
        for (Grass g : grass)
        {
            g.draw(g2d);
        }
    }

    /**
     * This method generates the grass with their randomized attributes.
     * @return the grass generated
     */
    private ArrayList<Grass> generateGrass ()
    {
        Random randomizer = new Random(seed);
        ArrayList<Grass> grass = new ArrayList<Grass> ();
        for(double x = x1; x < x2; x += grassDensity * randomizer.nextInt(0, 101) / 100)
        {
            for(double y = y1; y < y2; y += grassDensity * randomizer.nextInt(0, 101) / 100) {
                // Randomizes aspects of the grass' attributes
                double sizeAdj = grassSize + grassSize * randomizer.nextDouble();
                double curX = x + grassDensity * randomizer.nextDouble(-0.5, 0.5);
                double curY = y + grassDensity * randomizer.nextDouble(-0.5, 0.5);

                /*
                Ensures the following conditions:
                - Grass doesn't spawn on the pathway
                - Grass doesn't spawn above or below the y limits
                - Grass doesn't spawn beyond the x limits
                 */
                if (!(curX <= (x2 + x1) / 2 + pathSize/2 && curX >= (x2 + x1) / 2 - pathSize/2))
                    if(curX > x1 && curX < x2)
                        if(curY > y1 && curY < y2)
                            // Adds the grass created
                            grass.add(
                                    new Grass(curX, curY, sizeAdj,
                                            (float) (grassSize * 1.5 / 5), grassColor, darkness, seed + (long) (x + y))
                            );
            }

        }
        return grass;
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        x1 += distance;
        x2 += distance;
        pathway.adjustX(distance);
        for (Grass g : grass)
        {
            g.adjustX(distance);
        }
    }

    /**
     * This transitions the object to their night colors
     * @param isDay     is the sky in day mode?
     * @param progress  how far down/up is the sun? (percent in decimals)
     */
    @Override
    public void timeTransition(boolean isDay, double progress) {
        pathway.timeTransition(isDay, progress);
        for (Grass g : grass)
        {
            g.timeTransition(isDay, progress);
        }
        curGroundColor = curColor(isDay, progress, groundColor, darkness);
    }
}
