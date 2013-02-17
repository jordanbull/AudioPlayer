package jbull.audioplayer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author jordan
 */
public class NewPlaylistDialog extends AnchorPane implements Component {
    
    @FXML TextField nameField;
    @FXML Label message;
    @FXML Button commit;
    Stage stage;
    ArrayList<String> allNames;
    
    public NewPlaylistDialog(ArrayList<String> allNames) {
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.allNames = allNames;
            commit.setText("Create");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void show() {
        this.stage = new Stage();
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler() {
            @Override
            public void handle(Event t) {
                AudioPlayer.stg.getScene().getRoot().setDisable(false);
            }
        });
        AudioPlayer.stg.getScene().getRoot().setDisable(true);
        stage.setScene(new Scene(this));
        stage.setTitle("Create New Playlist");
        stage.show();
    }
    
    @Override
    public URL getFXML() {
        return this.getClass().getResource("RenamePlaylistDialog.fxml");
    }
    
    @FXML protected void cancel() {
        AudioPlayer.stg.getScene().getRoot().setDisable(false);
        this.stage.close();
    }
    @FXML protected void rename() {
        //TODO
        String newName = nameField.getText();
        if (newName == null || newName.equals("")) {
            message.setText("Name cannot be empty.");
        } else if (allNames.contains(newName)) {
            message.setText("This name is taken.");
        } else {
            try {
                int playlistID = Database.Playlists.createPlaylist(newName);
                Playlist.addPlaylistToAllPanes(newName, playlistID);
                Playlist playlist = Application.contentPane.getPlaylistPanes().get(0);
                playlist.setPlaylist(newName);
            } catch (SQLException e) {
                e.printStackTrace();
                //TODO handle user
            }
            AudioPlayer.stg.getScene().getRoot().setDisable(false);
            this.stage.close();
        }
    }
    
    
}
