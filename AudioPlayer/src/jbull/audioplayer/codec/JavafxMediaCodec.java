package jbull.audioplayer.codec;

import java.net.URL;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableMap;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import jbull.audioplayer.Codec;

/**
 *
 * @author jordan
 */
public class JavafxMediaCodec extends Codec {

    Media media;
    MediaPlayer mediaPlayer;
    
    @Override
    protected Codec createInstance() {
        return new JavafxMediaCodec();
    }

    @Override
    protected String[] getFileExtensionTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void load(URL url) {
        media = new Media(url.toString());
    }

    @Override
    protected void play() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void pause() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void seek(double seconds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MetaData getMetaData() {
        ObservableMap<String, Object> map = media.getMetadata();
        map.addListener(new ChangeListener());
        while(true);
    }

    @Override
    protected void setForPlaying() {
        mediaPlayer = new MediaPlayer(media);
    }
    
    private class ChangeListener implements MapChangeListener {

        @Override
        public void onChanged(Change change) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
}
