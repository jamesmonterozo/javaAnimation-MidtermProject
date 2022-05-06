/**
 * This is an interface implemented by objects that need to transition between a day and a night mode.
 * The only method required by this interface is a method that can be called to transition the object
 * from its day mode to its night mode or vice-versa.
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

public interface DayNightObject {

    /** This method is called when transitioning between a day and a night mode. */
    public void timeTransition (boolean isDay, double progress);
}
