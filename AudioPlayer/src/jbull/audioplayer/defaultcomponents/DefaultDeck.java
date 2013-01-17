package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML ImageView buttonsImageView;
    
    ReadOnlyBooleanProperty playing;
    

    public DefaultDeck() {
        super();
        playing = this.getObservablePlaying();
        playing.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                //TODO
            }
        });
        /*ImageView playImg = new ImageView(new Image(this.getClass().getResource("playbutton.svg").toExternalForm()));
        playImg.fitWidthProperty().bind(playButton.widthProperty());
        playImg.fitHeightProperty().bind(playButton.heightProperty());
        playButton.setGraphic(playImg);*/
        //playButton.getStylesheets().add(this.getClass().getResource("playbutton-style.css").toExternalForm());
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
    public void playPause() {
        if (playing.getValue()) {
            super.pause();
        } else {
            super.play();
        }
        playPauseHover();
    }
    @FXML protected void playPausePressed() {
        setImage("playpressed.png");
    }
    @FXML protected void playPauseHover() {
        setImage("playhover.png");
    }
    @FXML
    @Override
    public void next() {
        super.next();
        nextHover();
    }
    @FXML protected void nextPressed() {
        setImage("nextpressed.png");
    }
    @FXML protected void nextHover() {
        setImage("nexthover.png");
    }
    @FXML
    @Override
    public void prev() {
        super.prev();
        prevHover();
    }
    @FXML protected void prevPressed() {
        setImage("prevpressed.png");
    }
    @FXML protected void prevHover() {
        setImage("prevhover.png");
    }
    @FXML protected void normal() {
        setImage("nomouse.png");
    }

    @Override
    protected void setGUINoTrack() {
        songTitleLabel.setText("");
    }
    
    private void setImage(String imgName) {
        String folder = "playbuttonshowing/";
        if (isPlaying()) {
            folder = "pausebuttonshowing/";
        }
        buttonsImageView.setImage(new Image(DefaultDeck.class.getResource(folder+imgName).toExternalForm()));
    }
}
