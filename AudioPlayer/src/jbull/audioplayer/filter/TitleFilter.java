/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer.filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try {
            ArrayList<Database.Library.Track> tracks = Database.Library.getAllTracks("title");
            for (Database.Library.Track track : tracks) {
                    library.appendItem(Application.createTrackView(track));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
