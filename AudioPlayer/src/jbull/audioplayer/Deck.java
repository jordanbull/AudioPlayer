package jbull.audioplayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import jbull.util.ObservableTimer;

/**
 *
 * @author jordan
 */
public abstract class Deck extends AnchorPane implements Component {
    
    private Playlist playlist;
    private TrackView trackView;
    private Codec codec;
    private boolean playing = false;
    private ObservableTimer timer;
    
    public Deck() {
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.timer = new ObservableTimer() {
                @Override
                public Object onCompletion() {
                    songFinished();
                    return null;
                }
            };
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    protected void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
    
    public boolean load(TrackView track) {
        try {
            String path = Database.Library.getTrack(track.songID).filepath;
            System.out.println(path);
            codec = Codec.getAppropriateCodec(track.fileType);
            codec.load(new URI(path));
            trackView = track;
            setGUI(track);
            setTimer(track.length*1000);//must multiply by 100 since timer takes millis
            attachTimerToProgress(timer.getElapsedMillis(), timer.getProgress());
            return true;
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    protected abstract void setGUI(TrackView track);
    
    public boolean play() {
        if (!playing) {
            if (trackView == null) {
                trackView = playlist.current();
                if (trackView == null) {
                    return false;
                }
            }
            boolean load = load(trackView);
            if (!load) {
                return load;
            }
            codec.play();
            playing = true;
            timer.play();
            return load;
        }
        return true;
    }
    
    protected void setTimer(long length) {
        //TODO
        timer.setLength(length);
        
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    private void songFinished() {
        //TODO
    }
    
    protected abstract void attachTimerToProgress(ObservableValue<java.lang.Number> elapsedMillis, ObservableValue<java.lang.Number> progress);
    
}
