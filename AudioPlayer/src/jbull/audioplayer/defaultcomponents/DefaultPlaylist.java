package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import java.util.HashMap;
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

    HashMap<String, Integer> playlistMapping = new HashMap<String, Integer>();
    
    ObservableList tracks;
    
    int position = -1;
    
    
    public DefaultPlaylist() {
        super();
        tracks = list.getItems();
    }
    
    public URL getFXML() {
        return this.getClass().getResource("DefaultPlaylist.fxml");
    }
    
    @Override
    public TrackView first() {
        position = -1;
        return next();
    }

    @Override
    public TrackView next() {
        if (hasNext()) {
            position++;
            return (TrackView) tracks.get(position);
        } else {
            position = -1;
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return (position+1) < tracks.size();
    }

    @Override
    public TrackView prev() {
        if (hasPrev()) {
            position--;
            return (TrackView) tracks.get(position);
        } else {
            position = -1;
            return null;
        }
    }

    @Override
    public boolean hasPrev() {
        return position > 0;
    }

    @Override
    public int numTracks() {
        return tracks.size();
    }

    @Override
    protected void addTrack(TrackView track, int index) {
        if (index <= position) {
            position++;
        }
        tracks.add(index, track);
    }

    @Override
    public void removeTrack(int index) {
        if (index <= position) {
            position--;
        }
        tracks.remove(index);
    }

    @Override
    protected String getName() {
        String s = (String) choosePlaylist.getSelectionModel().getSelectedItem();
        return s;
    }

    @Override
    protected void addPlaylist(String playlistName, int playlistID) {
        playlistMapping.put(playlistName, playlistID);
        choosePlaylist.getItems().add(playlistName);
    }

    @Override
    protected void removePlaylist(String playlistName) {
        playlistMapping.remove(playlistName);
        choosePlaylist.getItems().remove(playlistName);
    }

    @Override
    protected void addTrack(TrackView track) {
        tracks.add(track);
    }

    @Override
    protected void setPlaylist(int i) {
        choosePlaylist.getSelectionModel().select(i);
    }

    @Override
    protected void removeAllPlaylists() {
        choosePlaylist.getItems().clear();
    }

}
