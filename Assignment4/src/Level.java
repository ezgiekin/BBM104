import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Level class represents a level in the Duck Hunt game.
 * It manages the creation and initialization of ducks, updating the ammo count,
 * and handling user interactions with the ducks.
 */
public class Level extends DuckHunt {

    public static int ammo;
    private TranslateTransition slideInTransition;
    private TranslateTransition slideOutTransition;
    private TranslateTransition slideInTransitionForText;
    private TranslateTransition slideOutTransitionForText;
    private ArrayList<Duck> ducks = new ArrayList<>();
    private CustomText levelAmmoText;


    /**
     * Starts level 1 of the game by creating and initializing a duck, adding it to the list of ducks,
     * updating the ammo count, and making the duck fly horizontally.
     *
     * @param pane  The Pane where the duck will be displayed.
     * @param scene The Scene associated with the game screen.
     */
    public void level1(Pane pane, Scene scene) {
        Duck duck1 = new Duck("black");
        ducks.add(duck1);
        Duck.duckCount += 1;
        ammo = Duck.duckCount * 3;
        duck1.flyHorizontal(pane, scene, 1, 66 * SCALE);
    }

    /**
     * Starts level 2 of the game by creating and initializing a blue duck, adding it to the list of ducks,
     * updating the ammo count, and making the duck fly diagonally.
     *
     * @param pane  The Pane where the duck will be displayed.
     * @param scene The Scene associated with the game screen.
     */
    public void level2(Pane pane, Scene scene) {
        Duck duck1 = new Duck("blue");
        ducks.add(duck1);
        Duck.duckCount += 1;
        ammo = Duck.duckCount * 3;
        duck1.flyDiagonal(pane, scene, -1, -1, -133 * SCALE);
    }

    /**
     * Starts level 3 of the game by creating and initializing a red duck and a blue duck, adding them to the list of ducks,
     * updating the ammo count, and making the ducks fly horizontally.
     *
     * @param pane  The Pane where the ducks will be displayed.
     * @param scene The Scene associated with the game screen.
     */
    public void level3(Pane pane, Scene scene) {
        Duck duck1 = new Duck("red");
        Duck duck2 = new Duck("blue");
        ducks.add(duck1);
        ducks.add(duck2);
        Duck.duckCount += 2;
        ammo = Duck.duckCount * 3;
        duck1.flyHorizontal(pane, scene, 1, 17 * SCALE);
        duck2.flyHorizontal(pane, scene, -1, 83 * SCALE);
    }

    /**
     * Starts level 4 of the game by creating and initializing a black duck and a blue duck, adding them to the list of ducks,
     * updating the ammo count, and making the ducks fly diagonally.
     *
     * @param pane  The Pane where the ducks will be displayed.
     * @param scene The Scene associated with the game screen.
     */
    public void level4(Pane pane, Scene scene) {
        Duck duck1 = new Duck("black");
        Duck duck2 = new Duck("blue");
        ducks.add(duck1);
        ducks.add(duck2);
        Duck.duckCount += 2;
        ammo = Duck.duckCount * 3;
        duck1.flyDiagonal(pane, scene, -1, -1, -133 * SCALE);
        duck2.flyDiagonal(pane, scene, 1, -1, -200 * SCALE);
    }

    /**
     * Starts level 5 of the game by creating and initializing a black duck, a blue duck, and a red duck, adding them to the list of ducks,
     * updating the ammo count, and making the ducks fly in different directions.
     *
     * @param pane  The Pane where the ducks will be displayed.
     * @param scene The Scene associated with the game screen.
     */
    public void level5(Pane pane, Scene scene) {
        Duck duck1 = new Duck("black");
        Duck duck2 = new Duck("blue");
        Duck duck3 = new Duck("red");
        ducks.add(duck1);
        ducks.add(duck2);
        ducks.add(duck3);
        Duck.duckCount += 3;
        ammo = Duck.duckCount * 3;
        duck1.flyHorizontal(pane, scene, 1, 17 * SCALE);
        duck2.flyHorizontal(pane, scene, -1, 50 * SCALE);
        duck3.flyDiagonal(pane, scene, 1, -1, -70 * SCALE);
    }

