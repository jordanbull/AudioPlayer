/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.DefaultControllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jbull.audioplayer.Library;
import jbull.audioplayer.TrackView;
import jbull.audioplayer.filter.Filter;

/**
 *
 * @author jordan
 */
public class LibraryController implements Library {
    
    @FXML
    ListView list;
    @FXML
    ComboBox filters;
    @FXML
    TextField searchTerm;
    
    private ArrayList<TrackView> tracks = new ArrayList<TrackView>();
    
    @Override
    public int getNumTracks() {
        return tracks.size();
    }

    @Override
    public void sort(Filter filter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void empty() {
        list.getItems().clear();
    }
    
}
