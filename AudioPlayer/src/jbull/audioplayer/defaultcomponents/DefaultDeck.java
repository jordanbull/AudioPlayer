package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import jbull.audioplayer.Deck;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class DefaultDeck extends Deck {

    @FXML
    Label songTitleLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    Button playButton;
    @FXML
    Button nextButton;
    @FXML
    Button prevButton;

    @Override
    protected void setGUI(TrackView track) {
        songTitleLabel.setText(track.getTitle());
    }

    @Override
    protected void attachTimerToProgress(ObservableValue<Number> elapsedMillis, ObservableValue<Number> progress) {
        progressBar.progressProperty().bind(progress);
    }

    @Override
    public URL getFXML() {
        return this.getClass().getResource("DefaultDeck.fxml");
    }
    
    @FXML
    @Override
    public boolean play() {
        return super.play();
    }
    
}
