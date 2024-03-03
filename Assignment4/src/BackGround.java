import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The BackGround class extends the DuckHunt game and manages the background images displayed on the game screen.
 * It provides methods to change the background image and handle the current background index.
 */
public class BackGround extends DuckHunt {
    public static final String[] backgroundimages = {
            "assets/background/1.png",
            "assets/background/2.png",
            "assets/background/3.png",
            "assets/background/4.png",
            "assets/background/5.png",
            "assets/background/6.png",
    };

    public static int currentIndex = 0;

    public static double sizeW;
    public static double sizeH;

    /**
     * Changes the background image displayed on the specified pane.
     *
     * @param pane The Pane where the background image will be displayed.
     */
    public static void changeBackgroundImage(Pane pane) {
        if (currentIndex == -1) {
            currentIndex = backgroundimages.length - 1;
        } else if (currentIndex > backgroundimages.length - 1) {
            currentIndex = 0;
        }
        String imagePath = backgroundimages[currentIndex];
        Image backgroundImage = new Image(imagePath);
        ImageView backgroundObj = new ImageView(backgroundImage);
        backgroundObj.setFitWidth(backgroundImage.getWidth() * SCALE);
        backgroundObj.setFitHeight(backgroundImage.getHeight() * SCALE);
        sizeW = backgroundImage.getWidth() * SCALE;
        sizeH = backgroundImage.getHeight() * SCALE;

        ImageView backgroundObj2 = new ImageView(backgroundImage);
        backgroundObj2.setFitWidth(backgroundImage.getWidth() * SCALE);
        backgroundObj2.setFitHeight(backgroundImage.getHeight() * SCALE);
        backgroundObj2.setLayoutX(-sizeW);

        ImageView backgroundObj3 = new ImageView(backgroundImage);
        backgroundObj3.setFitWidth(backgroundImage.getWidth() * SCALE);
        backgroundObj3.setFitHeight(backgroundImage.getHeight() * SCALE);
        backgroundObj.setLayoutX(sizeW );

        backgroundObj.toBack();
        pane.getChildren().add(backgroundObj);
        pane.getChildren().add(backgroundObj2);
        pane.getChildren().add(backgroundObj3);

    }

}
