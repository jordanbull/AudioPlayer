package jbull.audioplayer;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author jordan
 */
public abstract class TrackView extends AnchorPane implements Component {
    protected String title;
    protected String artist;
    protected String album;
    protected String genre;
    protected int length;
    protected int songID;
    protected String fileType;
    protected String filePath;
    protected Node node;
    protected String playlist;
    
    /**
     * Creates a new TrackView GUI node and returns its controller.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param filePath  the path to the file
     * @param fileType  the type of file that this track maps to
     */
    public TrackView(String title, String artist, String album, String genre,
            Integer length, Integer songID, String filePath, String fileType) {
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.setInfo(title, artist, album, genre, length, songID, filePath, fileType);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        final TrackView me = this;
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = me.startDragAndDrop(TransferMode.ANY);
                
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                if (me.playlist != null) {
                    content.putString(me.playlist);
                } else {
                    content.putHtml(""); // does this for the sake of working
                }
                db.setContent(content);
                Application.draggedObject = me;
                event.consume();
            }
        });
        this.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Application.draggedObject = null;
                event.consume();
            }
        });
    }
    
    public TrackView(TrackView tv) {
        this(tv.title, tv.artist, tv.album, tv.genre, tv.length, tv.songID,
                tv.filePath, tv.fileType);
    }
    
    /**
     * Sets the information of the the TrackView instance to reflect the
     * meta-data of the track. It should in turn call the setGUI method.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param filePath  the path to the file
     * @param fileType  the type of file that this track maps to
     */
    public abstract void setInfo(String title, String artist, String album,
            String genre, int length, int songID, String filePath, String fileType);
    
    /**
     * Sets all of GUI text and configurations to reflect the attributes of the
     * track. This should be called by the setInfo method.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param filePath  the path to the file
     * @param fileType  the type of file that this track maps to
     */
    protected abstract void setGUI(String title, String artist, String album,
            String genre, int length, int songID, String filePath, String fileType);
    
    /*
     * Getter methods
     */
    public String getTitle() {
        return this.title;
    }
    public String getArtist() {
        return this.artist;
    }
    public String getAlbum() {
        return this.album;
    }
    public String getGenre() {
        return this.genre;
    }
    public int getLength() {
        return this.length;
    }
    public int getSongID() {
        return this.songID;
    }
    public String getFileType() {
        return this.fileType;
    }
    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }
}