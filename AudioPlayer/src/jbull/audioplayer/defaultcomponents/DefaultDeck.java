package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import jbull.audioplayer.Deck;
import jbull.audioplayer.TrackView;
import jbull.util.SlidingProgressBar;

/**
 *
 * @author jordan
 */
public class DefaultDeck extends Deck {

    @FXML
    Label songTitleLabel;
    @FXML
    SlidingProgressBar progressBar;
    @FXML
    Button playButton;
    @FXML
    Button nextButton;
    @FXML
    Button prevButton;
    
    ReadOnlyBooleanProperty playing;

    public DefaultDeck() {
        super();
        playing = this.getObservablePlaying();
        playing.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    playButton.setText("||");
                } else {
                    playButton.setText(">");
                }
            }
        });
    }
    
    @Override
    protected void setGUI(TrackView track) {
        songTitleLabel.setText(track.getTitle());
    }

    @Override
    protected void attachTimerToProgress(ObservableValue<Number> elapsedMillis, ObservableValue<Number> progress) {
        progressBar.bind(progress);
        progressBar.setOnSliderChange(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    seek(progressBar.getValue());
                }
            }
        });
    }

    @Override
    public URL getFXML() {
        return this.getClass().getResource("DefaultDeck.fxml");
    }
    
    @FXML
    public void playButtonPressed() {
        if (playing.getValue()) {
            super.pause();
        } else {
            super.play();
        }
    }
    @FXML
    public void nextButtonPressed() {
        next();
    }
    @FXML
    public void prevButtonPressed() {
        prev();
    }

    @Override
    protected void setGUINoTrack() {
        songTitleLabel.setText("");
    }
}
