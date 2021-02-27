import javax.swing.*;
import javax.swing.event.CaretEvent;
import java.awt.*;
import java.awt.event.*;


/**
 * UserInterface handles the content and actions for the text editor.
 *
 * @author Stefano Frazzetto
 * @since 1.0.0
 */
public class UserInterface extends JFrame implements ActionListener {

    private final static int DEFAULT_WINDOW_WIDTH = 800;
    private final static int DEFAULT_WINDOW_HEIGHT = 400;
    private static JTextArea contentArea;
    private static JTextArea informationArea;

    private static String windowTitle;
    private static int charactersCount = 0;
    private static int wordCount = 0;
    private static int caretPosition = 0;

    public UserInterface(String windowTitle) {
        UserInterface.windowTitle = windowTitle;
        initialiseApplicationWindow();
        initialiseWindowComponents();
        updateInformationArea();
        bindKeyListener();
        bindCaretListener();
    }

    private void initialiseApplicationWindow() {
        setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        setTitle(windowTitle);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout()); // the BorderLayout makes it fill it automatically
    }

    private void initialiseWindowComponents() {
        initialiseContentArea();
        initializeInformationArea();
    }

    private void initialiseContentArea() {
        contentArea = createTextArea();
        contentArea.setEditable(true);
        JScrollPane scrollPane = createScrollPane(contentArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel panel = createPanelWithBorderLayout(scrollPane);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void initializeInformationArea() {
        informationArea = createTextArea();
        informationArea.setEditable(false);
        JScrollPane scrollPane = createScrollPane(informationArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        JPanel panel = createPanelWithBorderLayout(scrollPane);
        getContentPane().add(panel, BorderLayout.SOUTH);
    }

    private JTextArea createTextArea() {
        // Ideally, the arguments here could be parameters of this method,
        // or they could be stored as constants, i.e. private static final <TYPE>
        JTextArea textArea = new JTextArea("", 0, 0);
        textArea.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        textArea.setTabSize(2);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JScrollPane createScrollPane(JTextArea textArea, int scrollBarPolicy) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(scrollBarPolicy);
        return scrollPane;
    }

    private JPanel createPanelWithBorderLayout(JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(component);
        return panel;
    }

    private void bindKeyListener() {
        contentArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyReleased(e);
            }
        });
    }

    private void handleKeyReleased(KeyEvent event) {
        // If the content has changed, update the information
        if (charactersCount != contentArea.getText().length()) {
            String contentAreaText = contentArea.getText();
            charactersCount = contentAreaText.length();
            wordCount = contentAreaText.trim().split("\\s+").length;
            updateInformationArea();
        }
    }

    private void bindCaretListener() {
        contentArea.addCaretListener(this::handleCaretUpdate);
    }

    private void handleCaretUpdate(CaretEvent event) {
        int caretCurrentPosition = event.getDot();
        if (caretCurrentPosition != caretPosition) {
            caretPosition = caretCurrentPosition;
            updateInformationArea();
        }
    }

    private void updateInformationArea() {
        String separator = " | ";
        String builder = "Words: " + wordCount +
                separator + "Characters: " + charactersCount +
                separator + "Cursor position: " + caretPosition;
        informationArea.setText(builder);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.dispose();
        }
    }
}
