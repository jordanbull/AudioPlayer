package jbull.audioplayer;

import java.net.URL;
import java.util.HashMap;

/**
 *
 * @author jordan
 */
public abstract class Codec {
    
    private static HashMap<String, Codec> codecs = new HashMap<String, Codec>();
    
    protected abstract Codec createInstance();
    
    protected abstract String[] getFileExtensionTypes();
    
    
    protected abstract void load(URL url);
    
    protected abstract void setForPlaying();
    
    protected abstract void play();
    
    protected abstract void pause();
    
    protected abstract void seek(double seconds);
    
    protected abstract MetaData getMetaData();
    
    public static class MetaData {
        public String artist;
        public String album;
        public String title;
        public String genre;
        public String year;
        public int length;
    }
}
