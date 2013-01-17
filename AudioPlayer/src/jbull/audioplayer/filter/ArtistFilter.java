package jbull.audioplayer.filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
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
        try {
            ArrayList<Database.Library.Track> tracks = Database.Library.getAllTracks("artist, album, title");
            TreeItem<Node> root = new TreeItem<Node>();
            TreeView tree = new TreeView(root);
            tree.setShowRoot(false);
            String artist = null;
            String album = null;
            TreeItem<Node> artistNode = null;
            TreeItem<Node> albumNode = null;
            for (Database.Library.Track track : tracks) {
                if (!track.artist.equals(artist)) {
                    if (artist != null) {
                        root.getChildren().add(artistNode);
                    }
                    artist = track.artist;
                    if (artist.equals("")) {
                        artist = "<no artist>";
                    }
                    artistNode = createArtistNode(artist);
                    album = null;
                }
                if (!track.album.equals(album)) {
                    album = track.album;
                    if (album.equals("")) {
                        album = "<no album>";
                    }
                    albumNode = appendAlbumNode(artistNode, album);
                }
                appendTrack(albumNode, Application.createTrackView(track));
            }
            root.getChildren().add(artistNode);
            library.appendItem(tree);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TreeItem<Node> createArtistNode(String artist) {
        TreeItem<Node> node = new TreeItem<Node>(new Text(artist));
        return node;
    }
    private TreeItem<Node> appendAlbumNode(TreeItem<Node> artist, String album) {
        TreeItem<Node> albumNode = new TreeItem<Node>(new Text(album));
        artist.getChildren().add(albumNode);
        return albumNode;
    }
    private void appendTrack(TreeItem<Node> albumNode, TrackView track) {
        albumNode.getChildren().add(new TreeItem<Node>(track));
    }
    
    @Override
    public String getName() {
        return "Artist";
    }
    
}
