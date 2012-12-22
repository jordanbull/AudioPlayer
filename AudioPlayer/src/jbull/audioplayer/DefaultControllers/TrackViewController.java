/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.DefaultControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class TrackViewController extends TrackView {
    
    @FXML
    Label titleView;
    @FXML
    Label albumView;
    @FXML
    Label artistView;
    @FXML
    Label genreView;
    @FXML
    Label lengthView;

    @Override
    public void setInfo(String title, String artist, String album, String genre,
                int length, int songID, String fileType) {
        super.title = title;
        super.artist = artist;
        super.album = album;
        super.genre = genre;
        super.length = length;
        super.songID = songID;
        super.fileType = fileType;
        setGUI(title, artist, album, genre, length, songID, fileType);
    }

    @Override
    public void setGUI(String title, String artist, String album, String genre,
                int length, int songID, String fileType) {
        titleView.setText(title);
        artistView.setText(artist);
        albumView.setText(album);
        genreView.setText(genre);
        lengthView.setText(Integer.toString(length));
    }

    @Override
    public TrackView createTrackView(String title, String artist, String album,
                String genre, int length, int songID, String fileType) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Form.fxml"));
        Node node = (Node) loader.load();
        TrackView controller = loader.getController();
        super.node = node;
        controller.setInfo(title, artist, album, genre, length, songID, fileType);
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Do Nothing
    }
}
