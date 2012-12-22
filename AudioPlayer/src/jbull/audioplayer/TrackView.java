/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;

/**
 *
 * @author jordan
 */
public abstract class TrackView extends Controller {
    protected String title;
    protected String artist;
    protected String album;
    protected String genre;
    protected int length;
    protected int songID;
    protected String fileType;
    protected Node node;
    
    
    /**
     * Sets the information of the the TrackView instance to reflect the
     * meta-data of the track. It should in turn call the setGUI method.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param fileType  the type of file that this track maps to
     */
    public abstract void setInfo(String title, String artist, String album, String genre, int length, int songID, String fileType);
    
    /**
     * Sets all of GUI text and configurations to reflect the attributes of the
     * track. This should be called by the setInfo method.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param fileType  the type of file that this track maps to
     */
    protected abstract void setGUI(String title, String artist, String album, String genre, int length, int songID, String fileType);
    
    /**
     * Creates a new TrackView GUI node by calling the createTrackView method and returns its controller.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param fileType  the type of file that this track maps to
     * @return          the controller of the newly created TrackView GUI node
     */
    protected static TrackView newTrackView(String title, String artist, String album, String genre, int length, int songID, String fileType) {
        return new TrackView(){
            public void setInfo(String title, String artist, String album, String genre, int length, int songID, String fileType) {}
            public void setGUI(String title, String artist, String album, String genre, int length, int songID, String fileType) {}
            public TrackView createTrackView(String title, String artist, String album, String genre, int length, int songID, String fileType) {return null;}
            public void initialize(URL url, ResourceBundle rb) {}
        }.createTrackView(title, artist, album, genre, length, songID, fileType);
    }
    
    /**
     * Creates a new TrackView GUI node and returns its controller.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param fileType  the type of file that this track maps to
     * @return          the controller of the newly created TrackView GUI node
     */
    protected abstract TrackView createTrackView(String title, String artist, String album, String genre, int length, int songID, String fileType) throws IOException;
}