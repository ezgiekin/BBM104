import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;



/**
 * The main class for the Duck Hunt game.
 */
public class DuckHunt extends Application {
    public static double SCALE = 3.00;
    public static double VOLUME = 0.025;
    public static Stage window;
    public static Scene scene1, scene2, scene3;
    private Level levels;
    private CustomMediaPlayer customMediaPlayer2;

    /**
     * The entry point of the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the game.
     *
     * @param primaryStage the primary stage for the game
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        window.setTitle("HUBBM Duck Hunt");
        Image favicon = new Image("assets/favicon/1.png");
        window.getIcons().add(favicon);

        CustomMediaPlayer customMediaPlayer = new CustomMediaPlayer("assets/effects/Title.mp3", MediaPlayer.INDEFINITE);
        customMediaPlayer.mediaPlay();

        // setting the first scene
        Image image = new Image("assets/welcome/1.png");
        double originalheight = image.getHeight();
        double originalwidth = image.getWidth();
        ImageView welcome = new ImageView(image);
        welcome.setFitWidth(originalwidth * SCALE);
        welcome.setFitHeight(originalheight * SCALE);

        Pane pane = new Pane();
        pane.getChildren().add(welcome);
        scene1 = new Scene(pane, originalwidth * SCALE, originalheight * SCALE);

        CustomText welcomeText = new CustomText("PRESS ENTER TO PLAY\n   PRESS ESC TO EXIT",
                pane, scene1, Color.ORANGE, "Verdana", 18 * SCALE, 0, 42 * SCALE
        );
        welcomeText.addText();
        welcomeText.flashText();


        //setting the second scene
        Pane selector = new Pane();
        scene2 = new Scene(selector, originalwidth * SCALE, originalheight * SCALE);

        CustomText selectorText = new CustomText("USE ARROW KEYS TO NAVIGATE\n       PRESS ENTER TO START\n           PRESS ESC TO EXIT",
                selector, scene2, Color.ORANGE, "Verdana", 7 * SCALE, 0, -95 * SCALE
        );


        //setting the default display for second scene(selection scene)
        CustomCursor customCursor = new CustomCursor();
        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Foreground.cleanForeground(pane);
                window.setScene(scene2);
                BackGround.currentIndex = 0;
                CustomCursor.currentIndex = 0;
                BackGround.changeBackgroundImage(selector);
                selectorText.addText();
                customCursor.changeCursorImage(selector);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        //setting the third scene (game scene)
        Pane game = new Pane();
        scene3 = new Scene(game, originalwidth * SCALE, originalheight * SCALE);
        game.setPrefSize(BackGround.sizeW * 3, BackGround.sizeH);


        // keyboard interactions for second scene(selection scene)
        AtomicBoolean isPLaying = new AtomicBoolean(false);
        scene2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                customMediaPlayer.getMediaPlayer().stop();
                customMediaPlayer2 = new CustomMediaPlayer("assets/effects/Intro.mp3", 1);
                if (!isPLaying.get()) {
                    customMediaPlayer2.mediaPlay();
                    isPLaying.set(true);
                }
                try {
                    customMediaPlayer2.getMediaPlayer().setOnEndOfMedia(() -> { //after intro effect ends
                        window.setScene(scene3);
                        BackGround.changeBackgroundImage(game);
                        game.setTranslateX(0);
                        scene3.setCursor(CustomCursor.setSelectedCursor());
                        scene3.setOnMouseEntered(event1 -> scene3.setCursor(CustomCursor.setSelectedCursor()));
                        levels = new Level();
                        isPLaying.set(false);
                        try {
                            levels.playLevels(game, scene3, customMediaPlayer.getMediaPlayer()); // game starts here
                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (NullPointerException ignored) {
                }

            } else if (event.getCode() == KeyCode.ESCAPE) {
                if (!isPLaying.get()) {
                    window.setScene(scene1);
                    BackGround.currentIndex = 0;
                    CustomCursor.currentIndex = 0;
                    BackGround.changeBackgroundImage(selector);
                    selectorText.addText();
                    customCursor.changeCursorImage(selector);
                }
            } else if (event.getCode() == KeyCode.RIGHT) {
                if (!isPLaying.get()) {
                    BackGround.currentIndex++;
                    BackGround.changeBackgroundImage(selector);
                    selectorText.addText();
                    customCursor.changeCursorImage(selector);
                }

            } else if (event.getCode() == KeyCode.LEFT) {
                if (!isPLaying.get()) {
                    BackGround.currentIndex--;
                    BackGround.changeBackgroundImage(selector);
                    selectorText.addText();
                    customCursor.changeCursorImage(selector);
                }

            } else if (event.getCode() == KeyCode.UP) {
                if (!isPLaying.get()) {
                    CustomCursor.currentIndex++;
                    customCursor.changeCursorImage(selector);
                }

            } else if (event.getCode() == KeyCode.DOWN) {
                if (!isPLaying.get()) {
                    CustomCursor.currentIndex--;
                    customCursor.changeCursorImage(selector);
                }

            }
        });

        window.setScene(scene1);
        window.show();

    }


}