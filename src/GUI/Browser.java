package GUI;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class Browser extends Region
{
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser()
    {

        System.out.println("Initializing browser");
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        //webEngine.load("http://www.csce.uark.edu/~aelezcan/index_test1.html");
        webEngine.load("http://www.google.com");

        final EventListener listener = new EventListener() {
            public void handleEvent(Event ev) {
                System.out.println("Event: " + ev.toString());
                System.out.println(((Element) ev.getCurrentTarget()).getAttribute("*"));

                System.out.println("this: " + this.toString());
            }
        };

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue == Worker.State.SUCCEEDED) {
                    System.out.println(webEngine.getLocation());

                    NodeList nodeList = webEngine.getDocument().getElementsByTagName("*");

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        org.w3c.dom.Node node = nodeList.item(i);
                        Element element = (Element) node;

                        if (!element.getTagName().contentEquals("HTML") && !element.getTagName().contentEquals("BODY")
                                && !element.getTagName().contentEquals("HEAD") ) {

                            System.out.println("Element tag name: " + element.getTagName());
                            System.out.println("Attribute: " + element.getAttribute("id"));
                            ((EventTarget) element).addEventListener("click", listener, false);
                        }
                    }
                }
            }
        });

        //add the web view to the scene
        getChildren().add(browser);

        //loadBrowser("https://www.reddit.com/");

    }
    public WebEngine getWebEngine(){
        return webEngine;
    }

    public void test(){
        System.out.print("Browser Testing");
    }

    public void loadBrowser(String url)
    {
        System.out.println(url);
        webEngine.load(url);
        webEngine.reload();
        //getChildren().add(browser);
    }

    private Node createSpacer()
    {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
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