package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class DefaultTrackView extends TrackView {
    
    @FXML Label titleView;
    @FXML Label albumView;
    @FXML Label artistView;
    @FXML Label genreView;
    @FXML Label lengthView;
    
    public DefaultTrackView(String title, String artist, String album, String
            genre, Integer length, Integer songID, String filePath, String fileType) {
        super(title, artist, album, genre, length, songID, filePath, fileType);
    }
    
    public DefaultTrackView(TrackView tv) {
        super(tv);
    }

    @Override
    public void setInfo(String title, String artist, String album, String genre,
                int length, int songID, String filePath, String fileType) {
        super.title = title;
        super.artist = artist;
        super.album = album;
        super.genre = genre;
        super.length = length;
        super.songID = songID;
        super.filePath = filePath;
        super.fileType = fileType;
        setGUI(title, artist, album, genre, length, songID, filePath, fileType);
    }

    @Override
    public void setGUI(String title, String artist, String album, String genre,
                int length, int songID, String filePath, String fileType) {
        titleView.setText(title);
        artistView.setText(artist);
        albumView.setText(album);
        genreView.setText(genre);
        lengthView.setText(Integer.toString(length));
    }

    @Override
    public URL getFXML() {
        String FXMLname = "DefaultTrackView.fxml";
        return getClass().getResource(FXMLname);
    }
}
