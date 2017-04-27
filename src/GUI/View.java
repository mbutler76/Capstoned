package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.BadLocationException;
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
        scene = new Scene(browser,750,500, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            //@Override
            public void handle(KeyEvent event) {

                if(browser.gui.isRecording && browser.gui.dotSendKeys) {
                    try {
                        browser.doc.insertString(browser.doc.getLength(), event.getCode().toString(), null);
                    } catch (BadLocationException exc) {
                        exc.printStackTrace();
                    }
                }
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
