package jbull.audioplayer;

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
        Scene s = new Scene(launcher);
        stage.setScene(s);
        stage.setResizable(false);
        stage.show();
        launcher.launch();
    }
    
}
