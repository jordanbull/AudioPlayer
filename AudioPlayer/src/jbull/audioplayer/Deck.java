package jbull.audioplayer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private Codec codec;
    private long lengthMS = 0;
    private SimpleBooleanProperty playing = new SimpleBooleanProperty(false);
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
        setGUINoTrack();
    }
    
    protected void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
    
    public boolean load(TrackView track) {
        if (codec != null) {
            codec.destroy();
            codec = null;
        }
        try {
            String path = Database.Library.getTrack(track.songID).filepath;
            codec = Codec.getAppropriateCodec(track.fileType);
            codec.load(new URI(path));
            lengthMS = track.length * 1000l;
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
        boolean load = true;
        if (!playing.getValue()) {
            if (codec == null) {
                TrackView trackView = playlist.current();
                if (trackView == null) {
                    return false;
                }
                load = load(trackView);
            }
            if (!load) {
                return load;
            }
        }
        codec.play();
        timer.play();
        playing.set(true);
        return load;
    }
    
    public void pause() {
        if (playing.getValue()) {
            if (codec != null) {
                codec.pause();
            }
            timer.pause();
            playing.set(false);
        }
    }
    
    public void next() {
        if (codec != null) {
            codec.destroy();
        }
        codec = null;
        if (!playlist.hasNext()) {
            pause();
            timer.seek(0);
            setGUINoTrack();
            playlist.next();
        } else {
            load(playlist.next());
            if (isPlaying()) {
                play();
            } else {
                pause();
            }
        }
    }
    
    public void prev() {
        final long MAX_TIME_FOR_PREV_SONG = 3;
        if ((Double)timer.getElapsedSeconds().getValue() >= MAX_TIME_FOR_PREV_SONG) { //restart song
            boolean playing = isPlaying();
            pause();
            seek(0);
            if (playing) {
                play();
            }
        } else { // go to prev song
            if (codec != null) {
                codec.destroy();
            }
            codec = null;
            if (playlist.hasPrev()) {
                load(playlist.prev());
                if (isPlaying()) {
                    play();
                } else {
                    pause();
                }
            } else { //no previous track
                playlist.prev();
                pause();
                timer.seek(0);
                setGUINoTrack();
            }
        }
    }
    
    public void seek(long millis) {
        codec.pause();
        timer.pause();
        codec.seek(millis/1000);
        timer.seek(millis);
        if (isPlaying()) {
            play();
        }
    }
    
    public void seek(double progress) {
        seek(Math.round(progress * lengthMS));
    }
    
    protected void setTimer(long length) {
        //TODO
        timer.pause();
        timer.seek(0);
        timer.setLength(length);
        timer.setPeriod(100);
    }
    
    public boolean isPlaying() {
        return playing.getValue();
    }
    
    private void songFinished() {
        next();
    }
    
    private long getSongLnegth() {
        return lengthMS;
    }
    
    protected ReadOnlyBooleanProperty getObservablePlaying() {
        return playing;
    }
    
    protected abstract void attachTimerToProgress(ObservableValue<java.lang.Number> elapsedMillis, ObservableValue<java.lang.Number> progress);
    
    protected abstract void setGUINoTrack();
}
