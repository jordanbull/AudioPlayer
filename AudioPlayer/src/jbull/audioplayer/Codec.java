package jbull.audioplayer;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jordan
 */
public abstract class Codec {
    
    private static HashMap<String, Codec> codecs = new HashMap<String, Codec>();
    
    protected abstract Codec createInstance();
    
    protected abstract String[] getFileExtensionTypes();
    
    protected static void addCodec(Codec codec) {
        for (String type : codec.getFileExtensionTypes()) {
            if (!codecs.containsKey(type)) {
                codecs.put(type, codec);
            }
        }
    }
    
    protected static List<String> getSupportedAudioFormats() {
        List<String> list = new ArrayList<String>();
        list.addAll(codecs.keySet());
        return list;
    }
    
    protected abstract void load(URI uri);
    
    protected abstract void setForPlaying();
    
    protected abstract void play();
    
    protected abstract void pause();
    
    protected abstract void seek(double seconds);
    
    protected abstract MetaData getMetaData();
    
    protected static Codec getAppropriateCodec(String extension) {
        if (codecs.containsKey(extension)) {
            return codecs.get(extension).createInstance();
        } else {
            return null;
        }
    }
    
    public static class MetaData {
        public String artist = "";
        public String album = "";
        public String title = "";
        public String genre = "";
        public String year = "";
        public int length = 0;
    }
}
