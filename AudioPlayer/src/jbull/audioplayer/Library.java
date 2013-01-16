package jbull.audioplayer;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import jbull.audioplayer.Codec.MetaData;
import jbull.audioplayer.filter.Filter;

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
     * Sorts all of the songs in the library according to the specified {@link Filter}.
     * The filter is also responsible for inserting all of the tracks into the
     * library.
     * @param filter    a {@link Filter} for the tracks to be sorted on
     */
    protected abstract void sort(Filter filter);
    
    /**
     * Empties out all of the {@link TrackView}s from the GUI of the library.
     */
    protected abstract void empty();
    
    /**
     * Appends and item to the end of the library's view.
     * @param node  the node to be appended to the library
     */
    public abstract void appendItem(Node node);
    
    /**
     * Adds a filter to the comboBox and sets up any other necessary information.
     * A filter should not be added if one already exists with the same name.
     * @param filter    the filter to be added to the library's options
     * @return          returns whether or not the filter was successfully added
     */
    protected abstract boolean addFilter(Filter filter);
    
    /**
     * Removes all of the filters from the filter combobox.
     */
    protected abstract void removeFilters();
    
    /**
     * Sets the filter combobox to the selected index.
     * @param index the index to set the filter combobox to
     */
    protected abstract void setFilter(int index);
    
    /**
     * Returns the filter that is currently selected for the library.
     * @return  the filter currently selected for the library
     */
    protected abstract Filter getFilter();
    
    /**
     * The method that should be called by default by the comboBox on change.
     */
    @FXML
    protected abstract void onFilterChange();
    
    
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
