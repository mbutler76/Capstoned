package GUI;

import GUI.Browser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application
{
    public Scene scene;
    private Browser browser;

    @Override
    public void start(Stage stage)
    {
        // create the scene
        stage.setTitle("Selenium IDE");
        browser = new Browser();
        scene = new Scene(browser,750,500, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();
    }

    public static void openView(String[] args)
    {
        launch(args);
    }

    public void loadBrowser(String url) {
        browser.loadBrowser(url);
    }
}
