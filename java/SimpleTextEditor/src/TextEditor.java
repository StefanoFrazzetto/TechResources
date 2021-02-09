import javax.swing.*;

/**
 * A implementation of a simple text editor.
 * <p>
 * The program allows users to input any number of characters in the
 * text area, while the software will display some basic information,
 * such as the count of words and characters, and the position of the
 * caret relative to the text; treat this as an array index, with the
 * first position being 0.
 *
 * @author Stefano Frazzetto
 * @version 1.0.1
 */
public class TextEditor extends JFrame {
    private final static String WINDOW_TITLE = "Simple Text Editor";

    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface(WINDOW_TITLE);
        userInterface.setVisible(true);
    }
}