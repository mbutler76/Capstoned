package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.io.IOException;

public class Browser extends Region
{
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    public final javax.swing.text.Document doc;
    public final GUI gui;

    public Browser() throws IOException {


        final JFrame mainFrame = new JFrame("Selenium IDE");
        gui = new GUI();
        mainFrame.setContentPane(gui.mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        doc = gui.textEditor.getDocument();
        final String beginningCommand = "driver.findElement(By.xpath(\"";
        final String endCommand = "\"))";
        final String clickCommand = ".click();";
        final String getTextCommand = ".getText()";
        final String sendKeysCommand = ".sendKeys(";
        final String sleepCommand = "\nThread.sleep(1000);";

        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load(gui.URL);


        /*Event listener that gets added to each web element*/
        final EventListener listener = new EventListener() {
            public void handleEvent(Event ev) {
                if(gui.isRecording) {
                    Element element = (Element) ev.getCurrentTarget();
                    String insert;

                    if (element.getAttribute("id") != null) {
                        insert = "\n" + beginningCommand + "//*[@id='" + element.getAttribute("id") + "']" + endCommand;
                        if(gui.dotClick)
                            insert = insert + clickCommand + sleepCommand;
                        else if(gui.dotGetText)
                            insert = "\nSystem.out.println(" + beginningCommand + "//*[@id='" + element.getAttribute("id") + "']" + endCommand + getTextCommand + ");" + sleepCommand;
                        else if(gui.dotSendKeys)
                            insert = insert + sendKeysCommand;
                        try {
                            doc.insertString(doc.getLength(), insert, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    } else if (element.getAttribute("name") != null) {
                        insert = "\n" + beginningCommand + "//*[@name='" + element.getAttribute("name") + "']" + endCommand;
                        if(gui.dotClick)
                            insert = insert + clickCommand + sleepCommand;
                        else if(gui.dotGetText)
                            insert = "\nSystem.out.println(" + beginningCommand + "//*[@name='" + element.getAttribute("name") + "']" + endCommand + getTextCommand + ");" + sleepCommand;
                        else if(gui.dotSendKeys)
                            insert = insert + sendKeysCommand;
                        try {
                            doc.insertString(doc.getLength(), insert, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    } else if (element.getAttribute("class") != null) {
                        insert = "\n" + beginningCommand + "//*[@class='" + element.getAttribute("class") + "']" + endCommand;
                        if(gui.dotClick)
                            insert = insert + clickCommand + sleepCommand;
                        else if(gui.dotGetText)
                            insert = "\nSystem.out.println(" + beginningCommand + "//*[@class='" + element.getAttribute("class") + "']" + endCommand + getTextCommand + ");" + sleepCommand;
                        else if(gui.dotSendKeys)
                            insert = insert + sendKeysCommand;
                        try {
                            doc.insertString(doc.getLength(), insert, null);
                        } catch (BadLocationException exc) {
                            exc.printStackTrace();
                        }
                    } else {
                        //System.out.println(element.getAttribute("*"));
                    }
                }
            }
        };

        /*Adds event listeners to web elements*/
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue == Worker.State.SUCCEEDED) {
                    System.out.println(webEngine.getLocation());

                    NodeList nodeList = webEngine.getDocument().getElementsByTagName("*");

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        org.w3c.dom.Node node = nodeList.item(i);
                        Element element = (Element) node;

                        if (!element.getTagName().contentEquals("HTML") && !element.getTagName().contentEquals("BODY")
                                && !element.getTagName().contentEquals("HEAD") && !element.getTagName().contentEquals("DIV") ) {

                            ((EventTarget) element).addEventListener("click", listener, false);
                        }
                    }
                }
            }
        });

        //add the web view to the scene
        getChildren().add(browser);
    }


    @Override
    protected void layoutChildren()
    {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height)
    {
        return 750;
    }

    @Override protected double computePrefHeight(double width)
    {
        return 500;
    }
}