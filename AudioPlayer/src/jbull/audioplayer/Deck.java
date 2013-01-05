/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author jordan
 */
public abstract class Deck extends AnchorPane implements Component {
    
    private Playlist playlist;
    private TrackView trackView;
    private MediaPlayer mediaPlayer;
    
    public Deck(Playlist playlist) {
        this.playlist = playlist;
    }
    
    public boolean load(TrackView track) {
        try {
            String path = Database.Library.getTrack(track.songID).filepath;
            mediaPlayer = new MediaPlayer(new Media(path));
            trackView = track;
            setGUI(track);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    
    protected abstract void setGUI(TrackView track);
    
    public boolean play() {
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
        mediaPlayer.play();
        startPlayTimer(trackView.length);
        return load;
    }
    
    protected void startPlayTimer(final int length) {
        /*Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                final long interval = 500; // interval of updates in milliseconds
                final long numIntervals = length*1000;
                final Task task = this;
                new Timer().schedule(new TimerTask() {
                    long time = 0;
                    @Override
                    public void run() {
                        time += interval;
                        task.updateProgress(time, time);
                    }
                }, interval, interval);
                return null;
            }
        };
        setTimerToSong(task.progressProperty());*/
    }
    
    protected abstract void setTimerToSong(ReadOnlyDoubleProperty progress);
    
}
