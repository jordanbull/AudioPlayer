package jbull.audioplayer;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author jordan
 */
public abstract class TrackView extends AnchorPane implements Component {
    protected String title;
    protected String artist;
    protected String album;
    protected String genre;
    protected int length;
    protected int songID;
    protected String fileType;
    protected String filePath;
    private PlaylistInfo playlistInfo = null;
    
    /**
     * Creates a new TrackView GUI node and returns its controller.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param filePath  the path to the file
     * @param fileType  the type of file that this track maps to
     */
    public TrackView(String title, String artist, String album, String genre,
            Integer length, Integer songID, String filePath, String fileType) {
        FXMLLoader fxmlLoader = new FXMLLoader(getFXML());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.setInfo(title, artist, album, genre, length, songID, filePath, fileType);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        setOnDragDetected();
        setOnDragDone();
        setOnDragOver();
        setOnDragCompleted();
    }
    private void setOnDragDetected() {
        final TrackView me = this;
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = me.startDragAndDrop(TransferMode.ANY);
                
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                if (me.isInPlaylist()) {
                    content.putString(me.getPlaylistInfo().getName());
                } else {
                    content.putHtml(""); // does this for the sake of working
                }
                db.setContent(content);
                Application.draggedObject = me;
                event.consume();
            }
        });
    }
    private void setOnDragDone() {
        final TrackView me = this;
        this.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Application.draggedObject = null;
                if (me.isInPlaylist()) {
                    me.getPlaylistInfo().playlist.removeTrack(me);
                }
                event.consume();
            }
        });
    }
    private void setOnDragOver() {
        this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (Application.draggedObject instanceof TrackView) {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                }
            }
        });
    }
    private void setOnDragCompleted() {
        final TrackView me = this;
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (Application.draggedObject instanceof TrackView) {
                    TrackView draggedTrack = (TrackView) Application.draggedObject;
                    if (!me.isInPlaylist()) { //dragged to a library track
                        //do nothing
                        return;
                    } else if (draggedTrack.isInPlaylist()) { //moved from a playlist
                        if (draggedTrack.getPlaylistInfo().getPlaylist()==(me.getPlaylistInfo().getPlaylist())) {//moved from this playlist
                            
                            event.acceptTransferModes(TransferMode.MOVE);
                            TrackView trackView = Application.createTrackView(draggedTrack);
                            Playlist playlist = me.getPlaylistInfo().getPlaylist();
                            playlist.addTrack(trackView, me.getPlaylistInfo().getPosition());
                        } else { //moved from another playlist
                            //TODO
                        }
                    } else { // not dragged from playlist
                        event.acceptTransferModes(TransferMode.COPY);
                        TrackView trackView = Application.createTrackView(draggedTrack);
                        me.getPlaylistInfo().getPlaylist().addTrack(trackView, me.getPlaylistInfo().getPosition());
                    }
                    event.consume();
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(true);
             }
        });
    }
    
    public TrackView(TrackView tv) {
        this(tv.title, tv.artist, tv.album, tv.genre, tv.length, tv.songID,
                tv.filePath, tv.fileType);
    }
    
    /**
     * Sets the information of the the TrackView instance to reflect the
     * meta-data of the track. It should in turn call the setGUI method.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param filePath  the path to the file
     * @param fileType  the type of file that this track maps to
     */
    public abstract void setInfo(String title, String artist, String album,
            String genre, int length, int songID, String filePath, String fileType);
    
    /**
     * Sets all of GUI text and configurations to reflect the attributes of the
     * track. This should be called by the setInfo method.
     * @param title     the title of the track
     * @param artist    the artist of the track
     * @param album     the album the track is on
     * @param genre     the genre of the track
     * @param length    the length of the track in seconds
     * @param songID    the ID number of the track in the application's database
     * @param filePath  the path to the file
     * @param fileType  the type of file that this track maps to
     */
    protected abstract void setGUI(String title, String artist, String album,
            String genre, int length, int songID, String filePath, String fileType);
    
    /*
     * Getter methods
     */
    public String getTitle() {
        return this.title;
    }
    public String getArtist() {
        return this.artist;
    }
    public String getAlbum() {
        return this.album;
    }
    public String getGenre() {
        return this.genre;
    }
    public int getLength() {
        return this.length;
    }
    public int getSongID() {
        return this.songID;
    }
    public String getFileType() {
        return this.fileType;
    }
    public void setPlaylist(Playlist playlist, int position) {
        this.playlistInfo = new PlaylistInfo(playlist, position);
    }
    public PlaylistInfo getPlaylistInfo() {
        return playlistInfo;
    }
    public boolean isInPlaylist() {
        return playlistInfo != null;
    }
    
    public static class PlaylistInfo {
        private Playlist playlist;
        private int position;
        
        public PlaylistInfo(Playlist playlist, int position) {
            this.playlist = playlist;
            this.position = position;
        }
        public String getName() {
            return this.playlist.getName();
        }
        public int getPosition() {
            return this.position;
        }
        protected void setPosition(int position) {
            this.position = position;
        }
        protected Playlist getPlaylist() {
            return this.playlist;
        }
    }
}