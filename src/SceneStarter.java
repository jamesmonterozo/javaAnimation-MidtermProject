/**
 * This Scene Starter starts up the scene. It creates an instance
 * of the Scene Frame object and sets up the GUI, Listeners, and Music.
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

public class SceneStarter {

    public static void main (String args[])
    {
        SceneFrame sf = new SceneFrame();
        sf.setUpGUI();
        sf.setUpListeners();
        sf.startMusic();
    }
}
