import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a Duck object that extends the DuckHunt game.
 */
public class Duck extends DuckHunt {

    public static int duckCount = 0;
    private ImageView birdView;
    private Timeline timeline;
    private String color;
    private double birdX;
    private double birdY;
    private AtomicInteger flyingDirectionX = new AtomicInteger();
    private AtomicInteger flyingDirectionY = new AtomicInteger();
    private CustomMediaPlayer duckFallsPlayer;

    private boolean isPlaying = false;

    private boolean gotHit = false;

    /**
     * Constructs a new Duck object with the specified color.
     *
     * @param color The color of the duck.
     */
    public Duck(String color) {
        this.color = color;
    }

    /**
     * Returns the ImageView object representing the visual representation of the duck.
     *
     * @return The ImageView object of the duck.
     */
    public ImageView getBirdView() {
        return birdView;
    }

    /**
     * Makes the duck fly horizontally within the specified pane and scene.
     *
     * @param pane            The pane where the duck will be displayed.
     * @param scene           The scene where the duck is located.
     * @param flyingDirection The flying direction of the duck. A negative value (-1) represents flying to the left, while a positive value (1) represents flying to the right.
     * @param startingY       The initial Y position of the duck.
     */
    public void flyHorizontal(Pane pane, Scene scene, int flyingDirection, double startingY) {
        this.flyingDirectionX.set(flyingDirection);
        Image[] flyingDuckHorizontal = {new Image(String.format("assets/duck_%s/4.png", color)),
                new Image(String.format("assets/duck_%s/5.png", color)),
                new Image(String.format("assets/duck_%s/6.png", color)),
        };
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // wing movement of the duck is done in this keyframe.
        birdView = new ImageView();
        birdView.setImage(flyingDuckHorizontal[0]);
        birdView.setFitHeight(flyingDuckHorizontal[0].getHeight() * SCALE);
        birdView.setFitWidth(flyingDuckHorizontal[0].getWidth() * SCALE);

        Duration frameDuration = Duration.seconds(0.3);
        KeyFrame keyFrame = new KeyFrame(frameDuration, event -> {
            int currentIndex = 0;
            if (birdView.getImage().equals(flyingDuckHorizontal[0])) {
                currentIndex = 1;
            } else if (birdView.getImage().equals(flyingDuckHorizontal[1])) {
                currentIndex = 2;
            } else if (birdView.getImage().equals(flyingDuckHorizontal[2])) {
                currentIndex = 0;
            }
            birdView.setImage(flyingDuckHorizontal[currentIndex]);
        });

        // decides start position of the duck depending on the flying direction
        if (this.flyingDirectionX.get() == -1) {
            birdX = scene.getWidth() - birdView.getImage().getWidth() * SCALE;
            birdView.setScaleX(this.flyingDirectionX.get());
        } else {
            birdX = 0;
        }

        birdView.setLayoutY(startingY);
        birdView.setLayoutX(birdX);

        //duck flies horizontally with this keyframe
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.3), event -> {
            birdX += this.flyingDirectionX.get() * 20 * SCALE;
            if (birdX >= scene.getWidth() * 2 - birdView.getImage().getWidth() * SCALE) {
                birdX = scene.getWidth() * 2 - birdView.getImage().getWidth() * SCALE;
                this.flyingDirectionX.set(-this.flyingDirectionX.get());
                birdView.setScaleX(this.flyingDirectionX.get());
            } else if (birdX <= -scene.getWidth()) {
                birdX = -scene.getWidth();
                this.flyingDirectionX.set(-this.flyingDirectionX.get());
                birdView.setScaleX(this.flyingDirectionX.get());
            }
            birdView.setLayoutX(birdX);
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();
        pane.getChildren().add(birdView);


    }

