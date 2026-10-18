package com.example.weatherobject;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Snowflake extends WeatherObject {

    private static final double FALL_SPEED = 70;
    private static final double SIDE_AMPLITUDE = 35;
    private static final double SIDE_FREQUENCY = 2.5;

    private final Group snowflakeGraphic;

    private double elapsedTime = 0;
    private final double initialX;

    public Snowflake(
            double x,
            double y,
            double paneWidth,
            double paneHeight,
            Color color
    ) {
        super(x, y, paneWidth, paneHeight);

        initialX = x;

        /*
         * Grafika sa vytvára až po dokončení konštruktora rodiča.
         */
        snowflakeGraphic = createGraphic(color);
        getChildren().add(snowflakeGraphic);
    }

    private Group createGraphic(Color color) {
        Group graphic = new Group();

        Line verticalLine = new Line(0, -8, 0, 8);
        Line horizontalLine = new Line(-8, 0, 8, 0);
        Line diagonalLine1 = new Line(-6, -6, 6, 6);
        Line diagonalLine2 = new Line(-6, 6, 6, -6);

        Line[] lines = {
                verticalLine,
                horizontalLine,
                diagonalLine1,
                diagonalLine2
        };

        for (Line line : lines) {
            line.setStroke(color);
            line.setStrokeWidth(2);
        }

        graphic.getChildren().addAll(lines);

        return graphic;
    }

    @Override
    protected void update(double elapsedSeconds) {
        elapsedTime += elapsedSeconds;

        y += FALL_SPEED * elapsedSeconds;

        x = initialX
                + Math.sin(elapsedTime * SIDE_FREQUENCY)
                * SIDE_AMPLITUDE;

        updatePosition();

        if (y > paneHeight + 20) {
            expire();
        }
    }

    @Override
    public void setObjectColor(Color color) {
        for (Node node : snowflakeGraphic.getChildren()) {
            if (node instanceof Shape shape) {
                shape.setStroke(color);
                shape.setFill(color);
            }
        }
    }
}