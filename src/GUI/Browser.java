package GUI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

public class Browser extends Region
{
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser()
    {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load("http://www.google.com/");

        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue == Worker.State.SUCCEEDED){
                    System.out.println(webEngine.getLocation());
                }
            }
        });

        //add the web view to the scene
        getChildren().add(browser);

    }

    public void loadBrowser(String url)
    {
        webEngine.load(url);
        getChildren().add(browser);
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