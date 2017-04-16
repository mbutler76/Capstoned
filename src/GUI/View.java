package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class View extends Application
{
    public Scene scene;
    private Browser browser;

    @Override
    public void start(Stage stage) throws IOException {
        // create the scene
        stage.setTitle("Selenium IDE");
        browser = new Browser();
        browser.loadBrowser("www.google.com");
        scene = new Scene(browser,750,500, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.RIGHT) { // don't use toString here!!!
                    System.out.println(event.toString());
                } else if (event.getCode() == KeyCode.LEFT) {
                    System.out.println(event.toString());
                }
                System.out.println("INSIDE SETONKEYPRESSED");

                //System.out.println("HTML PROPERTIES: " + getBrowser().getAccessibleText());
            }
        });

        System.out.println(browser.getDepthTest());
    }

    public static void openView(String[] args)
    {
        launch(args);
    }

    public void loadBrowser(String url) {
        System.out.println("loadBrowser PART 1");
        browser.loadBrowser(url);
    }

    public Browser getBrowser(){
        return this.browser;
    }


}
