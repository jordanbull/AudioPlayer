/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Jordan
 */
public class Deck {
    
    protected PlayPauseHandler playPauseHandler = null;
    public Deck(Button play, Button pause, Button next, Button prev) {
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                pauseButtonPress();
            }
        });
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                playButtonPress();
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                nextButtonPress();
            }
        });
        prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                prevButtonPress();
            }
        });
    }
    public Deck(Button play, Button next, Button prev, PlayPauseHandler playPauseHandler) {
        this.playPauseHandler = playPauseHandler;
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                playPauseButtonPress();
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                nextButtonPress();
            }
        });
        prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                prevButtonPress();
            }
        });
        this.playPauseHandler.deck = this;
    }
    void play() {
        //TODO
        System.out.println("play");
    }
    void pause() {
        //TODO
        System.out.println("pause");
    }
    void next() {
        //TODO
        System.out.println("next");
    }
    void prev() {
        //TODO
        System.out.println("prev");
    }
    private void playPauseButtonPress() {
        playPauseHandler.defaultRun();
    }
    private void playButtonPress() {
        play();
    }
    private void pauseButtonPress() {
        pause();
    }
    private void nextButtonPress() {
        next();
    }
    private void prevButtonPress() {
        prev();
    }
    public static abstract class PlayPauseHandler {
        public Button playPause;
        public Deck deck;
        public boolean stateIsPlay = true;
        public PlayPauseHandler(Button playPause) {
            this.playPause = playPause;
        }
        private void defaultRun() {
            if (stateIsPlay) {
                play();
                deck.playButtonPress();
                stateIsPlay = false;
            } else {
                pause();
                deck.pauseButtonPress();
                stateIsPlay = true;
            }
        }
        public abstract void play();
        public abstract void pause();
    }
}
