/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author jordan
 */
public class TrackContextMenu extends ContextMenu {
    protected TrackContextMenu(final TrackView track) {
        super();
        ObservableList<MenuItem> items = this.getItems();
        
        MenuItem item = new MenuItem("Remove Track from Library");
        item.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                Application.contentPane.getLibraryPane().removeTrackFromLibrary(track);
            }
        });
        items.add(item);
        
        if (track.isInPlaylist()) { //indiates the trackview in a playlist panel
            item = new MenuItem("Remove Track from "+track.getPlaylistInfo().getName());
            item.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    track.getPlaylistInfo().getPlaylist().removeTrack(track);
                }
            });
            items.add(item);
        }
    }
    protected void show(MouseEvent event) {
        this.show(AudioPlayer.stg, event.getScreenX(), event.getScreenY());
    }
}
