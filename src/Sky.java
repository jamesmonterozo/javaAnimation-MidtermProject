import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This is a system that creates and manages the sky objects
 * along with the day and night transition. The Cloud System, Star System,
 * Sun, Moon, and base Sky are part of this system.
 * This system checks if mouse clicks are within the bounding box of the
 * sun/moon and acts appropriately.
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

public class Sky extends DrawingObject{

    private double x;
    private double y;
    private double height;
    private double width;
    private Color dayColor;
    private Color curSkyColor;
    private double darkness;

    private Sun sun;
    private Moon moon;
    private StarSystem stars;
    private CloudSystem clouds;

    private boolean isDay;
    private boolean isTimeTrans;
    private double percent;

    /**
     * This creates a new Sky object with the given parameters.
     * The Sun, Moon, Cloud System, and Star System are generated as well.
     * @param x                 x value of the upper left corner
     * @param y                 y value of the upper left corner
     * @param width             width of the sky (corresponding to canvas width)
     * @param height            height of the sky
     * @param skyObjSize        determines the size of the sun and moon
     * @param dayColor          color of the sky in the day
     * @param sunColors         colors of the sun
     * @param moonColor         colors of the moon
     * @param cloudColor        color of the clouds
     * @param starColor         color of the stars
     * @param darkness          how dark the sky can get
     * @param animationSpeed    the speed of the clouds
     * @param seed              used to randomize different components
     */
    public Sky(double x, double y, double width, double height, double skyObjSize, Color dayColor, Color[] sunColors, Color moonColor, Color cloudColor, Color starColor, double darkness, double animationSpeed, long seed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dayColor = dayColor;
        this.darkness = darkness;

        this.curSkyColor = dayColor;

        // Creation of the sky components
        double sunRadius = skyObjSize;
        double moonRadius = skyObjSize * 2;
        sun = new Sun (width - sunRadius * 2, sunRadius * 2, sunRadius, sunColors[0], sunColors[1]);
        moon = new Moon (moonRadius * 2, height - moonRadius, moonRadius, moonColor);
        stars = new StarSystem(width, height, skyObjSize / 50, starColor, width * 100 / 1024, seed, skyObjSize * 0.7 / 50);
        clouds = new CloudSystem(-width, width * 2, 0, height / 3f, skyObjSize * 20 / 50, cloudColor, darkness, width * 500 / 1024, height * 200 / 768, seed, skyObjSize * 0.2 / 50, animationSpeed);

        // Tracks the day-night status
        isDay = true;
        isTimeTrans = false;
    }

    /**
     * This method draws the sku objects generated and the base sky as well.
     * @param g2d   The Graphics2D object used to draw the objects.
     */
    @Override
    public void draw(Graphics2D g2d) {

        Parallelogram sky = new Parallelogram(x, y, height, width, 0, 0, curSkyColor, curSkyColor);

        sky.draw(g2d);
        stars.draw(g2d);
        sun.draw(g2d);
        moon.draw(g2d);
        clouds.draw(g2d);
    }

    /**
     * Updates the sky components if a transition from day to night (or vice versa) is ongoing.
     * @param speed The speed of the transition.
     */
    public void updateSky (double speed)
    {
        if (isTimeTrans)
        {
            percent = 1 - Math.abs(sun.getY() - (height - 100)) / Math.abs(100 - (height - 100));

            if (isDay)
            {

                transitionSkyColors();
                if(sun.getY() < height - 100)
                {
                    sun.adjustY(speed);
                    moon.adjustY(-speed);
                }
                else
                {
                    isDay = false;
                    isTimeTrans = false;
                }
            }
            else
            {
                percent = 1 - percent;
                transitionSkyColors();
                if(moon.getY() < height - 100)
                {
                    moon.adjustY(speed);
                    sun.adjustY(-speed);
                }
                else
                {
                    isDay = true;
                    isTimeTrans = false;
                }
            }
        }
    }

    /** This transitions the sky objects to their night colors  */
    private void transitionSkyColors ()
    {
        stars.timeTransition(isDay, percent);
        clouds.timeTransition(isDay, percent);
        curSkyColor = curColor(isDay, percent, dayColor, darkness);
    }

    /**
     * This moves the objects HORIZONTALLY given a specified distance.
     * @param distance  The horizontal distance the objects will be moved by.
     */
    @Override
    public void adjustX(double distance) {
        // only the clouds are moved, the rest are stationary.
        clouds.adjustX(distance);
    }

    /**
     * This returns the bounding box of either the SUN or the MOON.
     * @param obj   The name of the object whose bounding box is needed.
     * @return      Returns the x and y coordinates of the bounding box of the object specified.
     */
    private double[] getBoundingBox(String obj) {
        if(obj.equalsIgnoreCase("SUN"))
        {
            return sun.getBoundingBox();
        }
        else if(obj.equalsIgnoreCase("MOON"))
        {
            return moon.getBoundingBox();
        }
        return null;
    }

    /**
     * This handles the processing of Mouse Events to check if a time transition
     * is needed.
     * @param e The mouse event
     */
    public void checkMouseClick (MouseEvent e)
    {
        if (!isTimeTrans)
        {
            double[] box = isDay ? getBoundingBox("SUN") : getBoundingBox("MOON");
            if (box != null)
                if (e.getX() >= box[0] && e.getX() <= box[2])
                    if (e.getY() >= box[1] && e.getY() <= box[3])
                        isTimeTrans = true;

        }
    }

    /**
     * This obtains if the sky is transitioning or not.
     * @return True if the sky is transitioning and false otherwise
     */
    public boolean isTimeTrans() {
        return isTimeTrans;
    }

    /**
     * This obtains the current time of Day.
     * @return True if it is currently DAY time and false otherwise
     */
    public boolean isDay() {
        return isDay;
    }

    /**
     * This obtains the progress of the sky in transitioning between times.
     * @return a percent in decimal form indicating the transition's progress
     */
    public double getPercent ()
    {
        return percent;
    }
}
