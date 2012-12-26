/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jordan
 */
public class DatabaseTrackTests {
    
    static Connection conn;
    
    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        conn = Database.connectToDatabase();
    }
    
    @After
    public void tearDown() throws SQLException {
        Statement s = conn.createStatement();
        s.executeUpdate("DROP ALL OBJECTS DELETE FILES");
        conn.close();
    }
    
    @Test
    public void testConnection() {
        assertTrue(conn != null);
    }
    
    @Test
    public void testCreateTable() throws SQLException {
        Database.createTableIfNotExists("testingtable", "field1 VARCHAR");
        Database.tableExists("testingtable");
    }
    
    /*
     * Tests the createPlaylistsTable() and createTracksTable() methods. Calls
     * both methods and makes sure that the Playlists, Tracks, and PlaylistSongs
     * tables are created. Also makes sure that playlists contains the entry,
     * "new playlist".
     */
    @Test
    public void testCreatePlaylistsAndTracksTables() throws SQLException {
        Database.Library.createTracksTable();
        Database.Playlists.createPlaylistsTable();
        ResultSet results = conn.getMetaData().getTables(conn.getCatalog(), null, "Playlists" , null);
        results.next();
        assertTrue(results.getString("TABLE_NAME").equals("Playlists"));
        results = conn.getMetaData().getTables(conn.getCatalog(), null, "Tracks" , null);
        results.next();
        assertTrue(results.getString("TABLE_NAME").equals("Tracks"));
        results = conn.getMetaData().getTables(conn.getCatalog(), null, "PlaylistSongs" , null);
        results.next();
        assertTrue(results.getString("TABLE_NAME").equals("PlaylistSongs"));
        ArrayList<Database.Playlists.Playlist> playlists = Database.Playlists.getAllPlaylists();
        assertTrue(playlists.size() == 1);
        assertTrue(playlists.get(0).name.equals("new playlist"));
    }
    
    @Test
    public void testAddEditRemoveTrackInLibrary() throws SQLException {
        Database.Library.createTracksTable();
        Database.Playlists.createPlaylistsTable();
        Database.Library.insertTrack("title1", "artist1", null, "genre1", 90, "mp3", "moreThanFourChars", "filepath");
        Database.Library.Track track1 = Database.Library.getTrack(1);
        assertTrue(track1.title.equals("title1")); //ensures that the track was added and the getTrack works.
        assertTrue(track1.album.equals("")); // ensures null fields are changed to empty strings
        assertTrue(track1.year.equals("more")); // ensures that too long of strings are cut
        
        Database.Library.insertTrack("title2", "artist2", null, null, 90, null, null, "fp2");
        Database.Library.insertTrack("title1", "a", null, null, 90, null, null, "fp3");
        Database.Library.insertTrack("title3", "artist1", null, null, 90, null, null, "fp4");
        ArrayList<Database.Library.Track> tracks = Database.Library.getAllTracks("title, artist");
        //The following assertion ensure that all tracks are received and sorted on the correct fields
        assertTrue(tracks.size() == 4);
        assertTrue(tracks.get(0).title.equals("title1") && tracks.get(0).artist.equals("a"));
        assertTrue(tracks.get(1).title.equals("title1") && tracks.get(1).artist.equals("artist1"));
        assertTrue(tracks.get(2).title.equals("title2") && tracks.get(2).artist.equals("artist2"));
        assertTrue(tracks.get(3).title.equals("title3") && tracks.get(3).artist.equals("artist1"));
        
        Database.Playlists.addSongToPlaylist(1, 1, 0);
        Database.Library.removeTrack(0);
        //ensures the track was removed from the library
        assertTrue(!Database.Library.getAllTracks("title, artist").contains(track1));
        //ensures the removal cascades to playlists
        assertTrue(Database.Playlists.getPlaylistSongs(0).isEmpty());
        
        Database.Library.editTrack(2, "changedTitle", "", "", "", 100, "", "");
        //ensures the tracks can be edited
        assertTrue(Database.Library.getTrack(2).title.equals("changedTitle"));
    }
    
    @Test
    public void testAddEditRemoveSongInPlaylist() throws SQLException {
        Database.Library.createTracksTable();
        for (int i = 0; i < 4; i++) {
            Database.Library.insertTrack("", "", "", "", 90, "", "", "fp"+i);
        }
        Database.Playlists.createPlaylistsTable();
        int playlistID = Database.Playlists.getAllPlaylists().get(0).playlistID;
        Database.Playlists.addSongToPlaylist(playlistID, 1, 0);
        assertTrue(Database.Playlists.getPlaylistSongs(playlistID).get(0) == 1); //makes sure the song with songID 1 was added to the playlist;
        Database.Playlists.addSongToPlaylist(playlistID, 2, 1);
        Database.Playlists.addSongToPlaylist(playlistID, 3, 2);
        //the following makes sure order is maintained when getting all songs
        ArrayList<Integer> songIDs = Database.Playlists.getPlaylistSongs(playlistID);
        assertTrue(songIDs.size() == 3);
        assertTrue(songIDs.get(0) == 1);
        assertTrue(songIDs.get(1) == 2);
        assertTrue(songIDs.get(2) == 3);
        //the following makes sure the order is properly shifted on a song being add in the middle
        Database.Playlists.addSongToPlaylist(playlistID, 4, 1);
        songIDs = Database.Playlists.getPlaylistSongs(playlistID);
        assertTrue(songIDs.size() == 4);
        assertTrue(songIDs.get(0) == 1);
        assertTrue(songIDs.get(1) == 4);
        assertTrue(songIDs.get(2) == 2);
        assertTrue(songIDs.get(3) == 3);
        //the following makes sure the order is shifted back on a deletion in the middle
        Database.Playlists.removeSongFromPlaylist(playlistID, 2);
        songIDs = Database.Playlists.getPlaylistSongs(playlistID);
        assertTrue(songIDs.size() == 3);
        assertTrue(songIDs.get(0) == 1);
        assertTrue(songIDs.get(1) == 4);
        assertTrue(songIDs.get(2) == 3);
    }
}
