package jbull.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author jordan
 */
public abstract class ObservableTimer<T> {

    private boolean playing = false;
    private long length = 0;
    private final long periodMS = 500; //how often to update progress in milliseconds
    private long numCycles = 0;
    private IntegerProperty cyclesThusFar = new SimpleIntegerProperty(0);
    private DoubleProperty progress = new SimpleDoubleProperty(0);
    private double progressPerCycle = 0.0;
    private Timeline timeline;

    public void setLength(long length) {
        this.length = length;
        numCycles = length/periodMS;
        progressPerCycle = 1.0/numCycles;
        cyclesThusFar.set(0);
        progress.set(0);
        timeline = new Timeline(new KeyFrame(Duration.millis(periodMS), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cyclesThusFar.set(cyclesThusFar.get()+1);
                progress.set(progress.get()+progressPerCycle);
                if (cyclesThusFar.get() >= numCycles) {
                    timeline.stop();
                    cyclesThusFar.set(0);
                    progress.set(0);
                    onCompletion();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void play() {
        playing = true;
        timeline.play();
    }

    public void pause() {
        playing = false;
        timeline.pause();
    }

    public void seek(int cycleNum) {
        timeline.pause();
        cyclesThusFar.set(cycleNum);
        progress.set(cycleNum*progressPerCycle);
        if (playing)
            timeline.play();
    }

    public long getTotalCycles() {
        return numCycles;
    }
    public ReadOnlyIntegerProperty getNumCycles() {
        return cyclesThusFar;
    }
    public ReadOnlyDoubleProperty getProgress() {
        return progress;
    }
    public ObservableValue<Number> getElapsedMillis() {
        return cyclesThusFar.multiply(periodMS);
    }
    public ObservableValue<Number> getElapsedSeconds() {
        return cyclesThusFar.multiply(periodMS/1000);
    }

    public abstract T onCompletion();
}
