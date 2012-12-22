/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import jbull.audioplayer.filter.Filter;

/**
 *
 * @author jordan
 */
public interface Library {
    /**
     * Returns the number of tracks in the library.
     * @return  the number of tracks in the library
     */
    public int getNumTracks();
    
    /**
     * Sorts all of the songs in the library according to the specified {@link Filter}.
     * The filter is also responsible for inserting all of the tracks into the
     * library.
     * @param filter    a {@link Filter} for the tracks to be sorted on
     */
    public void sort(Filter filter);
    /**
     * Empties out all of the {@link TrackView}s from the GUI of the library.
     */
    public void empty();
}