    /**
     * Starts level 6 of the game by creating and initializing a blue duck, a red duck, and a black duck, adding them to the list of ducks,
     * updating the ammo count, and making the ducks fly in different directions.
     *
     * @param pane  The Pane where the ducks will be displayed.
     * @param scene The Scene associated with the game screen.
     */
    public void level6(Pane pane, Scene scene) {
        Duck duckk = new Duck("blue");
        Duck ducke = new Duck("red");
        Duck duckd = new Duck("black");
        ducks.add(duckk);
        ducks.add(ducke);
        ducks.add(duckd);
        Duck.duckCount += 3;
        ammo = Duck.duckCount * 3;
        duckk.flyDiagonal(pane, scene, 1, 1, 33 * SCALE);
        ducke.flyDiagonal(pane, scene, 1, -1, -53 * SCALE);
        duckd.flyDiagonal(pane, scene, -1, -1, -83 * SCALE);
    }

    /**
     * Checks if a mouse event hit any of the ducks on the screen.
     *
     * @param event The MouseEvent to check.
     * @param pane  The Pane containing the ducks.
     * @return Returns 1 if a duck was hit, -1 otherwise.
     */
    public int isHit(MouseEvent event, Pane pane) {
        int isHit = -1;
        if (ducks.stream().anyMatch(n -> n.getBirdView().getBoundsInParent().contains(event.getX() - pane.getTranslateX(), event.getY()))) {
            isHit = 1;
        }
        return isHit;
    }

    /**
     * Determines which ducks were hit by a mouse event on the screen.
     *
     * @param event The MouseEvent to check.
     * @param pane  The Pane containing the ducks.
     * @return Returns an ArrayList of Duck objects that were hit by the event.
     */
    public ArrayList<Duck> whichDuck(MouseEvent event, Pane pane) {
        ArrayList<Duck> hitDucks = new ArrayList<>();
        for (Duck d : ducks) {
            if (d.getBirdView().getBoundsInParent().contains(event.getX() - pane.getTranslateX(), event.getY())) {
                hitDucks.add(d);
            }
        }
        return hitDucks;
    }

