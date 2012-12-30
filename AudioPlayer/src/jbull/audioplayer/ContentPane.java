package jbull.audioplayer;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author jordan
 */
public abstract class ContentPane extends AnchorPane implements Component {
    
    protected ContentPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public abstract Library getLibraryPane();
    
    public abstract ArrayList<Playlist> getPlaylistPanes();
}
