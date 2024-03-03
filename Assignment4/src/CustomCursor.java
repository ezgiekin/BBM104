import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The CustomCursor class extends the DuckHunt game and manages the custom cursor displayed on the game screen.
 * It provides methods to set and change the cursor image.
 */
public class CustomCursor extends DuckHunt {

    public static int currentIndex = 0;

    public static ImageView cursorView;
    private static String[] crosshairImages = {
            "assets/crosshair/1.png",
            "assets/crosshair/2.png",
            "assets/crosshair/3.png",
            "assets/crosshair/4.png",
            "assets/crosshair/5.png",
            "assets/crosshair/6.png",
            "assets/crosshair/7.png"
    };

    /**
     * Sets the selected cursor image and returns it as a Cursor object.
     *
     * @return The selected cursor as a Cursor object.
     */
    public static Cursor setSelectedCursor() {
        String imagePath = crosshairImages[currentIndex];
        Image crosshairImage = new Image(imagePath);
        Image resizedCursor = new Image(imagePath, crosshairImage.getWidth() * SCALE, crosshairImage.getHeight() * SCALE, true, true);
        cursorView = new ImageView(resizedCursor);
        double hotspotX = resizedCursor.getWidth() / 2;
        double hotspotY = resizedCursor.getHeight() / 2;
        return new ImageCursor(resizedCursor, hotspotX, hotspotY);
    }

    /**
     * Changes the cursor image displayed on the specified pane.
     *
     * @param pane The Pane where the cursor image will be displayed.
     */
    public void changeCursorImage(Pane pane) {
        if (currentIndex == -1) {
            currentIndex = crosshairImages.length - 1;
        } else if (currentIndex > crosshairImages.length - 1) {
            currentIndex = 0;
        }
        String imagePath = crosshairImages[currentIndex];
        Image crosshairImage = new Image(imagePath);
        ImageView imageView = new ImageView(crosshairImage);
        imageView.setFitWidth(crosshairImage.getWidth() * SCALE);
        imageView.setFitHeight(crosshairImage.getHeight() * SCALE);
        imageView.toFront();
        pane.getChildren().add(imageView);
        imageView.setLayoutX(scene2.getWidth() / 2 - imageView.getImage().getWidth() / 4);
        imageView.setLayoutY(scene2.getHeight() / 2 + imageView.getImage().getWidth() / 4);
    }

}
