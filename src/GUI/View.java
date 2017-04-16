package GUI;

import GUI.Browser;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class View extends Application
{
    public Scene scene;
    private Browser browser;

    /*public View()
    {
        browser = new Browser();
    }*/

    @Override
    public void start(Stage stage)
    {
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

                System.out.println("HTML PROPERTIES: " + getBrowser().getAccessibleText());
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {

                }
                System.out.println("KEY RELEASED");
            }
        });



        scene.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                //System.out.println("Something clicked");

                //System.out.println(event.toString());
                //System.out.println(event.getSource());

                //System.out.println(scene.getOnMouseClicked().toString());

            }
        });


        System.out.println(browser.getAccessibleText());
        System.out.println(browser.getDepthTest());
        //System.out.println(browser.getTex)


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
