/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.filter;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class TitleFilter implements Filter {

    @Override
    public boolean compareAndInsert(TrackView inserting, Node existing, ObservableList<Node> container, int position) {
            if (existing == null) {
                container.add(position, inserting);
                return true;
            }
            switch(inserting.getTitle().compareTo(((TrackView) existing).getTitle())){
                case -1: 
                    container.add(position, inserting);
                    return true;
                case  0:
                    if (inserting.getArtist().compareTo(((TrackView) existing).getArtist()) <= 0) {
                        container.add(position, inserting);
                        return true;
                    } else  {
                        return false;
                    }
                case  1:
                    return false;
            }
            return false;
    }

    @Override
    public String getName() {
        return "Title";
    }
    
}
