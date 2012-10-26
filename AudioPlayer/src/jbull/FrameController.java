/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author Jordan
 */
public class FrameController implements Initializable {
    @FXML private MenuItem addFiles;
    @FXML private MenuItem addFolder;
    
    private static final String extensions[] = new String[] {"*.mp3"};
    private static final FileChooser.ExtensionFilter musicFilter = new FileChooser.ExtensionFilter("Music and Directories", extensions);
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //TODO
        addFiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().add(musicFilter);
                List<File> files = fc.showOpenMultipleDialog(null);
                for (File file : files) {
                    addFileToLibrary(file);
                }
            }
        });
        
        addFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                DirectoryChooser dc = new DirectoryChooser();
                File dir = dc.showDialog(null);
                if (dir == null)
                    return;
                ArrayList<File> files = getAllFilesInDirectory(dir);
                for (File file : files) {
                    addFileToLibrary(file);
                }
            }
        });
    }
    public void addFileToLibrary(File file) {
        //TODO
        System.out.println(file.getName());
    }
    public ArrayList<File> getAllFilesInDirectory(File f) {
        assert (f.isDirectory());
        File[] files = f.listFiles();
        ArrayList<File> results = new ArrayList<File>();
        for (File file : files) {
            if (file.isDirectory()) {
                ArrayList<File> recur = getAllFilesInDirectory(file);
                for (File fi : recur) {
                    results.add(fi);
                }
            } else {
                for (String ext : extensions) {
                    if (file.getName().endsWith(ext.substring(1)))
                        results.add(file);
                } 
            }
        }
        return results;
    }
}
