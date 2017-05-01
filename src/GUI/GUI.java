package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.text.BadLocationException;
import org.xml.sax.*;

public class GUI {
    /*GUI components*/
    public JPanel mainPanel;
    public JPanel fileTab;
    public JPanel recordTab;
    public JPanel testTab;
    public JButton saveButton;
    public JButton openButton;
    public JButton newButton;
    public JButton runButton;
    public JButton stopTestButton;
    public JButton recordButton;
    public JButton pauseButton;
    public JButton stopRecordButton;
    public JPanel editorPane;
    public JEditorPane textEditor;
    private JButton clickButton;
    private JButton gtButton;
    private JButton sendKeysButton;
    private JButton endSendKeysButton;

    public boolean isRecording = false;
    public boolean dotClick = false;
    public boolean dotGetText = false;
    public boolean dotSendKeys = false;
    public File selected;
    public boolean isFileOpen = false;
    JFileChooser chooser = new JFileChooser();

    public static String URL = "";
    public final String firstCode = "\timport org.openqa.selenium.By;\n" +
            "\timport org.openqa.selenium.WebDriver;\n" +
            "\timport org.openqa.selenium.chrome.ChromeDriver;\n" +
            "\timport org.openqa.selenium.chrome.ChromeOptions;\n" +
            "\t\n" +
            "\tpublic class SeleniumMain {\n" +
            "\n" +
            "\tpublic static WebDriver driver;\n" +
            "\tstatic ChromeOptions options;\n" +
            "\t\n" +
            "    public static void main(String[] args) throws InterruptedException {\n" +
            "\n" +
            "    \tSystem.setProperty(\"webdriver.chrome.driver\", System.getProperty(\"user.dir\") + \"/src/main/resources/chromedriver\");\n" +
            "    \t\n" +
            "       //Create a new instance of Chrome Browser\n" +
            "       driver = new ChromeDriver();\n" +
            "       \n" +
            "       //Open the URL in Chrome browser\n" +
            "       driver.get(\"";
    public final String secondCode = "\");\n" +
            "\n" +
            "       Thread.sleep(3000);\n" +
            "       //Get the current page URL and store the value in variable 'str'\n" +
            "       String str = driver.getCurrentUrl();\n" +
            "       System.out.println(str);\n" +
            "       ///////////////////////////////////////////////\n";
    public final String endCode = "\ndriver.close();\n}\n}";
    private static View view;

    /*Info Popup*/
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    /*opens a java file*/
    public void openFile() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Files", "java");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(openButton);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selected = chooser.getSelectedFile();
            isFileOpen = true;

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

    /*Function to save a file when one is currently open*/
    public void saveFile() {
        try {
            String code = textEditor.getText();
            PrintWriter printWriter = new PrintWriter(selected);
            printWriter.print(code);
            printWriter.close();
            infoBox(selected + " was saved successfully", "Successful Save");
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public GUI() throws IOException {

        /*Opens a java file in the editor pane*/
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!isFileOpen) {
                    openFile();
                } else {
                    int dialogChoice = JOptionPane.showConfirmDialog(null, "There is already an open file, would you like to save it before opening another?", "File already open", JOptionPane.YES_NO_OPTION);
                    if (dialogChoice == JOptionPane.YES_OPTION) {
                        saveFile();
                        openFile();
                    } else {
                        openFile();
                    }
                }
            }
        });

        /*Finishes writing the necessary code
        **Saves the java file that is currently open
        **If no file open, SeleniumTest is created and saved*/
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    textEditor.getDocument().insertString(textEditor.getDocument().getLength(), endCode, null);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }

                if (isFileOpen) {
                    saveFile();
                } else {
                    chooser.setSelectedFile(new File("SeleniumTest.java"));
                    int i = chooser.showSaveDialog(saveButton);
                    if (i == chooser.APPROVE_OPTION) {
                        selected = chooser.getSelectedFile();
                        saveFile();
                    }
                }
            }
        });

        /*Starts a new file*/
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = null;
                try {
                    textEditor.getDocument().remove(0, textEditor.getDocument().getLength());
                    textEditor.getDocument().insertString(textEditor.getDocument().getLength(), firstCode + URL + secondCode, null);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }
        });

        /*Sets click command active*/
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dotClick = true;
                dotGetText = false;
                dotSendKeys = false;
            }
        });

        /*Sets get text command active*/
        gtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dotClick = false;
                dotGetText = true;
                dotSendKeys = false;
            }
        });

        /*Sets send keys command active*/
        sendKeysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dotClick = false;
                dotGetText = false;
                dotSendKeys = true;
            }
        });

        /*Ends the send keys command and sets everything else to not active*/
        endSendKeysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dotClick = false;
                dotGetText = false;
                dotSendKeys = false;
                try {
                    textEditor.getDocument().insertString(textEditor.getDocument().getLength(), ");\nThread.sleep(1000);", null);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
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


    }

    public static void main(String[] args) throws IOException, SAXException {
        /*URL input, defaults to google if canceled*/
        URL = JOptionPane.showInputDialog("Enter URL");
        if (URL == null) {
            URL = "https://www.google.com/";
        }

        /*Opens the browser and the GUI*/
        view = new View();
        view.openView(args);

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(500, 600), null, 0, false));
        final JTabbedPane tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(228, 60), null, 0, false));
        fileTab = new JPanel();
        fileTab.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("File", fileTab);
        saveButton = new JButton();
        saveButton.setText("Save");
        fileTab.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        fileTab.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        newButton = new JButton();
        newButton.setText("New");
        fileTab.add(newButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openButton = new JButton();
        openButton.setText("Open");
        fileTab.add(openButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        recordTab = new JPanel();
        recordTab.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 8, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Record", recordTab);
        recordButton = new JButton();
        recordButton.setText("Record");
        recordTab.add(recordButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        recordTab.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pauseButton = new JButton();
        pauseButton.setText("Pause");
        recordTab.add(pauseButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopRecordButton = new JButton();
        stopRecordButton.setText("Stop");
        recordTab.add(stopRecordButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clickButton = new JButton();
        clickButton.setText("Click");
        recordTab.add(clickButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gtButton = new JButton();
        gtButton.setText("Get Text");
        recordTab.add(gtButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sendKeysButton = new JButton();
        sendKeysButton.setText("Send Keys");
        recordTab.add(sendKeysButton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endSendKeysButton = new JButton();
        endSendKeysButton.setText("End Send Keys");
        recordTab.add(endSendKeysButton, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testTab = new JPanel();
        testTab.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Test", testTab);
        runButton = new JButton();
        runButton.setText("Run");
        testTab.add(runButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        testTab.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        stopTestButton = new JButton();
        stopTestButton.setText("Stop");
        testTab.add(stopTestButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(400, 400), null, 0, false));
        editorPane = new JPanel();
        editorPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        scrollPane1.setViewportView(editorPane);
        textEditor = new JEditorPane();
        textEditor.setBackground(new Color(-1114882));
        textEditor.setText("");
        editorPane.add(textEditor, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(400, 400), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}


