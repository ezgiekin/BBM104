import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The CustomText class extends the DuckHunt game and provides functionality to add and manipulate custom text on the game screen.
 */
public class CustomText extends DuckHunt {

    private Text text;
    private Pane pane;
    private Scene scene;
    private Color color;
    private String font;
    private double size;
    private double xLayout;
    private double yLayout;
    private String message;

    /**
     * Constructs a CustomText object with the specified parameters.
     *
     * @param message The text message to be displayed.
     * @param pane    The Pane where the text will be added.
     * @param scene   The Scene associated with the game screen.
     * @param color   The color of the text.
     * @param font    The font of the text.
     * @param size    The size of the text.
     * @param xLayout The x-coordinate layout position of the text.
     * @param yLayout The y-coordinate layout position of the text.
     */
    public CustomText(String message, Pane pane, Scene scene, Color color, String font, double size, double xLayout, double yLayout) {
        this.message = message;
        this.pane = pane;
        this.scene = scene;
        this.color = color;
        this.font = font;
        this.size = size;
        this.xLayout = xLayout;
        this.yLayout = yLayout;
    }

    /**
     * Adds the custom text to the specified pane.
     */
    public void addText() {
        text = new Text(message);
        text.setFill(color);
        text.setFont(Font.font(font, FontWeight.BOLD, size));
        text.setLayoutX(scene.getWidth() / 2 - this.text.getLayoutBounds().getWidth() / 2 + xLayout);
        text.setLayoutY(scene.getHeight() / 2 + this.text.getLayoutBounds().getHeight() / 4 + yLayout - 17 * SCALE);
        pane.getChildren().add(text);
    }

    /**
     * Flashes the custom text by making it visible and invisible in a cycle.
     */
    public void flashText() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(this.text.visibleProperty(), true)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(this.text.visibleProperty(), false)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(this.text.visibleProperty(), true)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Retrieves the Text object representing the custom text.
     *
     * @return The Text object.
     */
    public Text getText() {
        return text;
    }

    /**
     * Sets a new text message for the custom text.
     *
     * @param message The new text message.
     */
    public void setCustomText(String message) {
        text.setText(message);
    }


}
