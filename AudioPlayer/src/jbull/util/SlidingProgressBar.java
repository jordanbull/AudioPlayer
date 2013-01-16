/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

/**
 *
 * @author jordan
 */
public class SlidingProgressBar extends StackPane {
    ProgressBar progressBar;
    Slider slide;
    DoubleProperty progressProperty = new SimpleDoubleProperty(0.0);
    //A dummy change listener is instantiated to avoid null pointer errors
    ChangeListener<Boolean> slideChangeListener = new ChangeListener<Boolean>() {public void changed(ObservableValue ov, Boolean t, Boolean t1) {}};
    
    public SlidingProgressBar() {
        super();
        this.getStylesheets().add(this.getClass().getResource("SlidingProgressBar.css")
   .toExternalForm());
        ObservableList<Node> children = this.getChildren();
        progressBar = new ProgressBar();
        children.add(progressBar);
        slide = new Slider();
        slide.setMax(1.0);
        slide.setMin(0.0);
        children.add(slide);
        slide.valueChangingProperty().addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                slideChangeListener.changed(ov, !oldValue, !newValue);
                if (newValue) {
                    slide.valueProperty().unbind();
                } else {
                    slide.valueProperty().bind(progressProperty());
                }
                
            }
        });
        //binds the progress to always indicate the percentage of the sliders value
        slide.valueProperty().bind(progressProperty);
        //progressProperty.bindBidirectional(slide.valueProperty());
        progressBar.progressProperty().bind(slide.valueProperty());
        this.setMaxHeight(slide.getHeight());
        progressBar.maxWidthProperty().bind(this.widthProperty().subtract(5));
        progressBar.prefWidthProperty().bind(this.widthProperty().subtract(5));
        progressBar.minWidthProperty().bind(this.widthProperty().subtract(5));
        slide.maxWidthProperty().bind(this.widthProperty());
        slide.prefWidthProperty().bind(this.widthProperty());
        slide.minWidthProperty().bind(this.widthProperty());
    }
    
    public void bind(ObservableValue<Number> doubleProperty) {
        progressProperty.bind(doubleProperty);
    }
    
    public ReadOnlyDoubleProperty progressProperty() {
        return progressProperty;
    }
    
    /**
     * Sets the change listener called when the slider is manually changed. The
     * boolean of the change listener is true if the slide is completed and has
     * been released and false on the start of the change.
     * @param changeListener    the change listener that is called on manual
     * change of the slider
     */
    public void setOnSliderChange(ChangeListener<Boolean> changeListener) {
        this.slideChangeListener = changeListener;
    }
    
    public Double getValue() {
        return slide.valueProperty().get();
    }
}
