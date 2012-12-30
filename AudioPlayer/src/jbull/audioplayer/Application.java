package jbull.audioplayer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import jbull.audioplayer.Database.Library.Track;
import jbull.audioplayer.defaultcomponents.DefaultContentPane;

/**
 *
 * @author jordan
 */
public class Application extends AnchorPane {

    @FXML MenuBar menu;
    @FXML BorderPane center;
    
    static ContentPane contentPane;
    
    private static Class trackViewClass;
    private static Constructor trackViewObjConstr;
    private static Constructor trackViewTrackViewConstr;
    protected static Object draggedObject;
    
    protected Application() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Application.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            setContentPane();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    private ContentPane getDefaultContentPane() {
        //TODO select layout desired
        return new DefaultContentPane();
    }
    private void setContentPane() {
        contentPane = getDefaultContentPane();
        center.setCenter(contentPane);
    }
    
    protected ContentPane getContentPane() {
        return this.contentPane;
    }
    
    protected void addTracksToLibrary(List<File> files) {
        HashMap<String, ArrayList<URI>> addResults = new HashMap<String, ArrayList<URI>>();
        addResults.put(Library.ERROR, new ArrayList<URI>());
        addResults.put(Library.EXISTS, new ArrayList<URI>());
        addResults.put(Library.SUCCESS, new ArrayList<URI>());
        for (File file : files) {
            URI uri = file.toURI();
            addResults.get(Library.addTrackToLibrary(uri)).add(uri);
        }
        //TODO handle add Results to display effects of trying to add the files.
        //System.out.println(addResults);
        
        Library lib = contentPane.getLibraryPane();
        lib.sort(lib.getFilter());
    }
    
    public static Class getTrackViewClass() {
        return trackViewClass;
    }
    
    @FXML
    protected void addSongs() {
        FileChooser fileChooser = new FileChooser();
        ObservableList<ExtensionFilter> filters = fileChooser.getExtensionFilters();
        filters.clear();
        List<String> l = Codec.getSupportedAudioFormats();
        ArrayList<String> formats = new ArrayList<String>(l.size());
        for (int i = 0; i < l.size(); i++) {
            formats.add("*."+l.get(i));
        }
        filters.add(new ExtensionFilter("Supported Audio Files", formats));
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        if (files != null) {
            addTracksToLibrary(files);
        }
    }
    
    @FXML
    protected void addDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        ArrayList<File> files = new ArrayList<File>();
        LinkedList<File> queue = new LinkedList<File>();
        queue.add(directory);
        List supportedAudioFormats = Codec.getSupportedAudioFormats();
        while (!queue.isEmpty()) {
            File dir = queue.pop();
            File[] fs = dir.listFiles();
            for (File f : fs) {
                if (f.isDirectory()) {
                    queue.add(f);
                } else if (supportedAudioFormats.contains(
                    f.getName().substring(f.getName().lastIndexOf('.') + 1))) {
                    files.add(f);
                }
            }
        }
        if (!files.isEmpty()) {
            addTracksToLibrary(files);
        }
    }
    
    public static TrackView createTrackView(TrackView track) {
        try {
            return (TrackView) trackViewTrackViewConstr.newInstance(track);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static TrackView createTrackView(Track track) {
        try {
            return (TrackView) trackViewObjConstr.newInstance(track.asObjectArray());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    protected static void setTrackViewClass(Class trackView) {
        try {
            trackViewClass = trackView;
            trackViewObjConstr = trackViewClass.getDeclaredConstructor(String.class, String.class, String.class, String.class,
                        Integer.class, Integer.class, String.class, String.class);
            trackViewTrackViewConstr = trackViewClass.getDeclaredConstructor(TrackView.class);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
