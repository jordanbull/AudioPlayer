package jbull.audioplayer;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import jbull.audioplayer.Codec.MetaData;

/**
 *
 * @author jordan
 */
public abstract class Library extends AnchorPane implements Component {
    
    
    protected Library() {
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
     * Returns the number of tracks in the library.
     * @return  the number of tracks in the library
     */
    protected abstract int getNumTracks();
    
    /**
     * Empties out all of the {@link TrackView}s from the GUI of the library.
     */
    protected abstract void empty();
    
    protected final static String SUCCESS = "Successfully added";
    protected final static String EXISTS = "Already in library";
    protected final static String ERROR = "Could not add";
    
    protected static String addTrackToLibrary(URI uri) {
        String path = uri.toString();
        try {
            if (!Database.Library.trackPathExists(path)) {
                String extension = path.substring(path.lastIndexOf('.')+1);
                Codec codec = Codec.getAppropriateCodec(extension);
                codec.load(uri);
                MetaData metadata = codec.getMetaData();
                Database.Library.insertTrack(metadata.title, metadata.artist,
                        metadata.album, metadata.genre, metadata.length, extension,
                        metadata.year, path);
                return "Successfully added";
            } else {
                return "Already in library";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Could not add";
        }
    }
    
    protected abstract void addTrackToGUI(TrackView track);
    protected abstract void addTracksToGUI(Collection<TrackView> tracks);
    
    protected void removeTrackFromLibrary(TrackView track) {
        try {
            Database.Library.removeTrack(track.getSongID());
            removeTrackFromGUI(track);
            for (Playlist playlist : Application.contentPane.getPlaylistPanes()) {
                playlist.restoreAllPlaylistTracksFromDatabase();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected abstract void removeTrackFromGUI(TrackView track);
    
}