    /**
     * Makes the duck fly diagonally within the specified pane and scene.
     *
     * @param pane             The pane where the duck will be displayed.
     * @param scene            The scene where the duck is located.
     * @param flyingDirectionX The horizontal flying direction of the duck. A negative value (-1) represents flying to the left, while a positive value (1) represents flying to the right.
     * @param flyingDirectionY The vertical flying direction of the duck. A negative value (-1) represents flying upwards, while a positive value (1) represents flying downwards.
     * @param startingY        The initial Y position of the duck.
     */
    public void flyDiagonal(Pane pane, Scene scene, int flyingDirectionX, int flyingDirectionY, double startingY) {
        this.flyingDirectionX.set(flyingDirectionX);
        this.flyingDirectionY.set(flyingDirectionY);
        Image[] flyingDuckHorizontal = {new Image(String.format("assets/duck_%s/1.png", color)),
                new Image(String.format("assets/duck_%s/2.png", color)),
                new Image(String.format("assets/duck_%s/3.png", color)),
        };
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // wing movement of the duck is done in this keyframe.
        birdView = new ImageView();
        birdView.setImage(flyingDuckHorizontal[0]);
        birdView.setFitHeight(flyingDuckHorizontal[0].getHeight() * SCALE);
        birdView.setFitWidth(flyingDuckHorizontal[0].getWidth() * SCALE);

        Duration frameDuration = Duration.seconds(0.3);
        KeyFrame keyFrame = new KeyFrame(frameDuration, event -> {
            int currentIndex = 0;
            if (birdView.getImage().equals(flyingDuckHorizontal[0])) {
                currentIndex = 1;
            } else if (birdView.getImage().equals(flyingDuckHorizontal[1])) {
                currentIndex = 2;
            } else if (birdView.getImage().equals(flyingDuckHorizontal[2])) {
                currentIndex = 0;
            }
            birdView.setImage(flyingDuckHorizontal[currentIndex]);

        });

        // decides start position of the duck depending on the flying direction on both axes
        if (this.flyingDirectionX.get() == -1) {
            birdX = scene.getWidth() - birdView.getImage().getWidth() * SCALE;
            birdView.setScaleX(this.flyingDirectionX.get());
        } else {
            birdX = 0;
        }
        if (this.flyingDirectionY.get() == -1) {
            birdY = scene.getHeight() - birdView.getImage().getHeight() * SCALE + startingY;
        } else {
            birdY = 0 + startingY;
            birdView.setScaleY(-this.flyingDirectionY.get());
        }

        birdView.setLayoutY(birdY);
        birdView.setLayoutX(birdX);

        //duck flies diagonally with this keyframe
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.3), event -> {
            birdY += this.flyingDirectionY.get() * 20 * SCALE;
            if (birdY >= scene.getHeight() - birdView.getImage().getHeight() * SCALE) {
                birdView.setScaleY(this.flyingDirectionY.get());
                birdY = scene.getHeight() - birdView.getImage().getHeight() * SCALE;
                this.flyingDirectionY.set(-this.flyingDirectionY.get());
            } else if (birdY <= 0) {
                birdView.setScaleY(this.flyingDirectionY.get());
                birdY = 0;
                this.flyingDirectionY.set(-this.flyingDirectionY.get());
            }
            birdView.setLayoutY(birdY);

            birdX += this.flyingDirectionX.get() * 20 * SCALE;
            if (birdX >= scene.getWidth() * 2 - birdView.getImage().getWidth() * SCALE) {
                birdX = scene.getWidth() * 2 - birdView.getImage().getWidth() * SCALE;
                this.flyingDirectionX.set(-this.flyingDirectionX.get());
                birdView.setScaleX(this.flyingDirectionX.get());
            } else if (birdX <= -scene.getWidth()) {
                birdX = -scene.getWidth();
                this.flyingDirectionX.set(-this.flyingDirectionX.get());
                birdView.setScaleX(this.flyingDirectionX.get());
            }
            birdView.setLayoutX(birdX);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();
        pane.getChildren().add(birdView);
    }

    /**
     * Simulates the falling of the duck within the specified scene.
     *
     * @param scene The scene where the duck is located.
     */
    public void duckFalls(Scene scene) {
        Image[] flyingDuckHorizontal = {
                new Image(String.format("assets/duck_%s/7.png", color)),
                new Image(String.format("assets/duck_%s/8.png", color))};
        timeline.stop();
        Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(1);

        // flip the duck depending on the flying direction when it got hit
        if (flyingDirectionY.get() == 1) {
            birdView.setScaleY(flyingDirectionY.get());
        }
        birdView.setImage(flyingDuckHorizontal[0]);

        // ducks falling images are set in this keyframe
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(300), e -> {
            int currentIndex = 0;
            if (birdView.getImage().equals(flyingDuckHorizontal[0])) {
                currentIndex = 1;
            }
            birdView.setImage(flyingDuckHorizontal[currentIndex]);
        });
        timeline2.getKeyFrames().add(keyFrame2);
        timeline2.play();

        // ducks falling motion
        timeline2.setOnFinished(event1 -> {
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(2.5),birdView);
            translateTransition1.setFromY(birdView.getTranslateY());
            translateTransition1.setToY(scene.getHeight());
            translateTransition1.setCycleCount(1);
            translateTransition1.play();

            // sound effect of falling
            duckFallsPlayer = new CustomMediaPlayer("assets/effects/DuckFalls.mp3", 1);
            duckFallsPlayer.mediaPlay();
            duckFallsPlayer.getMediaPlayer().setOnEndOfMedia(() ->{
                isPlaying = false;
            });
        });
    }

    public MediaPlayer getDuckFallsPlayer(){
        return duckFallsPlayer.getMediaPlayer();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean x){
        isPlaying = x;
    }

    public boolean isGotHit() {
        return gotHit;
    }

    public void setGotHit(boolean gotHit) {
        this.gotHit = gotHit;
    }
}
