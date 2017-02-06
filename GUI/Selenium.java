package GUI;

import javax.swing.*;
import java.awt.Desktop;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by bbeckwith on 1/23/2017.
 */
public class Selenium {
    private JPanel mainPanel;
    private JPanel fileTab;
    private JPanel editTab;
    private JPanel recordTab;
    private JPanel testTab;
    private JPanel browserPane;
    private JPanel testPane;
    private JList testCases;
    private JScrollBar testScrollbar;
    private JButton saveButton;
    private JButton openButton;
    private JButton newButton;
    private JLabel testLabel;
    private JProgressBar progressBar;
    private JLabel runsLabel;
    private JLabel failuresLabel;
    private JButton runButton;
    private JButton stopButton;
    private JButton recordButton;
    private JButton pauseButton;
    private JButton stopButton1;
    private JEditorPane googleChromeEditorPane;
    private JPanel editorPane;
    private JEditorPane textEditor;
    private JEditorPane pictureEditorPane;


    public static void main(String[] args) {
            JFrame mainFrame = new JFrame("Selenium IDE");
            mainFrame.setContentPane(new Selenium().mainPanel);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setVisible(true);

    }
}