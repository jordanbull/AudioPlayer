/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author jordan
 */
public abstract class Playlist extends AnchorPane implements Component {
    
    public Playlist() {
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    /**
     * Sets the position to -1 and calls next().
     * @return  the first track in the playlist if there is one or null if there
     * are no tracks in the playlist
     */
    public abstract TrackView first();
    
    /**
     * Increments the position and returns the the next track. If there are no
     * more tracks, the position is set to -1 and null is returned.
     * @return  the next track if there is one and null if there is no next track
     */
    public abstract TrackView next();
    
    /**
     * Returns true if there is a next track or false if there is not.
     * @return true if there is a next track or false if there is not
     */
    public abstract boolean hasNext();
    
    /**
     * Decrements the position and returns the previous track if there is one.
     * If there is not a previous track, the position is set to -1 and null is
     * returned.
     * @return  the previous track if there is one or null otherwise
     */
    public abstract TrackView prev();
    
    /**
     * Returns true if there is a previous track or false is there is not.
     * @return  true if there is a previous track or false is there is not
     */
    public abstract boolean hasPrev();
    
    /**
     * Returns the number of tracks the library contains. Note: this is not
     * necessarily the number of {@link TrackView}s in the library GUI.
     * @return  the number of tracks in the library
     */
    public abstract int numTracks();
    
    /**
     * Adds track to the specified index pushing down all tracks at or below the
     * index.
     * @param track the {@link TrackView} to be placed in the playlist
     * @param index the index the track is to be placed at
     */
    public abstract void addTrack(TrackView track, int index);
    
    /**
     * Removes the track at the specified index pushing all tracks below the
     * index up one.
     * @param index the index of the track to be removed 
     */
    public abstract void removeTrack(int index);
}
