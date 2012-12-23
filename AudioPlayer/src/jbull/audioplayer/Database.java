/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import com.iciql.Db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jordan
 */
public class Database {
    
    private static final String USERNAME = "AudioPlayer";
    private static final String PASSWORD = "dbj23js8nRvdg8";
    
    private static Connection connection;
    
    protected static Connection connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:" +
                System.getProperty("java.class.path").substring(0,
                System.getProperty("java.class.path").lastIndexOf('/')) +
                "/database/db", USERNAME, PASSWORD);
        connection = conn;
        return connection;
    }
    
    /**
     * Creates a table in the database if a table does not already exist with
     * that name.
     * @param tableName the name of the new table
     * @param sqlFields the fields and constraints on the fields (do not include
     * the parenthesis)
     */
    protected static void createTableIfNotExists(String tableName,
            String sqlFields) throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " +
                                  tableName + " ( " + sqlFields + " )");
    }
    
    protected static void createTracksTable() throws SQLException {
        String tableName = "Tracks";
        String sqlFields =
                "songID int NOT NULL AUTO_INCREMENT,"
                + "title varchar(255) NOT NULL,"
                + "artist varchar(255) NOT NULL,"
                + "album varchar(255) NOT NULL,"
                + "genre varchar(255) NOT NULL,"
                + "length int NOT NULL,"
                + "fileType varchar(10) NOT NULL,"
                + "year varchar(4),"
                + "url text NOT NULL,"
                + "UNIQUE (url),"
                + "PRIMARY KEY (songID)";
        createTableIfNotExists(tableName, sqlFields);
    }
    
    protected static void createPlaylistTable() throws SQLException {
        String tableName = "Playlists";
        String sqlFields =
                "pid int NOT NULL AUTO_INCREMENT,"
                + "name varchar(255) NOT NULL,"
                + "UNIQUE (name),"
                + "PRIMARY KEY (pid)";
        createTableIfNotExists(tableName, sqlFields);
        
        tableName = "PlaylistSongs";
        sqlFields = 
                "pid int NOT NULL,"
                + "sid int NOT NULL,"
                + "PRIMARY KEY (pid, sid)";
        createTableIfNotExists(tableName, sqlFields);
        
        createPlaylist("new playlist");
    }
    
    public static void createPlaylist(String name) throws SQLException {
        name = format(name, 255);
        String sqlCommand = "INSERT INTO Playlists (name) VALUES (\'"+name+"\')";
        connection.createStatement().execute(sqlCommand);
    }
    
    protected static void removePlaylist(String name) throws SQLException {
        String sqlQuery = "SELECT pid FROM Playlists WHERE name="+format(name, 255);
        ResultSet rs = connection.createStatement().executeQuery(sqlQuery);
        rs.next();
        String pid = Integer.toString(rs.getInt("pid"));
        sqlQuery = "DELETE FROM PlaylistSongs WHERE pid="+pid;
        connection.createStatement().execute(sqlQuery);
        sqlQuery = "DELETE FROM Playlists WHERE pid="+pid;
        connection.createStatement().execute(sqlQuery);
    }
    
    public static void insertTrack(String title, String artist, String album,
            String genre, int length, String fileType, String year, String url) throws SQLException {
        title = format(title, 255);
        artist = format(artist, 255);
        album = format(artist, 255);
        genre = format(genre, 255);
        String len = Integer.toString(length);
        fileType = format(fileType, 10);
        year = format(year, 4);
        String sqlCommand = "INSERT INTO Tracks (title, artist, album, genre,"
                + "length, fileType, year, url) VALUES ("+title+", "+artist+", "+album
                + ", "+genre+", "+len+", "+fileType+", "+year+", "+url+")";
        connection.createStatement().execute(sqlCommand);
    }
    
    protected static void removeTrack(int sid) throws SQLException {
        String sqlQuery = "DELETE FROM Tracks WHERE songID="+sid;
        connection.createStatement().execute(sqlQuery);
    }
    
    public static Track getTrack(int songID) throws SQLException {
        String sqlQuery = "SELECT * FROM Tracks WHERE songID="+songID;
        ResultSet rs = connection.createStatement().executeQuery(sqlQuery);
        rs.next();
        Track t = new Track();
        t.title = rs.getString("title");
        t.album = rs.getString("album");
        t.artist = rs.getString("artist");
        t.genre = rs.getString("genre");
        t.length = rs.getInt("length");
        t.year = rs.getString("year");
        t.fileType = rs.getString("fileType");
        t.songID = songID;
        t.URL = rs.getString("url");
        return t;
    }
    
    public static ArrayList<Track> getAllTracks() throws SQLException {
        String sqlQuery = "SELECT * FROM Tracks";
        ArrayList<Track> tracks = new ArrayList<Track>();
        ResultSet rs = connection.createStatement().executeQuery(sqlQuery);
        while (rs.next()) {
            Track t = new Track();
            t.title = rs.getString("title");
            t.album = rs.getString("album");
            t.artist = rs.getString("artist");
            t.genre = rs.getString("genre");
            t.length = rs.getInt("length");
            t.year = rs.getString("year");
            t.fileType = rs.getString("fileType");
            t.songID = rs.getInt("songID");
            t.URL = rs.getString("url");
            tracks.add(t);
        }
        return tracks;
    }
    public static class Track {
        public String title = "";
        public String album = "";
        public String artist = "";
        public String genre = "";
        public String fileType = "";
        public String year = "";
        public int length = 0;
        public int songID;
        public String URL = "";
    }
    
    public static String format(String str, int length) {
        if (str == null) {
            return "";
        } else if (str.length() > length) {
            return str.substring(0, length);
        }
        return str;
    }
}
