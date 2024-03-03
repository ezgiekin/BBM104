import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * CustomMediaPlayer is a class that extends DuckHunt and represents a custom media player.
 * It provides functionality to play media files with specific settings.
 */
public class CustomMediaPlayer extends DuckHunt {

    private String path;

    private int cycleCount;

    private MediaPlayer mediaPlayer;

    /**
     * Constructs a CustomMediaPlayer object with the given path and cycleCount.
     *
     * @param path       The path of the media file to be played.
     * @param cycleCount The number of times the media should be played.
     */
    public CustomMediaPlayer(String path, int cycleCount) {
        this.path = path;
        this.cycleCount = cycleCount;
    }

    /**
     * Plays the media using the specified path and cycleCount.
     * Sets up a MediaPlayer instance and starts playing the media file.
     */
    public void mediaPlay() {
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(cycleCount);
        mediaPlayer.setVolume(VOLUME);
        mediaPlayer.play();
    }

    /**
     * Retrieves the MediaPlayer instance used by the CustomMediaPlayer.
     *
     * @return The MediaPlayer instance.
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
