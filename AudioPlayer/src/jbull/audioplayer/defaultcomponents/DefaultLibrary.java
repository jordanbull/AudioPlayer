package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jbull.audioplayer.Library;
import jbull.audioplayer.TrackView;
import jbull.audioplayer.filter.Filter;

/**
 * A standard controller for a library that displays all tracks and allows for
 * the sorting of them according to a filter.
 * @author jordan
 */
public class DefaultLibrary extends Library {
    
    @FXML
    ListView list;
    @FXML
    ComboBox filters;
    @FXML
    TextField searchTerm;
    
    public DefaultLibrary() {
        super();
    }
    
    @Override
    public URL getFXML() {
        return this.getClass().getResource("DefaultLibrary.fxml");
    }
    
    private ArrayList<TrackView> tracks = new ArrayList<TrackView>();
    private HashMap<String, Filter> filterMap = new HashMap<String, Filter>();
    
    @Override
    protected int getNumTracks() {
        return tracks.size();
    }

    @Override
    protected void sort(Filter filter) {
        empty();
        int size = getNumTracks();
        for (int i = 0; i < size; i++) {
            TrackView track = tracks.get(i);
            ObservableList<Node> items = list.getItems();
            int numChildren = items.size();
            for (int j = 0; j <= numChildren; j++){
                if (j == numChildren) {
                    filter.compareAndInsert(track, null, items, j);
                    break;
                } else if (filter.compareAndInsert(track, items.get(j), items, j)) {
                    break;
                }
            }
        }
    }

    @Override
    protected void empty() {
        list.getItems().clear();
    }

    @Override
    protected void insertTrack(TrackView track) {
        Filter filter = filterMap.get((String)filters.getSelectionModel().getSelectedItem());
        tracks.add(track);
        ObservableList<Node> items = list.getItems();
        int numChildren = items.size();
        for (int j = 0; j <= numChildren; j++){
            if (j == numChildren) {
                filter.compareAndInsert(track, null, items, j);
                break;
            } else if (filter.compareAndInsert(track, items.get(j), items, j)) {
                break;
            }
        }
    }

    @Override
    protected boolean addFilter(Filter filter) {
        if (filters.getItems().contains(filter.getName())) {
            return false;
        }
        filters.getItems().add(filter.getName());
        filterMap.put(filter.getName(), filter);
        return true;
    }

    @Override
    protected void onFilterChange() {
        Filter filter = filterMap.get((String)filters.getSelectionModel().getSelectedItem());
        sort(filter);
    }
}
