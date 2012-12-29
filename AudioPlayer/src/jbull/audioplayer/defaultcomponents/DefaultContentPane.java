/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import jbull.audioplayer.ContentPane;
import jbull.audioplayer.Library;

/**
 *
 * @author jordan
 */
public class DefaultContentPane extends ContentPane {

    @FXML Library libraryPane;
    @FXML AnchorPane rightPane;
    
    public DefaultContentPane() {
        super();
    }
    @Override
    public URL getFXML() {
        return this.getClass().getResource("DefaultContentPane.fxml");
    }

    @Override
    public Library getLibraryPane() {
        return libraryPane;
    }
    
}
