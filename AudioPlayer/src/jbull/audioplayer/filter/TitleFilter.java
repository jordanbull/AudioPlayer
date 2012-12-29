/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jbull.audioplayer.Application;
import jbull.audioplayer.Database;
import jbull.audioplayer.Library;
import jbull.audioplayer.TrackView;

/**
 *
 * @author jordan
 */
public class TitleFilter implements Filter {

    @Override
    public String getName() {
        return "Title";
    }

    @Override
    public void sortAndInsert(Library library) {
        Class trackViewClass = Application.getTrackViewClass();
        try {
            Constructor<TrackView> constr = trackViewClass.getDeclaredConstructor(String.class, String.class, String.class, String.class,
                Integer.class, Integer.class, String.class, String.class);
            ArrayList<Database.Library.Track> tracks = Database.Library.getAllTracks("title");
            for (Database.Library.Track track : tracks) {
                    library.appendItem(constr.newInstance(new Object[] {track.title, track.artist,
                            track.album, track.genre, new Integer(track.length), new Integer(track.songID),
                            track.filepath, track.songformat}));
            }
        } catch (SQLException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
            
        }
    }
    
}
