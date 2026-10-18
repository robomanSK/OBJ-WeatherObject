package com.example.weatherobject;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cloud extends WeatherObject {

    private static final double SPEED = 20;

    private final Group cloudGraphic;

    public Cloud(
            double x,
            double y,
            double paneWidth,
            double paneHeight,
            Color color
    ) {
        super(x, y, paneWidth, paneHeight);

        /*
         * Atribút je už inicializovaný až po zavolaní super().
         */
        cloudGraphic = createGraphic(color);
        getChildren().add(cloudGraphic);
    }

    private Group createGraphic(Color color) {
        Group graphic = new Group();

        Circle leftCircle = new Circle(-30, 5, 20);
        Circle middleCircle = new Circle(0, -10, 28);
        Circle rightCircle = new Circle(30, 5, 20);

        Rectangle body = new Rectangle(-50, 5, 100, 25);

        leftCircle.setFill(color);
        middleCircle.setFill(color);
        rightCircle.setFill(color);
        body.setFill(color);

        leftCircle.setStroke(Color.GRAY);
        middleCircle.setStroke(Color.GRAY);
        rightCircle.setStroke(Color.GRAY);
        body.setStroke(Color.GRAY);

        graphic.getChildren().addAll(
                body,
                leftCircle,
                middleCircle,
                rightCircle
        );

        return graphic;
    }

    @Override
    protected void update(double elapsedSeconds) {
        x += SPEED * elapsedSeconds;

        /*
         * Po opustení pravej strany sa oblak vráti zľava.
         */
        if (x > paneWidth + 60) {
            x = -60;
        }

        updatePosition();
    }

    @Override
    public void setObjectColor(Color color) {
        for (Node node : cloudGraphic.getChildren()) {
            if (node instanceof Shape shape) {
                shape.setFill(color);
            }
        }
    }
}