import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.*;

/**
 * This scene canvas contains and handles the drawing and updating
 * of all visual elements of the scene. This handles signalling the transition
 * of objects to day/night mode, and shifts the objects left or right
 * depending on keyboard input.
 *
 * In terms of horizontal movement, the canvas will only go as far as the width
 * variable to the left and the right.
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

public class SceneCanvas extends JComponent {

    private int width;
    private int height;

    private double x;
    private double hMove;

    private double animationSpeed;

    private ArrayList<DrawingObject> drawings;
    private Sky sky;

    /**
     * This creates the Scene Canvas object and generates the scene objects
     * @param w                 the width of the canvas
     * @param h                 the height of the canvas
     * @param seed              used for randomization of the objects
     * @param animationSpeed    the animation speed for moving objects and shifting the canvas
     */
    public SceneCanvas(int w, int h, long seed, double animationSpeed)
    {
        width = w;
        height = h;
        x = 0; // Tracks the x position of the origin relative to the moving canvas
        hMove = 0;
        this.animationSpeed = animationSpeed;

        setPreferredSize(new Dimension(width, height));

        // Creates the colors needed for the objects
        Color[] catColors = new Color[] {
                new Color (238, 150, 75),
                new Color (123, 62, 25),
                new Color (112, 108, 97),
                new Color (255, 254, 255),
                new Color (29, 26, 36)
        };
        Color[] leftPersonColors = new Color[]{
                new Color(164,124,106), new Color(221,219,255), new Color(22,32,42), new Color(133,77,39), Color.BLACK
        };
        Color[] rightPersonColors = new Color[]{
                new Color(196,169,152), new Color(85,36,50), new Color(188,179,164), new Color(133,77,39), Color.BLACK
        };
        Color[] tableSetColors = new Color[]{
                new Color(133,77,39), Color.WHITE, new Color (1, 0.97f, 0, 0.29f), Color.WHITE, Color.BLACK
        };
        Color[] leftCatColors = new Color[]{
                new Color(211,174,122), Color.BLACK, Color.BLACK
        };
        Color[] rightCatColors = new Color[]{
                new Color(103,100,80), Color.BLACK, Color.BLACK
        };
        Color[] flagTriColor = new Color[]{
                new Color(0, 47, 103),
                new Color(255, 255, 255),
                new Color(239, 65, 53)
        };
        Color[] sunColors = new Color[]{
          Color.YELLOW,
          Color.ORANGE
        };

        // Generates all needed objects
        drawings = new ArrayList<DrawingObject> (
            Arrays.asList(

                    new BackgroundTrees(-width, width * 2, 430, 100, new Color(105, 143, 21), new Color(129, 82, 63), 0.2, 50, seed),
                    new EiffelTower(width / 2.0, 10, 500, 1, new Color(241,172,82), Color.DARK_GRAY, Color.BLACK, Color.YELLOW),
                    new Ground(-width, width * 2, 510, height, width * 0.4, new Color(56, 176, 0), Color.GRAY, Color.YELLOW, new Color (158, 240, 26), 0.2, 100, 5, seed),
                    new FlagPole(-width + width / 5d, 510, 300, flagTriColor, Color.LIGHT_GRAY, Color.BLACK, Color.YELLOW, 0.2, animationSpeed * 0.1),
                    new CatSystem(-width, width, 510, height, 60, 7, catColors, Color.BLACK, 500, animationSpeed, seed),
                    new Date(width + width / 2d, 510 + (height - 510) / 2d, 150, leftPersonColors, rightPersonColors, tableSetColors, leftCatColors, rightCatColors, 0.2, animationSpeed * 0.1, seed),
                    new WelcomeSign(-width + width / 3d, 0 + height / 4d, 200, flagTriColor, Color.WHITE, Color.BLACK, Color.YELLOW)
            )

        );
        sky = new Sky(0, 0, width, height, 50, Color.CYAN, sunColors, Color.YELLOW, Color.WHITE, Color.WHITE, 0.1, animationSpeed / 5, seed);

    }

    /**
     * This handles the painting of the objects on the canvas.
     * @param g The Graphics object that will be used for drawing on the canvas
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Applies anti aliasing
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2d.setRenderingHints(rh);

        sky.draw(g2d); // Draws the sky

        // Draws all objects
        for (DrawingObject drawing : drawings)
        {
            // Obtains original stroke, color and Transform to ensure no errors from misaligned transforms
            Stroke ogStroke = g2d.getStroke();
            Color ogColor = g2d.getColor();
            AffineTransform ogAT = g2d.getTransform();

            drawing.draw(g2d);

            g2d.setStroke(ogStroke);
            g2d.setColor(ogColor);
            g2d.setTransform(ogAT);
        }
    }

    /**
     * If the canvas should be moving (if the A or D keys are pressed), this handles the
     * shifting of all objects from left to right.
     */
    public void updateCanvasX () {

        if(hMove != 0)
        {
            double distance = hMove * animationSpeed;
            if (distance > 0 && x < width)
            {
                // move the canvas right
                if (x + distance > width)
                {
                    // ensures that the canvas will remain within its limits
                    distance = width - x;
                    x = width;
                }
                else
                {
                    x += distance;
                }

                // adjusts the x value of the objects
                sky.adjustX(distance);
                for (DrawingObject drawing : drawings)
                {
                    drawing.adjustX(distance);
                }
            }
            else if (distance < 0 && x > -width)
            {
                // move the canvas left
                if (x + distance < -width)
                {
                    // ensures that the canvas will remain within its limits
                    distance = -width - x;
                    x = -width;
                }
                else
                {
                    x += distance;
                }

                // adjusts the x value of the objects
                sky.adjustX(distance);
                for (DrawingObject drawing : drawings)
                {
                    drawing.adjustX(distance);
                }
            }
        }

    }

    /**
     * If the canvas should be transitioning between day and night or vice-versa,
     * this handles updating the objects such that their day/night mode is activated.
     */
    public void updateTime ()
    {
        if (sky.isTimeTrans())
        {
            // Updates the sky as necessary
            sky.updateSky(animationSpeed);

            // This performs transitions if the sky is still transitioning
            if (sky.isTimeTrans())
            {
                double progress = sky.getPercent();
                boolean isDay = sky.isDay();
                for (DrawingObject drawing : drawings)
                {
                    ((DayNightObject) drawing).timeTransition(isDay, progress);
                }
            }

        }
    }

    /**
     * This allows for the editing of the hMove variable.
     * A stationary canvas will have this set to 0 while a moving canvas
     * will have this set to either 1 or -1 depending on if the canvas should
     * be moving left or right respectively.
     * @param hMove The value that the horizontal movement will be set to.
     */
    public void setHMove(double hMove) {
        this.hMove = hMove;
    }

    /**
     * This handles mouse click events by passing the event onto the sky object.
     * @param e The mouse event
     */
    public void mouseClicked(MouseEvent e) {
        sky.checkMouseClick(e);
    }
}
