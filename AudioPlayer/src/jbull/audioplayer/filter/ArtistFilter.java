/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import jbull.audioplayer.Application;
import jbull.audioplayer.Database;
import jbull.audioplayer.Library;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class ArtistFilter implements Filter {

    @Override
    public void sortAndInsert(Library library) {
        Class trackViewClass = Application.getTrackViewClass();
        try {
            Constructor<TrackView> constr = trackViewClass.getDeclaredConstructor(String.class, String.class, String.class, String.class,
                Integer.class, Integer.class, String.class, String.class);
            ArrayList<Database.Library.Track> tracks = Database.Library.getAllTracks("artist, album, title");
            String artist = null;
            String album = null;
            TreeView<TrackView> artistNode = null;
            TreeItem<TrackView> albumNode = null;
            for (Database.Library.Track track : tracks) {
                System.out.println(track.artist);
                if (track.artist != artist) {
                    if (artist != null) {
                        library.appendItem(artistNode);
                    }
                    artist = track.artist;
                    artistNode = createArtistNode(artist);
                    album = null;
                }
                if (track.album != album) {
                    album = track.album;
                    albumNode = appendAlbumNode(artistNode, album);
                }
                appendTrack(albumNode, constr.newInstance(track.asObjectArray()));
            }
            library.appendItem(artistNode);
        } catch (SQLException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
        }
    }

    private TreeView createArtistNode(String artist) {
        TreeItem<TrackView> root = new TreeItem<TrackView>(null, new Label(artist));
        TreeView view = new TreeView(root);
        return view;
    }
    private TreeItem<TrackView> appendAlbumNode(TreeView<TrackView> artist, String album) {
        TreeItem<TrackView> albumNode = new TreeItem<TrackView>(null, new Label(album));
        artist.getRoot().getChildren().add(albumNode);
        return albumNode;
    }
    private void appendTrack(TreeItem<TrackView> albumNode, TrackView track) {
        albumNode.getChildren().add(new TreeItem<TrackView>(track));
    }
    
    @Override
    public String getName() {
        return "Album";
    }
    
}
