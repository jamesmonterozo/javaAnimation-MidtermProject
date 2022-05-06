import java.awt.*;
import java.util.*;

/**
 * This is a composite shape that draws a Pathway using the trapezoid and Line shapes.
 * This pathway can also be transitioned between its day and night modes.
 * The x and y values indicate the top left corner of the pathway.
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

public class Pathway extends DrawingObject implements DayNightObject {

    private double x;
    private double y;

    private Color pathwayColor;
    private Color edgeColor;
    private Color litEdgeColor;
    private double darkness;

    private Color curEdgeColor;
    private Color curPathwayColor;

    private double pathWidth;
    private double pathAdjust;
    private double pathHeight;
    private float edgeThickness;

    /**
     * This creates a Pathway object with the parameters specified.
     * @param x             x value of the top left corner
     * @param y             y value of the top left corner
     * @param size          base size of the pathway
     * @param height        the height of the pathway
     * @param pathwayColor  the color of the pathway
     * @param edgeColor     the color of the lines on the pathway
     * @param litEdgeColor  the color of the lines at night
     * @param darkness      how dark the object can get
     */
    public Pathway (double x, double y, double size, double height, Color pathwayColor, Color edgeColor, Color litEdgeColor, double darkness)
    {
        this.x = x;
        this.y = y;

        this.pathwayColor = pathwayColor;
        this.edgeColor = edgeColor;
        this.litEdgeColor = litEdgeColor;

        this.darkness = darkness;

        this.pathWidth = size;
        this.pathAdjust = size * 0.25;
        this.pathHeight = height;
        this.edgeThickness = (float) (size * 0.007);

        this.curEdgeColor = edgeColor;
        this.curPathwayColor = pathwayColor;
    }

    /**
     * This method draws the pathway using methods that generate its parts.
     * @param g2d   The Graphics2D object that will be used to draw the shape.
     */
    @Override
    public void draw(Graphics2D g2d) {
        ArrayList<Line> edges = generateEdges();
        Trapezoid path = new Trapezoid(
                x - pathWidth / 2, y,
                pathHeight + edgeThickness,
                pathWidth, pathAdjust, edgeThickness,
                curPathwayColor, curEdgeColor
        );

        path.draw(g2d);
        for (Line edge : edges)
        {
            edge.draw(g2d);
        }

    }

    /**
     * This creates the lines that cut through the pathway (ie the brick edges).
     * @return the list of lines that cut through the pathway
     */
    private ArrayList<Line> generateEdges ()
    {
        // Ensures the transparency of the edges
        curEdgeColor = new Color (
                (float) (curEdgeColor.getRed() / 255),
                (float) (curEdgeColor.getGreen() / 255),
                (float) (curEdgeColor.getBlue() / 255),
                0.5f
        );
        // creates the vertical lines
        ArrayList<Line> edges = new ArrayList<Line>();
        edges.addAll(
          Arrays.asList(
                  new Line (x, y, x, y + pathHeight, edgeThickness, curEdgeColor),
                  new Line (x - pathWidth / 4 + pathAdjust/2, y, x - pathWidth / 4,
                            y + pathHeight, edgeThickness, curEdgeColor),
                  new Line (x + pathWidth / 4 - pathAdjust/2, y, x + pathWidth / 4,
                          y + pathHeight, edgeThickness, curEdgeColor)
          )
        );

        // generates the horizontal lines
        double increment = pathHeight / 12;
        double curX = x - pathWidth / 2;
        for (int i = 1; i < 12; i++)
        {
            double curY = y + increment * i;
            double curAdj = (pathAdjust) * (12 - i) / 12.0;

            edges.add(
                    new Line(curX + curAdj, curY,
                            curX + pathWidth - curAdj, curY,
                            edgeThickness, curEdgeColor)
            );
        }

        return edges;
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
        curPathwayColor = curColor(isDay, progress, pathwayColor, darkness);

        /*
         * The pathway's night mode includes its edges being lit
         * and this happens once the sun is 60% down.
         * It's turned off once the sun is 40% up.
         */
        if (isDay)
        {
            if (progress >= 0.6)
                curEdgeColor = litEdgeColor;
        }
        else
        {
            if (progress >= 0.4)
                curEdgeColor = edgeColor;
        }
    }
}
