/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.filter;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public interface Filter {
    
    /**
     * Compares a {@link TrackView} instance to an object and either inserts it
     * accordingly into the container returning true or does not and returns false.
     * @param inserting the TrackView that needs to be inserted into the container
     * @param existing  the existing object to compare the TrackView to
     * @param container the container that inserting should be inserted into
     * @param position  the integer position currently being checked in the container
     * @return          true if the TrackView was inserted and false if it was
     * not inserted
     */
    public boolean compareAndInsert(TrackView inserting, Node existing, 
            ObservableList<Node> container, int position);
    
    /**
     * Returns the name associated with this filter to be displayed in the
     * library comboBox.
     * @return  the name of this filter
     */
    public String getName();
}
