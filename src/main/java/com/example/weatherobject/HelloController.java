package com.example.weatherobject;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController {

    private static final double PANE_WIDTH = 800;
    private static final double PANE_HEIGHT = 500;

    @FXML
    private Pane drawingPane;

    private final List<WeatherObject> weatherObjects =
            new ArrayList<>();

    private final Random random = new Random();

    private boolean rainStopped = false;

    /**
     * Pridá oblak na náhodnú pozíciu v hornej časti plochy.
     */
    @FXML
    private void addCloud() {
        double x = random.nextDouble() * PANE_WIDTH;
        double y = 30 + random.nextDouble() * 70;

        Cloud cloud = new Cloud(
                x,
                y,
                PANE_WIDTH,
                PANE_HEIGHT,
                Color.LIGHTGRAY
        );

        addWeatherObject(cloud);
    }

    /**
     * Pridá dažďovú kvapku na náhodnú pozíciu.
     */
    @FXML
    private void addRaindrop() {
        double x = random.nextDouble() * PANE_WIDTH;
        double y = random.nextDouble() * 150;

        RainDrop raindrop = new RainDrop(
                x,
                y,
                PANE_WIDTH,
                PANE_HEIGHT,
                Color.DEEPSKYBLUE
        );

        addWeatherObject(raindrop);
    }

    /**
     * Pridá snehovú vločku na náhodnú pozíciu.
     */
    @FXML
    private void addSnowflake() {
        double x = random.nextDouble() * PANE_WIDTH;
        double y = random.nextDouble() * 100;

        Snowflake snowflake = new Snowflake(
                x,
                y,
                PANE_WIDTH,
                PANE_HEIGHT,
                Color.WHITE
        );

        addWeatherObject(snowflake);
    }

    /**
     * Zastaví alebo znovu spustí všetky dažďové kvapky.
     */
    @FXML
    private void stopRain() {
        rainStopped = !rainStopped;

        for (WeatherObject object : weatherObjects) {
            if (object instanceof RainDrop) {
                if (rainStopped) {
                    object.stopMoving();
                } else {
                    object.resumeMoving();
                }
            }
        }
    }

    /**
     * Zmení farbu všetkých oblakov na modrú.
     */
    @FXML
    private void makeCloudsBlue() {
        for (WeatherObject object : weatherObjects) {
            if (object instanceof Cloud) {
                object.setObjectColor(Color.BLUE);
            }
        }
    }

    /**
     * Posunie všetky objekty na ploche o 20 px doľava.
     */
    @FXML
    private void moveAllObjectsLeft() {
        for (WeatherObject object : weatherObjects) {
            object.moveLeft(20);
        }
    }

    /**
     * Pridá objekt do zoznamu a do grafickej plochy.
     */
    private void addWeatherObject(WeatherObject object) {
        object.setExpirationHandler(this::removeWeatherObject);

        weatherObjects.add(object);
        drawingPane.getChildren().add(object);

        /*
         * Ak je dážď práve zastavený, nová kvapka bude tiež zastavená.
         */
        if (rainStopped && object instanceof RainDrop) {
            object.stopMoving();
        }
    }

    /**
     * Odstráni objekt, ktorý opustil plochu.
     */
    private void removeWeatherObject(WeatherObject object) {
        weatherObjects.remove(object);
        drawingPane.getChildren().remove(object);
    }

    /**
     * Uvoľnenie animácií.
     */
    public void dispose() {
        for (WeatherObject object : weatherObjects) {
            object.dispose();
        }

        weatherObjects.clear();
    }
}