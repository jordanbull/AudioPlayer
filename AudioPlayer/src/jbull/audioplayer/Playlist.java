package jbull.audioplayer;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

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
        final Playlist me = this;
        
        this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node 
                 * and if it has a string data */
                if (Application.draggedObject instanceof TrackView) {
                    Dragboard db = event.getDragboard();
                    String pName = ((TrackView) Application.draggedObject).playlist;
                    boolean b = pName != null;
                    if (b && pName.equals(me.getName())) { //moved from this playlist
                        event.acceptTransferModes(TransferMode.MOVE);
                    } else if (!db.hasString()) { // moved from library
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                    event.consume();
                }
            }
        });
        
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (Application.draggedObject instanceof TrackView) {
                    Dragboard db = event.getDragboard();
                    if (db.hasString() && db.getString().equals(me.getName())) { //moved from this playlist
                        event.acceptTransferModes(TransferMode.MOVE);
                    } else if (!db.hasString()) { // moved from library
                        TrackView trackView = Application.createTrackView((TrackView) Application.draggedObject);
                        me.addAndSetTrack(trackView);
                    }
                    event.consume();
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(true);
             }
        });
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
     * index. This should also set the playlist field of the TrackView. The
     * track's playlist field is also set to this playlist.
     * @param track the {@link TrackView} to be placed in the playlist
     * @param index the index the track is to be placed at
     */
    public void addAndSetTrack(TrackView track, int index) {
        track.setPlaylist(this.getName());
        addTrack(track, index);
    }
    
    /**
     * Appends a track to the end of the playlist. The
     * track's playlist field is also set to this playlist.
     * @param track the track to append to the playlist
     */
    public void addAndSetTrack(TrackView track) {
        track.setPlaylist(this.getName());
        addTrack(track);
    }
    
    /**
     * Adds track to the specified index pushing down all tracks at or below the
     * index. This should also set the playlist field of the TrackView.
     * @param track the {@link TrackView} to be placed in the playlist
     * @param index the index the track is to be placed at
     */
    protected abstract void addTrack(TrackView track, int index);
    
    /**
     * Appends a track to the end of the playlist.
     * @param track the track to append to the playlist
     */
    protected abstract void addTrack(TrackView track);
    
    /**
     * Removes the track at the specified index pushing all tracks below the
     * index up one.
     * @param index the index of the track to be removed 
     */
    public abstract void removeTrack(int index);
    
    /**
     * Returns the name of the currently displayed playlist.
     * @return  the name of the currently displayed playlist
     */
    protected abstract String getName();
    
    
    protected abstract void addPlaylist(String playlistName, int playlistID);
    
    protected abstract void removePlaylist(String playlistName);
    
    protected abstract void setPlaylist(int i);
    
    protected abstract void removeAllPlaylists();
}