    /**
     * Plays the levels of the game.
     *
     * @param pane        The Pane where the game is displayed.
     * @param scene       The Scene where the game is rendered.
     * @param mediaPlayer The MediaPlayer for playing background music.
     * @throws NoSuchMethodException     If a specified method cannot be found.
     * @throws InvocationTargetException If an exception occurs while invoking a method.
     * @throws IllegalAccessException    If access to a method is denied.
     */
    public void playLevels(Pane pane, Scene scene, MediaPlayer mediaPlayer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Method> levels = new ArrayList<>();
        levels.add(Level.class.getMethod("level1", Pane.class, Scene.class));
        levels.add(Level.class.getMethod("level2", Pane.class, Scene.class));
        levels.add(Level.class.getMethod("level3", Pane.class, Scene.class));
        levels.add(Level.class.getMethod("level4", Pane.class, Scene.class));
        levels.add(Level.class.getMethod("level5", Pane.class, Scene.class));
        levels.add(Level.class.getMethod("level6", Pane.class, Scene.class));

        AtomicInteger index = new AtomicInteger(1);
        levels.get(0).invoke(this, pane, scene);
        Foreground.setForeground(pane);


        levelAmmoText = new CustomText("Level " + index.get() + "/6                    Ammo Left: " + ammo,
                pane, scene, Color.ORANGE, "Verdana", 7 * SCALE, 50 * SCALE, -97 * SCALE
        );
        levelAmmoText.addText();

        // horizontal scrolling functionality is done here
        slideInTransitionForText = new TranslateTransition(Duration.seconds(3.5), levelAmmoText.getText());
        slideInTransitionForText.setToX(scene.getWidth());
        slideInTransitionForText.setInterpolator(Interpolator.LINEAR);

        slideOutTransitionForText = new TranslateTransition(Duration.seconds(3.5), levelAmmoText.getText());
        slideOutTransitionForText.setToX(-scene.getWidth());
        slideOutTransitionForText.setInterpolator(Interpolator.LINEAR);

        slideInTransition = new TranslateTransition(Duration.seconds(3.5), pane);
        slideInTransition.setToX(-scene.getWidth());
        slideInTransition.setInterpolator(Interpolator.LINEAR);

        slideOutTransition = new TranslateTransition(Duration.seconds(3.5), pane);
        slideOutTransition.setToX(scene.getWidth());
        slideOutTransition.setInterpolator(Interpolator.LINEAR);

        double scrollThreshold = scene.getWidth() * 0.05;

        scene.setOnMouseMoved(event1 -> {
            double mouseX = event1.getX();
            if ((ammo > 0 && Duck.duckCount > 0)) {
                if (mouseX < scene.getWidth() - scrollThreshold) {
                    slideOutTransition.stop();
                    slideInTransition.stop();
                    slideOutTransitionForText.stop();
                    slideInTransitionForText.stop();
                }
                if (mouseX > scrollThreshold) {
                    slideOutTransition.stop();
                    slideOutTransitionForText.stop();
                    slideInTransition.stop();
                    slideInTransitionForText.stop();
                }
                if (mouseX < scrollThreshold) {
                    slideOutTransition.stop();
                    slideOutTransitionForText.stop();
                    slideOutTransition.play();
                    slideOutTransitionForText.play();

                } else if (mouseX > scene.getWidth() - scrollThreshold) {
                    slideInTransition.stop();
                    slideInTransitionForText.stop();
                    slideInTransition.play();
                    slideInTransitionForText.play();

                }
            }
        });

        //if mouse exits game window stop scrolling
        scene.setOnMouseExited(event -> {
            slideOutTransition.stop();
            slideInTransition.stop();
            slideOutTransitionForText.stop();
            slideInTransitionForText.stop();
        });

        // checks levels ongoing situation after gunshots
        scene.setOnMouseClicked(event -> {
            if (ammo > 0 && Duck.duckCount>0) {
                if (Duck.duckCount > 0) {
                    ammo -= 1;
                    levelAmmoText.setCustomText("Level " + index.get() + "/6                    Ammo Left: " + ammo);
                    CustomMediaPlayer customMediaPlayer = new CustomMediaPlayer("assets/effects/Gunshot.mp3", 1);
                    customMediaPlayer.mediaPlay();
                }
                if (isHit(event, pane) == 1) {
                    for (Duck d : whichDuck(event, pane)) {
                        if (!d.isGotHit()) {
                            d.duckFalls(scene);
                            d.setGotHit(true);
                            d.setIsPlaying(true);
                            Duck.duckCount -= 1;
                        }
                    }
                }
                if (ammo >= 0 && Duck.duckCount == 0 && index.get() <= 5) {
                    CustomText winText;
                    winText = new CustomText("YOU WIN!",
                            pane, scene3, Color.ORANGE, "Verdana", 14 * SCALE, 0 - pane.getTranslateX(), 0
                    );

                    winText.addText();

                    CustomText contText = new CustomText("Press ENTER to play next level",
                            pane, scene3, Color.ORANGE, "Verdana", 14 * SCALE, 0 - pane.getTranslateX(), winText.getText().getLayoutBounds().getHeight()
                    );
                    contText.addText();
                    contText.flashText();

                    CustomMediaPlayer customMediaPlayer = new CustomMediaPlayer("assets/effects/LevelCompleted.mp3", 1);
                    customMediaPlayer.mediaPlay();

                    // if the duck gets hit near the scroll areas stop scrolling
                    slideOutTransitionForText.stop();
                    slideOutTransition.stop();
                    slideInTransitionForText.stop();
                    slideInTransition.stop();

                    scene3.setOnKeyPressed(event1 -> { // skip to next level
                        if (event1.getCode() == KeyCode.ENTER) {
                            if (Duck.duckCount == 0) {
                                try {
                                    customMediaPlayer.getMediaPlayer().stop();
                                    for (Duck d : ducks) {
                                        pane.getChildren().remove(d.getBirdView());
                                        if (d.isPlaying()) {
                                            d.getDuckFallsPlayer().stop();
                                        }

                                    }
                                    ducks.clear();
                                    if (index.get() <= 5) {
                                        levels.get(index.get()).invoke(this, pane, scene);
                                        Foreground.setForeground(pane);
                                        index.getAndIncrement();
                                        pane.getChildren().remove(winText.getText());
                                        pane.getChildren().remove(contText.getText());
                                        pane.setTranslateX(0.0);
                                        levelAmmoText.getText().setTranslateX(0.0);
                                        levelAmmoText.setCustomText("Level " + index.get() + "/6                    Ammo Left: " + ammo);
                                    }
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }

                if ((ammo == 0 && Duck.duckCount > 0) || (index.get() == 6 && ammo >= 0 && Duck.duckCount == 0)) {
                    CustomText gameOverText;
                    CustomMediaPlayer customMediaPlayer;

                    if ((ammo == 0 && Duck.duckCount > 0)) {
                        slideOutTransition.stop();
                        slideInTransition.stop();
                        slideOutTransitionForText.stop();
                        slideInTransitionForText.stop();
                        gameOverText = new CustomText("GAME OVER!",
                                pane, scene3, Color.ORANGE, "Verdana", 14 * SCALE, 0 - pane.getTranslateX(), 0
                        );

                        customMediaPlayer = new CustomMediaPlayer("assets/effects/GameOver.mp3", 1);
                        customMediaPlayer.mediaPlay();

                    } else if (index.get() == 6 && Duck.duckCount == 0 && ammo >= 0) {
                        slideOutTransition.stop();
                        slideInTransition.stop();
                        slideOutTransitionForText.stop();
                        slideInTransitionForText.stop();
                        gameOverText = new CustomText("You have completed the game!",
                                pane, scene3, Color.ORANGE, "Verdana", 14 * SCALE, 0 - pane.getTranslateX(), 0
                        );

                        customMediaPlayer = new CustomMediaPlayer("assets/effects/GameCompleted.mp3", 1);
                        customMediaPlayer.mediaPlay();
                    } else {
                        gameOverText = null;
                        customMediaPlayer = null;
                    }
                    gameOverText.addText();

                    CustomText contText = new CustomText("Press ENTER to play again \n        Press ESC to exit",
                            pane, scene3, Color.ORANGE, "Verdana", 14 * SCALE, 0 - pane.getTranslateX(), gameOverText.getText().getLayoutBounds().getHeight()
                    );
                    contText.addText();
                    contText.flashText();

                    scene3.setOnKeyPressed(event1 -> { //interactions after if game is over or all levels are completed
                        if (event1.getCode() == KeyCode.ESCAPE) {
                            Foreground.cleanForeground(pane);
                            window.setScene(scene1);
                            mediaPlayer.play();
                            pane.getChildren().remove(gameOverText.getText());
                            pane.getChildren().remove(contText.getText());
                            pane.getChildren().remove(levelAmmoText.getText());
                            for (Duck duck : ducks) {
                                pane.getChildren().remove(duck.getBirdView());
                                if (duck.isPlaying()) {
                                    duck.getDuckFallsPlayer().stop();
                                }
                            }
                            Duck.duckCount = 0;
                            pane.setTranslateX(0.0);
                            levelAmmoText.getText().setTranslateX(0.0);
                            customMediaPlayer.getMediaPlayer().stop();
                        } else if (event1.getCode() == KeyCode.ENTER) {
                            pane.getChildren().remove(gameOverText.getText());
                            pane.getChildren().remove(contText.getText());
                            for (Duck duck : ducks) {
                                pane.getChildren().remove(duck.getBirdView());
                                if (duck.isPlaying()) {
                                    duck.getDuckFallsPlayer().stop();
                                }
                            }
                            Duck.duckCount = 0;
                            ammo = 0;
                            index.set(1);
                            try {
                                pane.setTranslateX(0.0);
                                levelAmmoText.getText().setTranslateX(0.0);
                                levels.get(0).invoke(this, pane, scene);
                                Foreground.setForeground(pane);
                                levelAmmoText.setCustomText("Level " + index.get() + "/6                    Ammo Left: " + ammo);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                            customMediaPlayer.getMediaPlayer().stop();

                        }

                    });
                }

            }
        });


    }
}
