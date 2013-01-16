package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        choosePlaylist.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                setPlaylist(newValue);
            }
        });
        choosePlaylist.getItems().clear();
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

    @Override
    protected void renamePlaylistInGUI(String oldName, String newName) {
        choosePlaylist.getItems().remove(oldName);
        choosePlaylist.getItems().add(newName);
        if (this.getName().equals(oldName)) {
            setPlaylistGUI(newName);
        }
    }

    @Override
    protected void emptyTracksFromGUI() {
        tracks.clear();
    }
}
