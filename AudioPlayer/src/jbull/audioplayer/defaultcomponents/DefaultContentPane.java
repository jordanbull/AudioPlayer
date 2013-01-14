/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.defaultcomponents;

import java.net.URL;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import jbull.audioplayer.ContentPane;
import jbull.audioplayer.Deck;
import jbull.audioplayer.Library;
import jbull.audioplayer.Playlist;

/**
 *
 * @author jordan
 */
public class DefaultContentPane extends ContentPane {

    @FXML Library libraryPane;
    @FXML AnchorPane rightPane;
    @FXML Playlist playlistPane;
    @FXML Deck deckPane;
    
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

    @Override
    public ArrayList<Playlist> getPlaylistPanes() {
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        playlists.add(playlistPane);
        return playlists;
    }

    @Override
    public ArrayList<Deck> getDeckPanes() {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        decks.add(deckPane);
        return decks;
    }
    
}
