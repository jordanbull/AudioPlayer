package jbull.audioplayer;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class AudioPlayer extends javafx.application.Application {
    
    protected static Stage stg;
    @Override
    public void start(Stage stage) throws Exception {
                try {
            Database.connectToDatabase();
        } catch (ClassNotFoundException | SQLException ex) {
            //TODO handle problem
            Logger.getLogger(Launch.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
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
