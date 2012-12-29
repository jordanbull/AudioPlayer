package jbull.audioplayer;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
     * @return          the controller of the newly created TrackView GUI node
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
}