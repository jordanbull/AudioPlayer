package jbull.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
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
    private LongProperty length = new SimpleLongProperty(0l);
    private LongProperty periodMS = new SimpleLongProperty(500); //how often to update progress in milliseconds
    private LongProperty numCycles = new SimpleLongProperty(0);
    private LongProperty cyclesThusFar = new SimpleLongProperty(0);
    private DoubleProperty progress = new SimpleDoubleProperty(0);
    private DoubleProperty progressPerCycle = new SimpleDoubleProperty(0.0);
    private Timeline timeline;

    public ObservableTimer() {
        setLength(0);
        numCycles.bind(this.length.divide(periodMS));
        progressPerCycle.bind(new SimpleDoubleProperty(1.0).divide(numCycles));
    }
    
    public void setLength(long length) {
        this.length.set(length);
        cyclesThusFar.set(0);
        progress.set(0);
        timeline = new Timeline(new KeyFrame(Duration.millis(periodMS.doubleValue()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cyclesThusFar.set(cyclesThusFar.get()+1);
                progress.set(progress.get()+progressPerCycle.get());
                if (cyclesThusFar.get() >= numCycles.get()) {
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

    public void seek(long millis) {
        timeline.pause();
        cyclesThusFar.set(millis/periodMS.get());
        progress.set(cyclesThusFar.getValue()*progressPerCycle.getValue());
        if (playing)
            timeline.play();
    }

    public long getTotalCycles() {
        return numCycles.get();
    }
    public ReadOnlyLongProperty getNumCycles() {
        return cyclesThusFar;
    }
    public ReadOnlyDoubleProperty getProgress() {
        return progress;
    }
    public ObservableValue<Number> getElapsedMillis() {
        return cyclesThusFar.multiply(periodMS);
    }
    public ObservableValue<Number> getElapsedSeconds() {
        return cyclesThusFar.multiply(periodMS.get()/1000.0);
    }
    public void setPeriod(long periodMS) {
        this.periodMS.set(periodMS);
    }
    public long getPeriod() {
        return this.periodMS.get();
    }

    public abstract T onCompletion();
}
