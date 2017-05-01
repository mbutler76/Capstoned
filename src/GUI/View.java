package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
            @Override
            public void handle(KeyEvent event) {

                //Records Keyboard presses and writes to file
                if(browser.gui.isRecording && browser.gui.dotSendKeys) {
                    try {
                        if(!event.getCode().toString().equals("SHIFT"))
                            browser.doc.insertString(browser.doc.getLength(), event.getCode().toString(), null);
                    } catch (BadLocationException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
    }

    public static void openView(String[] args)
    {
        launch(args);
    }

}