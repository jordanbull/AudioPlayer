/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    
    private static final String PLAYLIST_TABLE = "Playlists";
    private static final String PLAYLIST_SONGS_TABLE = "PlaylistSongs";
    private static final String LIBRARY_TABLE = "Tracks";
    private static final String SETTINGS_TABLE = "Settings";
    private static final String PLUGINS_TABLE = "Plugins";
    private static final String APPLICATION = "DefaultApplication";
    
    private static Connection connection;
    
    
    
    protected static String getDBLocation() {
        return System.getProperty("java.class.path").substring(0,
                System.getProperty("java.class.path").lastIndexOf('/')) +
                "/database/db";
    }
    
    protected static Connection connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        System.out.println("connecting to database at " + getDBLocation());
        Connection conn = DriverManager.getConnection("jdbc:h2:" + getDBLocation()
                + ";DATABASE_TO_UPPER=FALSE", USERNAME, PASSWORD);
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
    
    protected static boolean tableExists(String tableName) throws SQLException {
        ResultSet results = connection.getMetaData().getTables(
                connection.getCatalog(), null, tableName , null);
        if (results.next()) {
            return results.getString("TABLE_NAME").equals(tableName);
        }
        else {
            return false;
        }
    }
    
    public static String format(String str, int length) {
        if (str == null) {
            return "";
        } else if (str.length() > length) {
            return str.substring(0, length);
        }
        return str;
    }
    
    
    public static class Library {
        
        private static final String songToLibraryStr = "INSERT INTO "+
            LIBRARY_TABLE+" (title, artist, album, genre, length, songformat, "
                + "year, filepath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        private static final String getTrackByIDStr = "SELECT * FROM " + LIBRARY_TABLE
                + " WHERE songID=?";
        private static final String getAllTracksOrderedStr = "SELECT * FROM " +
                LIBRARY_TABLE + " ORDER BY ";
        private static final String editTrackStr = "UPDATE "+LIBRARY_TABLE+" SET"
                + " title=?, artist=?, album=?, genre=?, length=?, songformat=?,"
                + "year=? WHERE songID=?";
        
        private static PreparedStatement addSongToLibrary;
        private static PreparedStatement getTrackByID;
        private static PreparedStatement editTrack;
        
        protected static void createTracksTable() throws SQLException {
            String sqlFields =
                    "songID int NOT NULL AUTO_INCREMENT,"
                    + "title varchar(255) NOT NULL,"
                    + "artist varchar(255) NOT NULL,"
                    + "album varchar(255) NOT NULL,"
                    + "genre varchar(255) NOT NULL,"
                    + "songformat varchar(10) NOT NULL,"
                    + "length int NOT NULL,"
                    + "year varchar(4),"
                    + "filepath varchar NOT NULL,"
                    + "UNIQUE (filepath),"
                    + "PRIMARY KEY (songID)";
            createTableIfNotExists(LIBRARY_TABLE, sqlFields);
            
            addSongToLibrary = connection.prepareStatement(songToLibraryStr);
            getTrackByID = connection.prepareStatement(getTrackByIDStr);
            editTrack = connection.prepareStatement(editTrackStr);
        }

        protected static void insertTrack(String title, String artist, String album,
                String genre, int length, String songformat, String year, String filepath) throws SQLException {
            addSongToLibrary.setString(1, format(title, 255));
            addSongToLibrary.setString(2, format(artist, 255));
            addSongToLibrary.setString(3, format(album, 255));
            addSongToLibrary.setString(4, format(genre, 255));
            addSongToLibrary.setString(5, Integer.toString(length));
            addSongToLibrary.setString(6, format(songformat, 10));
            addSongToLibrary.setString(7, format(year, 4));
            addSongToLibrary.setString(8, filepath);
            addSongToLibrary.executeUpdate();
        }

        protected static void editTrack(int sid, String title, String artist, String album,
                String genre, int length, String songformat, String year) throws SQLException {
            editTrack.setString(1, format(title, 255));
            editTrack.setString(2, format(artist, 255));
            editTrack.setString(3, format(artist, 255));
            editTrack.setString(4, format(genre, 255));
            editTrack.setString(5, Integer.toString(length));
            editTrack.setString(6, format(songformat, 10));
            editTrack.setString(7, format(year, 4));
            editTrack.setInt(8, sid);
            editTrack.executeUpdate();
        }
        
        protected static void removeTrack(int sid) throws SQLException {
            Playlists.removeSongFromAllPlaylists(sid);
            String sqlQuery = "DELETE FROM "+LIBRARY_TABLE+" WHERE songID="+sid;
            connection.createStatement().execute(sqlQuery);
        }

        protected static Track getTrack(int songID) throws SQLException {
            getTrackByID.setInt(1, songID);
            ResultSet rs = getTrackByID.executeQuery();
            rs.next();
            Track t = new Track();
            t.title = rs.getString("title");
            t.album = rs.getString("album");
            t.artist = rs.getString("artist");
            t.genre = rs.getString("genre");
            t.length = rs.getInt("length");
            t.year = rs.getString("year");
            t.songformat = rs.getString("songformat");
            t.songID = songID;
            t.filepath = rs.getString("filepath");
            return t;
        }

        protected static ArrayList<Track> getAllTracks(String orderBy) throws SQLException {
            String sqlQuery = getAllTracksOrderedStr + orderBy;
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
                t.songformat = rs.getString("songformat");
                t.songID = rs.getInt("songID");
                t.filepath = rs.getString("filepath");
                tracks.add(t);
            }
            return tracks;
        }
        public static class Track {
            public String title = "";
            public String album = "";
            public String artist = "";
            public String genre = "";
            public String songformat = "";
            public String year = "";
            public int length = 0;
            public int songID;
            public String filepath = "";
        }
    }
    
    
    public static class Playlists {
        
        private static final String songToPlaylistStr = "INSERT INTO "+
            PLAYLIST_SONGS_TABLE+" (pid, sid, position) VALUES (?, ?, ?)";
        private static final String incrementSongPositionsStr = "UPDATE "+
                PLAYLIST_SONGS_TABLE+" SET position=( position + 1 ) WHERE "
                + "pid=? AND position>=?";
        private static final String removeSongFromPlaylistStr = "DELETE FROM " +
                PLAYLIST_SONGS_TABLE+" WHERE pid=? AND sid=?";
        private static final String decrementSongPositionsStr = "UPDATE "+
                PLAYLIST_SONGS_TABLE+" SET position=( position - 1 ) WHERE "
                + "pid=? AND position>?";
        private static final String getPlaylistSongsStr = "SELECT sid FROM " +
                PLAYLIST_SONGS_TABLE+" WHERE pid=? ORDER BY position";
        
        private static PreparedStatement addSongToPlaylist;
        private static PreparedStatement incrementSongPositions;
        private static PreparedStatement removeSongFromPlaylist;
        private static PreparedStatement decrementSongPositions;
        private static PreparedStatement getPlaylistSongs;
        
        protected static void createPlaylistsTable() throws SQLException {
            boolean tableExists = tableExists(PLAYLIST_TABLE);
            String sqlFields =
                    "pid int NOT NULL AUTO_INCREMENT,"
                    + "name varchar(255) NOT NULL,"
                    + "UNIQUE (name),"
                    + "PRIMARY KEY (pid)";
            createTableIfNotExists(PLAYLIST_TABLE, sqlFields);
            
            if (!tableExists) {
                createPlaylist("new playlist");
            }
            
            sqlFields = 
                    "pid int NOT NULL,"
                    + "sid int NOT NULL,"
                    + "position int NOT NULL,"
                    + "PRIMARY KEY (pid, position),"
                    + "FOREIGN KEY (sid) REFERENCES "+LIBRARY_TABLE+"(songID),"
                    + "FOREIGN KEY (pid) REFERENCES "+PLAYLIST_TABLE+"(pid)";
            createTableIfNotExists(PLAYLIST_SONGS_TABLE, sqlFields);

            addSongToPlaylist = connection.prepareStatement(songToPlaylistStr);
            incrementSongPositions = connection.prepareStatement(incrementSongPositionsStr);
            removeSongFromPlaylist = connection.prepareStatement(removeSongFromPlaylistStr);
            decrementSongPositions = connection.prepareStatement(decrementSongPositionsStr);
            getPlaylistSongs = connection.prepareStatement(getPlaylistSongsStr);
            
        }
    
        protected static void createPlaylist(String name) throws SQLException {
            name = format(name, 255);
            String sqlCommand = "INSERT INTO "+PLAYLIST_TABLE+" (name) VALUES (\'"+name+"\')";
            connection.createStatement().execute(sqlCommand);
        }
        
        protected static void renamePlaylist(String oldName, String newName) throws SQLException {
            newName = format(newName, 255);
            String sqlCommand = "UPDATE "+PLAYLIST_TABLE+" SET name=\""+newName
                    +"\" WHERE name=\""+oldName+"\"";
            connection.createStatement().execute(sqlCommand);
            createPlaylist("new playlist");
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
    
        protected static void addSongToPlaylist(int playlistID, int songID, int position) throws SQLException {
            incrementSongPositions.setInt(1, playlistID);
            incrementSongPositions.setInt(2, position);
            incrementSongPositions.executeUpdate();
            
            addSongToPlaylist.setInt(1, playlistID);
            addSongToPlaylist.setInt(2, songID);
            addSongToPlaylist.setInt(3, position);
            addSongToPlaylist.execute();
        }
        
        protected static void removeSongFromPlaylist(int playlistID, int position) throws SQLException {
            removeSongFromPlaylist.setInt(1, playlistID);
            removeSongFromPlaylist.setInt(2, position);
            removeSongFromPlaylist.executeUpdate();
            
            decrementSongPositions.setInt(1, playlistID);
            decrementSongPositions.setInt(2, position);
            decrementSongPositions.executeUpdate();
        }
        
        protected static void removeSongFromAllPlaylists(int songID) throws SQLException {
            String sqlQuery = "SELECT pid, position FROM "+PLAYLIST_SONGS_TABLE
                    +" WHERE sid="+songID;
            ResultSet results = connection.createStatement().executeQuery(sqlQuery);
            while (results.next()) {
                removeSongFromPlaylist(results.getInt("pid"), results.getInt("position"));
            }
        }
        
        protected static ArrayList<Playlist> getAllPlaylists() throws SQLException {
            String sqlQuery = "SELECT * FROM "+PLAYLIST_TABLE;
            ResultSet results = connection.createStatement().executeQuery(sqlQuery);
            ArrayList<Playlist> playlists = new ArrayList<Playlist>();
            while (results.next()) {
                Playlist playlist = new Playlist();
                playlist.name = results.getString("name");
                playlist.playlistID = results.getInt("pid");
                playlists.add(playlist);
            }
            return playlists;
        }
        
        protected static ArrayList<Integer> getPlaylistSongs(int playlistID) throws SQLException {
            getPlaylistSongs.setInt(1, playlistID);
            ResultSet results = getPlaylistSongs.executeQuery();
            ArrayList<Integer> songIDs = new ArrayList<Integer>();
            while (results.next()) {
                songIDs.add(results.getInt(playlistID));
            }
            return songIDs;
        }
        
        public static class Playlist {
            public String name;
            public int playlistID;
        }
    }

    
    public static class Plugins {
        
        private static final String setAutoLaunchStr = "UPDATE "+PLUGINS_TABLE+
                " SET autoLaunch=? WHERE pluginID=?";
        
        private static PreparedStatement setAutoLaunch;
        
        protected static void createPluginsTable() throws SQLException {
            boolean tableExists = tableExists(PLUGINS_TABLE);
            String sqlFields = "pluginID int NOT NULL AUTO INCREMENT,"
                    + "name VARCHAR NOT NULL,"
                    + "filePath VARCHAR NOT NULL,"
                    + "autoLaunch VARCHAR(5) NOT NULL"
                    + "PRIMARY KEY (pluginID),"
                    + "UNIQUE (filePath)";
            createTableIfNotExists(PLUGINS_TABLE, sqlFields);
            setAutoLaunch = connection.prepareStatement(setAutoLaunchStr);
            if (!tableExists) {
                registerPlugin(APPLICATION, System.getProperty("java.class.path"));
            }
        }
        
        protected static void registerPlugin(String pluginName, String filepath) throws SQLException {
            String sqlCommand = "INSERT INTO "+PLUGINS_TABLE+" (name, filepath, autoLaunch) "
                    + "VALUES ("+pluginName+", "+filepath+", true)";
            connection.createStatement().executeUpdate(sqlCommand);
        }
        
        protected static ArrayList<Plugin> getAllPlugins() throws SQLException {
            String sqlQuery = "SELECT * FROM "+PLUGINS_TABLE;
            ResultSet results = connection.createStatement().executeQuery(sqlQuery);
            ArrayList<Plugin> plugins = new ArrayList<Plugin>();
            while (results.next()) {
                Plugin plugin = new Plugin();
                plugin.pluginID = results.getInt("pluginID");
                plugin.name = results.getString("name");
                plugin.filePath = results.getString("filePath");
                plugin.autoLaunch = Boolean.valueOf(results.getString("autoLaunch"));
                plugins.add(plugin);
            }
            return plugins;
        }
        
        protected static void setAutoLaunch(boolean autoLaunch, int pluginID) throws SQLException {
            setAutoLaunch.setString(1, Boolean.toString(autoLaunch));
            setAutoLaunch.setInt(2, pluginID);
            setAutoLaunch.executeUpdate();
        }
        
        public static class Plugin {
            public int pluginID;
            public String name;
            public String filePath;
            public boolean autoLaunch;
        }
    }

    public static class Settings {
        
        private static final String getSettingValueStr = "Select settingValue "
                + "FROM "+SETTINGS_TABLE+" WHERE pluginID=? AND settingName=?";
        
        private static PreparedStatement getSettingValue;
        
        protected static void createSettingsTable() throws SQLException {
            String sqlFields = "pluginID int NOT NULL,"
                    + "settingName VARCHAR NOT NULL,"
                    + "settingValue VARCHAR NOT NULL,"
                    + "PRIMARY KEY (pluginID, settingName),"
                    + "FOREIGN KEY (pluginID) REFERENCES "
                    + PLUGINS_TABLE;
            createTableIfNotExists(SETTINGS_TABLE, sqlFields);
            getSettingValue = connection.prepareStatement(getSettingValueStr);
        }
        
        protected static void addSetting(int pluginID, String settingName,
                String settingValue) throws SQLException {
            String sqlCommand = "INSERT INTO "+SETTINGS_TABLE+" (pluginID,"
                    + "settingName, settingValue) VALUES ( "+pluginID+
                    ", "+settingName+", "+settingValue+" )";
            connection.createStatement().executeUpdate(sqlCommand);
        }
        
        protected static String getSettingValue(int pluginID, String settingName)
                throws SQLException {
            getSettingValue.setInt(1, pluginID);
            getSettingValue.setString(2, settingName);
            ResultSet results = getSettingValue.executeQuery();
            results.next();
            return results.getString("settingValue");
        }
    }
}
