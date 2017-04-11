package GUI;


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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
        webEngine.load("http://www.csce.uark.edu/~aelezcan/index_test1.html");

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue == Worker.State.SUCCEEDED) {
                    System.out.println(webEngine.getLocation());
                    //System.out.println(observable.toString());
                    //System.out.println("old Value: " + oldValue);
                    //System.out.println("new Value: " + newValue);

                    NodeList nodeList = webEngine.getDocument().getElementsByTagName("*");

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        org.w3c.dom.Node node = nodeList.item(i);
                        //if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        //System.out.println("Node Name: " + node.getNodeName() );
                        //System.out.println("Node Value: " + node.getNodeValue() );

                        Element element = (Element) node;
                        System.out.println("Element tag name: " + element.getTagName());


                    }



                        ScriptEngineManager factory = new ScriptEngineManager();
                        ScriptEngine engine = factory.getEngineByName("JavaScript");
                        try {
                            engine.eval("print('Hello, World')");
                            //engine.eval("element.addEventListener(\"click\", function(){ alert(\"Hello World!\"); });");

                        } catch (ScriptException e) {
                            e.printStackTrace();
                        }

                        //webEngine.getDocument()

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