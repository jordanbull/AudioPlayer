/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import jbull.Deck;
import jbull.GUIController;

/**
 *
 * @author Jordan
 */
public class DefaultDeckController implements Initializable {
    @FXML private Button play;
    @FXML private Button next;
    @FXML private Button prev;
    
    
    @Override
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        Deck.PlayPauseHandler playPauseHandler = new Deck.PlayPauseHandler(play) {
            @Override
            public void play() {
                    playPause.setText("Pause");
            }
            @Override
            public void pause() {
                    playPause.setText("Play");
            }
        };
        Deck d = new Deck(play, next, prev, playPauseHandler);
        GUIController.addDeckControl(d);
    }
    
}
