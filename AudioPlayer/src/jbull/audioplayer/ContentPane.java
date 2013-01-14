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
            linkDecksToPlaylists();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public abstract Library getLibraryPane();
    
    public abstract ArrayList<Playlist> getPlaylistPanes();
    public abstract ArrayList<Deck> getDeckPanes();
    
    protected void linkDecksToPlaylists() {
        ArrayList<Playlist> playlists = getPlaylistPanes();
        ArrayList<Deck> decks = getDeckPanes();
        for (int i = 0; i < decks.size(); i++) {
            decks.get(i).setPlaylist(playlists.get(i));
        }
    }
}
