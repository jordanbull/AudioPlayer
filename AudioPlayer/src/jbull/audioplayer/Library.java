/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
     * Adds the track to the library and places it in according to the current
     * filter.
     * @param track 
     */
    protected abstract void insertTrack(TrackView track);
    
    /**
     * Empties out all of the {@link TrackView}s from the GUI of the library.
     */
    protected abstract void empty();
    
    /**
     * Adds a filter to the comboBox and sets up any other necessary information.
     * A filter should not be added if one already exists with the same name.
     * @param filter    the filter to be added to the library's options
     * @return          returns whether or not the filter was successfully added
     */
    protected abstract boolean addFilter(Filter filter);
    
    /**
     * The method that should be called by default by the comboBox on change.
     */
    @FXML
    protected abstract void onFilterChange();
}
