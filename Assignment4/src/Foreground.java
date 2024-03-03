import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The Foreground class extends the DuckHunt game and manages the foreground images displayed on the game screen.
 * It provides methods to set and clean the foreground images.
 */
public class Foreground extends DuckHunt {

    public static final String[] foregroundimages = {
            "assets/foreground/1.png",
            "assets/foreground/2.png",
            "assets/foreground/3.png",
            "assets/foreground/4.png",
            "assets/foreground/5.png",
            "assets/foreground/6.png",
    };

    public static ImageView imageView1, imageView2, imageView3;

    /**
     * Sets the foreground images on the specified pane.
     *
     * @param pane The Pane where the foreground images will be displayed.
     */
    public static void setForeground(Pane pane) {
        String imagePath = foregroundimages[BackGround.currentIndex];
        Image foregroundImage = new Image(imagePath);
        imageView1 = new ImageView(foregroundImage);
        imageView1.setFitWidth(foregroundImage.getWidth() * SCALE);
        imageView1.setFitHeight(foregroundImage.getHeight() * SCALE);

        imageView2 = new ImageView(foregroundImage);
        imageView2.setFitWidth(foregroundImage.getWidth() * SCALE);
        imageView2.setFitHeight(foregroundImage.getHeight() * SCALE);
        imageView2.setLayoutX(-foregroundImage.getWidth() * SCALE);

        imageView3 = new ImageView(foregroundImage);
        imageView3.setFitWidth(foregroundImage.getWidth() * SCALE);
        imageView3.setFitHeight(foregroundImage.getHeight() * SCALE);
        imageView3.setLayoutX(foregroundImage.getWidth() * SCALE );

        pane.getChildren().add(imageView1);
        pane.getChildren().add(imageView2);
        pane.getChildren().add(imageView3);
    }

    /**
     * Removes the foreground images from the specified pane.
     *
     * @param pane The Pane from which the foreground images will be removed.
     */
    public static void cleanForeground(Pane pane) {
        pane.getChildren().remove(imageView1);
        pane.getChildren().remove(imageView2);
        pane.getChildren().remove(imageView3);

    }
}


