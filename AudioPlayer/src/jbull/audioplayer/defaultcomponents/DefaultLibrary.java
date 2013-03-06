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
import javafx.scene.input.MouseEvent;
import jbull.audioplayer.Library;
import jbull.audioplayer.TrackView;

/**
 * A standard controller for a library that displays all tracks and allows for
 * the sorting of them according to a filter.
 * @author jordan
 */
public class DefaultLibrary extends Library {
    
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
    
    @Override
    protected int getNumTracks() {
        return tracks.size();
    }

    @Override
    protected void empty() {
        tableView.getItems().clear();
    }

    @Override
    protected void removeTrackFromGUI(TrackView track) {
        tableView.getItems().remove(track);
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
