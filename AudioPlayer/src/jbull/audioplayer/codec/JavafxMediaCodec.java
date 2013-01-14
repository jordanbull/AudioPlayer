package jbull.audioplayer.codec;

import java.io.File;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import jbull.audioplayer.Codec;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

/**
 *
 * @author jordan
 */
public class JavafxMediaCodec extends Codec {

    MediaPlayer mediaPlayer;
    URI uri;
    
    @Override
    protected Codec createInstance() {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF); 
        return new JavafxMediaCodec();
    }

    @Override
    protected String[] getFileExtensionTypes() {
        return new String[] {"aif", "aiff", "mp3", "wav"};
    }

    @Override
    protected void load(URI uri) {
        this.uri = uri;
        mediaPlayer = new MediaPlayer(new Media(uri.toString()));
    }

    @Override
    protected void play() {
        mediaPlayer.play();
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
        MetaData metaData = new MetaData();
        try {
            File file = new File(this.uri);
            metaData.title = file.getName();
            AudioFile f = AudioFileIO.read(file);
            Tag tag = f.getTag();
            AudioHeader header = f.getAudioHeader();
            metaData.title = tag.getFirst(FieldKey.TITLE);
            metaData.album = tag.getFirst(FieldKey.ALBUM);
            metaData.artist = tag.getFirst(FieldKey.ARTIST);
            metaData.year = tag.getFirst(FieldKey.YEAR);
            metaData.length = header.getTrackLength();
            if (metaData.title.equals(""))
                metaData.title = file.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaData;
    }

    @Override
    protected void setForPlaying() {
        Media media = new Media(uri.toString());
        mediaPlayer = new MediaPlayer(media);
    }
    
}
