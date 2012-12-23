/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class AudioPlayer extends javafx.application.Application {
    
    public static Stage stg;
    @Override
    public void start(Stage stage) throws Exception {
        Application application = new Application();
        Launch launcher = new Launch(application);
        stg = stage;
        stage.setScene(new Scene(launcher));
        stage.show();
        launcher.launch();
    }
    
}
