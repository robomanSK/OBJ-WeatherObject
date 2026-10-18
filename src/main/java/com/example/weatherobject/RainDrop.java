package com.example.weatherobject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RainDrop extends WeatherObject {

    private static final double SPEED = 260;

    private final Line raindropGraphic;

    public RainDrop(
            double x,
            double y,
            double paneWidth,
            double paneHeight,
            Color color
    ) {
        super(x, y, paneWidth, paneHeight);

        raindropGraphic = new Line(0, 0, 0, 18);
        raindropGraphic.setStroke(color);
        raindropGraphic.setStrokeWidth(3);

        getChildren().add(raindropGraphic);
    }

    @Override
    protected void update(double elapsedSeconds) {
        y += SPEED * elapsedSeconds;
        updatePosition();

        if (y > paneHeight + 20) {
            expire();
        }
    }

    @Override
    public void setObjectColor(Color color) {
        raindropGraphic.setStroke(color);
    }
}