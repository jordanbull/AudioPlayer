package jbull.audioplayer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import jbull.audioplayer.codec.JavafxMediaCodec;
import jbull.audioplayer.defaultcomponents.DefaultTrackView;
import jbull.audioplayer.filter.ArtistFilter;
import jbull.audioplayer.filter.TitleFilter;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.Event;

/**
 *
 * @author jordan
 */
public class Launch extends AnchorPane implements Component {
    
    @FXML ProgressBar progressBar;
    @FXML Label loadInfo;
    
    
    private static final int PLUGIN_VALUE = 5;
    private static final int SONG_VALUE = 1;
    private static final int SETTING_VALUE = 2;
    Application application;
    
    Connection connection;
    
    protected Launch(Application application) {
        this.application = application;
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    @Override
    public URL getFXML() {
        return this.getClass().getResource("Launch.fxml");
    }
    
    protected void launch() {
        try {
            Database.connectToDatabase();
            Database.Library.createTracksTable();
            Database.Playlists.createPlaylistsTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        
        LaunchTask task = new LaunchTask();
        ReadOnlyDoubleProperty prog = task.progressProperty();
        progressBar.progressProperty().bind(prog);
        loadInfo.textProperty().bind(task.messageProperty());
        task.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event t) {
                AudioPlayer.stg.setScene(new Scene(application));
                AudioPlayer.stg.show();
            }
        });
        new Thread(task).start();
    }
    
    class LaunchTask extends Task {

        private long totalItems = 0;
        private int progress = 0;
        
        public LaunchTask() {
            super();
            updateProgress(0, 10000);
        }
        
        @Override
        protected Object call() throws Exception {
            totalItems = (PLUGIN_VALUE *getNumPlugins()) +
                (SONG_VALUE * getNumSongs()) + (SETTING_VALUE * getNumSettings());
            initializationTasks();
            this.updateMessage("Launching Plugins");
            launchPlugins();
            this.updateMessage("Filling Library");
            fillLibrary();
            this.updateMessage("Filling Playlists");
            fillPlaylists();
            this.updateMessage("Restoring Default Settings");
            defaultSettings();
            return null;
        }
        
        private void initializationTasks() {
            //TODO
            addDefaultCodecs();
            setDefaultTrackView();
            addFilters();
        }

        private void addDefaultCodecs() {
            Codec.addCodec(new JavafxMediaCodec());
        }
        private void setDefaultTrackView() {
            //TODO
            Application.setTrackViewClass(DefaultTrackView.class);
        }
        private void addFilters() {
            //TODO
            Library lib = Application.contentPane.getLibraryPane();
            lib.removeFilters();
            lib.addFilter(new TitleFilter());
            lib.addFilter(new ArtistFilter());
            lib.setFilter(0);
        }

        private void launchPlugins() {
            //TODO
        }

        private void fillLibrary() {
            Library lib = Application.contentPane.getLibraryPane();
            lib.sort(lib.getFilter()); //TODO get default filter
        }

        private void fillPlaylists() {
            ArrayList<Playlist> playlists = Application.contentPane.getPlaylistPanes();
            ArrayList<Database.Playlists.Playlist> dbps = null;
            try {
                 dbps = Database.Playlists.getAllPlaylists();
            } catch (SQLException ex) {
                Logger.getLogger(Launch.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < playlists.size(); i++) {
                Playlist p = playlists.get(i);
                p.removeAllPlaylists();
                for (int j = 0; j < dbps.size(); j++) {
                    p.addPlaylist(dbps.get(j).name, dbps.get(j).playlistID);
                }
                p.setPlaylist("new playlist"); //TODO handle actual default playlists and load the correspondingsongs
            }

        }

        private void defaultSettings() {
            //TODO
        }

        private long getNumPlugins() {

            //TODO
            return 10000*PLUGIN_VALUE;
        }

        private long getNumSongs() {
            //TODO
            return 0;
        }

        private int getNumSettings() {
            //TODO
            return 0;
        }
    }
    
    
}
