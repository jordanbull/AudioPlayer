/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author jordan
 */
public class Application extends AnchorPane {

    @FXML MenuBar menu;
    @FXML BorderPane contentPane;
    
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
    private URL getContentLayout() {
        //TODO select layout desired
        return this.getClass().getResource("defaultcomponents/DefaultContentPane.fxml");
    }
    private void setContentPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getContentLayout());
        try {
            Node node = (Node) fxmlLoader.load();
            contentPane.setCenter(node);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
