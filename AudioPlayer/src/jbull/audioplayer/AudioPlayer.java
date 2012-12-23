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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new Application();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
