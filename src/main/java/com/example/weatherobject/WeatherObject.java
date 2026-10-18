package com.example.weatherobject;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public abstract class WeatherObject extends Group {

    protected double x;
    protected double y;

    protected final double paneWidth;
    protected final double paneHeight;

    private boolean moving = true;
    private boolean expired = false;

    private final AnimationTimer animationTimer;

    private Consumer<WeatherObject> expirationHandler;

    public WeatherObject(
            double x,
            double y,
            double paneWidth,
            double paneHeight
    ) {
        this.x = x;
        this.y = y;
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;

        setLayoutX(x);
        setLayoutY(y);

        /*
         * Dôležité:
         * createGraphic() sa tu už nevolá.
         * Odvodená trieda najprv vytvorí grafiku
         * a následne ju pridá do getChildren().
         */
        animationTimer = new AnimationTimer() {

            private long previousTime = 0;

            @Override
            public void handle(long now) {
                if (previousTime == 0) {
                    previousTime = now;
                    return;
                }

                double elapsedSeconds =
                        (now - previousTime) / 1_000_000_000.0;

                previousTime = now;

                if (moving && !expired) {
                    update(elapsedSeconds);
                }
            }
        };

        animationTimer.start();
    }

    /**
     * Pohyb konkrétneho typu objektu.
     */
    protected abstract void update(double elapsedSeconds);

    /**
     * Zastavenie pohybu.
     */
    public void stopMoving() {
        moving = false;
    }

    /**
     * Opätovné spustenie pohybu.
     */
    public void resumeMoving() {
        moving = true;
    }

    /**
     * Posun objektu doľava.
     */
    public void moveLeft(double pixels) {
        x -= pixels;
        updatePosition();
    }

    /**
     * Aktualizácia pozície na obrazovke.
     */
    protected void updatePosition() {
        setLayoutX(x);
        setLayoutY(y);
    }

    /**
     * Nastavenie callbacku pri zániku objektu.
     */
    public void setExpirationHandler(
            Consumer<WeatherObject> expirationHandler
    ) {
        this.expirationHandler = expirationHandler;
    }

    /**
     * Objekt zanikne a oznámi to controlleru.
     */
    protected void expire() {
        if (expired) {
            return;
        }

        expired = true;
        animationTimer.stop();

        if (expirationHandler != null) {
            expirationHandler.accept(this);
        }
    }

    /**
     * Každá trieda si nastaví vlastnú farbu.
     */
    public abstract void setObjectColor(Color color);

    /**
     * Uvoľnenie animácie.
     */
    public void dispose() {
        animationTimer.stop();
    }
}