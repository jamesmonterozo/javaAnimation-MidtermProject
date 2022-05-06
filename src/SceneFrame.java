import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

/**
 * This Scene Frame handles the JFrame itself along with its components.
 * This also handles the set-up for the GUI, Music, and any listeners needed.
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

public class SceneFrame {
    private JFrame frame;
    private SceneCanvas canvas;

    /**
     * This creates the Scene Frame object and initializes the frame and canvas objects.
     */
    public SceneFrame () {
        frame = new JFrame();
        canvas = new SceneCanvas(1024, 768, 12345, 5);
    }

    /**
     * This begins the looping background music.
     */
    public void startMusic ()
    {
        // Code for WAV playing from: https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
        try {

            File soundFile = new File("lefestin.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * This accomplishes the set-up for the GUI elements of the Scene Frame.
     */
    public void setUpGUI()
    {
        frame.setTitle("Midterm Project - Monterozo, Clarence James - ---");

        frame.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * This handles the creation and application for all the listeners needed
     * by the application.
     */
    public void setUpListeners() {
        // Instances of the two inner classes are created
        Mouse mouse = new Mouse();
        Update update = new Update();

        // Mouse Listener is Setup
        frame.addMouseListener(mouse);

        // The constant update is begun
        int delay = 10; // 1000 milliseconds = 1 second
        new Timer(delay, update).start();


        /**
         * This handles the movement of the canvas depending on the key pressed.
         * Pressing the "A" key will set the HMovement to 1 and pressing "D"
         * will set the HMovement to -1.
         */
        Action canvasMovement = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("a"))
                    canvas.setHMove(1);
                else if(e.getActionCommand().equals("d"))
                    canvas.setHMove(-1);

            }
        };

        /**
         * This handles stopping the movement of the canvas upon lifting the key press.
         * If either the "A" or "D" key are lifted, the HMovement is set to 0.
         */
        Action haltMovement = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("a") || e.getActionCommand().equals("d"))
                    canvas.setHMove(0);

            }
        };

        /* The keys are bound to their appropriate listeners.
        The Key Binding code was inspired by the following:
        - https://examples.javacodegeeks.com/desktop-java/swing/java-swing-key-binding-example/
        - https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html#eg
         */
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                                                                0, false),
                            "moveLeft");
        canvas.getActionMap().put("moveLeft",
                canvasMovement);
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                                                                    0, false),
                            "moveLeft");
        canvas.getActionMap().put("moveRight",
                canvasMovement);
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                        0, true),
                "stopA");
        canvas.getActionMap().put("stopA",
                haltMovement);
        canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                        0, true),
                "stopB");
        canvas.getActionMap().put("stopB",
                haltMovement);
    }

    /**
     * This action listener ensures that the canvas objects are updated appropriately.
     * Necessary update functions within the canvas are called and the canvas
     * is repainted upon each update.
     *
     * This is separated from the SetUpListeners for organization purposes
     * and easier debugging and manipulation.
     */
    private class Update implements ActionListener {

        /**
         * This handles the updating of the canvas objects and the repainting of the canvas.
         * @param e The action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Updates the sky if needed (checks are made in the method to determine the need)
            canvas.updateTime();

            // Updates the canvas if needed (checks are made in the method to determine the need)
            canvas.updateCanvasX();

            // Repaint to reflect any changes
            canvas.repaint();

        }
    }

    /**
     * This mouse listener ensures calls the necessary canvas method when a click is made.
     * The MouseEvent is passed onto the canvas method for processing.
     */
    private class Mouse implements MouseListener {

        /**
         * This passes the mouse event onto the appropriate canvas method.
         * @param e The mouse event to be passed on.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            canvas.mouseClicked(e);
        }

        /**
         * There is no need for the Mouse Pressed event, so this is left empty
         * @param e The mouse event.
         */
        @Override
        public void mousePressed(MouseEvent e) {

        }

        /**
         * There is no need for the Mouse Pressed event, so this is left empty
         * @param e The mouse event.
         */
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        /**
         * There is no need for the Mouse Pressed event, so this is left empty
         * @param e The mouse event.
         */
        @Override
        public void mouseEntered(MouseEvent e) {

        }

        /**
         * There is no need for the Mouse Pressed event, so this is left empty
         * @param e The mouse event.
         */
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
