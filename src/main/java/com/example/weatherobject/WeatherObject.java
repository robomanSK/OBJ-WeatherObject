package com.example.weatherobject;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
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

    protected abstract void update(double elapsedSeconds);

    public void stopMoving() {
        moving = false;
    }

    public void resumeMoving() {
        moving = true;
    }

    public void moveLeft(double pixels) {
        x -= pixels;
        updatePosition();
    }

    protected void updatePosition() {
        setLayoutX(x);
        setLayoutY(y);
    }

    public void setExpirationHandler(
            Consumer<WeatherObject> expirationHandler
    ) {
        this.expirationHandler = expirationHandler;
    }

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

    public abstract void setObjectColor(Color color);

    public void dispose() {
        animationTimer.stop();
    }
}