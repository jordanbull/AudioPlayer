/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.filter;

import javafx.scene.Node;
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
     * @return          true if the TrackView was inserted and false if it was
     * not inserted
     */
    public boolean compareAndInsert(TrackView inserting, Object existing, Node container);
}
