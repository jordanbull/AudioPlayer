package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import jbull.audioplayer.Playlist;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class DefaultPlaylist extends Playlist {
    
    @FXML ListView list;
    @FXML ChoiceBox<String> choosePlaylist;
    
    private ObservableList<TrackView> tracks;

    public DefaultPlaylist() {
        super();
        tracks = list.getItems();
    }
    
    @Override
    protected void addTrackToGUI(TrackView track, int index) {
        tracks.add(index, track);
    }

    @Override
    protected void addTrackToGUI(TrackView track) {
        tracks.add(track);
    }

    @Override
    protected void removeTrackFromGUI(int index) {
        tracks.remove(index);
    }

    @Override
    protected void addPlaylistToGUI(String playlistName) {
        choosePlaylist.getItems().add(playlistName);
    }

    @Override
    protected void removePlaylistFromGUI(String playlistName) {
        choosePlaylist.getItems().remove(playlistName);
    }

    @Override
    protected void setPlaylistGUI(String playlistName) {
        choosePlaylist.getSelectionModel().select(playlistName);
    }

    @Override
    public URL getFXML() {
        String FXMLname = "DefaultPlaylist.fxml";
        return getClass().getResource(FXMLname);
    }
}
