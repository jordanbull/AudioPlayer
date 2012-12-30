package jbull.audioplayer.filter;

import jbull.audioplayer.Library;

/**
 *
 * @author jordan
 */
public interface Filter {
    
    /**
     * Calls the database to get all songs and sorts them according to the
     * filter and then inserts the sorted items into the library.
     * @param library   the library that the sorted songs should be inserted into
     */
    public void sortAndInsert(Library library);
    
    /**
     * Returns the name associated with this filter to be displayed in the
     * library comboBox.
     * @return  the name of this filter
     */
    public String getName();
}
