package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import jbull.audioplayer.Application;
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
    @FXML TableView<TrackView> tableView;
    @FXML TableColumn<TrackView, String> titleColumn;
    @FXML TableColumn<TrackView, String> artistColumn;
    @FXML TableColumn<TrackView, String> albumColumn;
    @FXML TableColumn<TrackView, String> genreColumn;
    
    public DefaultLibrary() {
        super();
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory("genre"));
        setOnDragDetected();
    }
    private void setOnDragDetected() {
        //final Library me = this;
        tableView.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println(".");
                TrackView tv = tableView.getSelectionModel().getSelectedItem();
                tv.getOnDragDetected().handle(event);
                /*Dragboard db = tv.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                if (tv.isInPlaylist()) {
                    content.putString(tv.getPlaylistInfo().getName());
                } else {
                    content.putHtml(""); // does this for the sake of working
                }
                db.setContent(content);
                //Application.draggedObject = tv;
                event.consume();*/
            }
        });
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
        filter.sortAndInsert(this);
    }

    @Override
    protected void empty() {
        list.getItems().clear();
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

    @FXML @Override
    protected void onFilterChange() {
        Filter filter = getFilter();
        sort(filter);
    }

    @Override
    public void appendItem(Node node) {
        list.getItems().add(node);
    }

    @Override
    protected void removeFilters() {
        filters.getItems().clear();
    }

    @Override
    protected Filter getFilter() {
        return filterMap.get((String)filters.getSelectionModel().getSelectedItem());
    }

    @Override
    protected void setFilter(int index) {
        filters.getSelectionModel().select(index);
    }

    @Override
    protected void removeTrackFromGUI(TrackView track) {
        sort(getFilter());
    }

    @Override
    protected void addTrackToGUI(TrackView track) {
        tableView.getItems().add(track);
    }

    @Override
    protected void addTracksToGUI(Collection<TrackView> tracks) {
        tableView.getItems().addAll(tracks);
    }
}
