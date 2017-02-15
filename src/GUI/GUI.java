package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.io.File;
import javax.swing.filechooser.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class GUI {
    /*GUI compoenents*/
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
    private JButton stopTestButton;
    private JButton recordButton;
    private JButton pauseButton;
    private JButton stopRecordButton;
    private JEditorPane googleChromeEditorPane;
    private JPanel editorPane;
    private JEditorPane pictureEditorPane;
    private JEditorPane textEditor;

    public boolean isRecording = false;


    public GUI() throws IOException {

        /*Opens a java file in the editor pane*/
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Files", "java");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(openButton);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File selected = chooser.getSelectedFile();

                    try {
                        FileReader fileReader = new FileReader(selected);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                            stringBuffer.append("\n");
                        }
                        fileReader.close();
                        textEditor.setText(stringBuffer.toString());
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });

        /*Starts a recording session*/
        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRecording = true;
            }
        });

        /*pauses a recording session*/
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRecording = false;
            }
        });

        /*stops a recording session*/
        stopRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRecording = false;
            }
        });

        googleChromeEditorPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if(isRecording) {
                    System.out.println("Browser clicked");
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        JFrame mainFrame = new JFrame("Selenium IDE");
        mainFrame.setContentPane(new GUI().mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}

