package cz.vianel.artwork.fx;

import java.util.Arrays;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// From: https://stackoverflow.com/questions/26298873/resizable-and-movable-rectangle

public final class ResisableRectangle {

    private ResisableRectangle() {}

    public static Rectangle create(double x, double y, double width, double height, Color dragersColor, double handleSize) {
        Rectangle rect = new Rectangle(x, y, width, height);

        // top left resize handle:
        Rectangle resizeHandleNW = new Rectangle(handleSize, handleSize);
        resizeHandleNW.setFill(dragersColor);
        // bind to top left corner of Rectangle:
        resizeHandleNW.xProperty().bind(rect.xProperty());
        resizeHandleNW.yProperty().bind(rect.yProperty());

        // bottom right resize handle:
        Rectangle resizeHandleSE = new Rectangle(handleSize, handleSize);
        resizeHandleSE.setFill(dragersColor);
        // bind to bottom right corner of Rectangle:
        resizeHandleSE.xProperty().bind(rect.xProperty().add(rect.widthProperty().subtract(handleSize)));
        resizeHandleSE.yProperty().bind(rect.yProperty().add(rect.heightProperty().subtract(handleSize)));

        // move handle:
        Rectangle moveHandle = new Rectangle(handleSize, handleSize);
        moveHandle.setFill(dragersColor);
        // bind to bottom center of Rectangle:
        moveHandle.xProperty().bind(rect.xProperty().add(rect.widthProperty().divide(2)));
        moveHandle.yProperty().bind(rect.yProperty().add(rect.heightProperty().divide(2)));

        // force circles to live in same parent as rectangle:
        rect.parentProperty().addListener((obs, oldParent, newParent) -> {
            for (Rectangle c : Arrays.asList(resizeHandleNW, resizeHandleSE, moveHandle)) {
                Pane currentParent = (Pane)c.getParent();
                if (currentParent != null) {
                    currentParent.getChildren().remove(c);
                }
                ((Pane)newParent).getChildren().add(c);
            }
        });

        Wrapper<Point2D> mouseLocation = new Wrapper<>();

        setUpDragging(resizeHandleNW, mouseLocation);
        setUpDragging(resizeHandleSE, mouseLocation);
        setUpDragging(moveHandle, mouseLocation);

        // top left
        resizeHandleNW.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();

                double newX = rect.getX() + deltaX;
                if (newX >= 0 && newX <= rect.getX() + rect.getWidth()) {
                    rect.setX(newX);
                    rect.setWidth(rect.getWidth() - deltaX);
                }

                double newY = rect.getY() + deltaY;
                if (newY >= 0 && newY <= rect.getY() + rect.getHeight()) {
                    rect.setY(newY);
                    rect.setHeight(rect.getHeight() - deltaY);
                }

                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });

        // bottom right
        resizeHandleSE.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();

                double newMaxX = rect.getX() + rect.getWidth() + deltaX;
                if (newMaxX >= rect.getX() && newMaxX <= rect.getParent().getBoundsInLocal().getWidth()) {
                    rect.setWidth(rect.getWidth() + deltaX);
                }

                double newMaxY = rect.getY() + rect.getHeight() + deltaY;
                if (newMaxY >= rect.getY() && newMaxY <= rect.getParent().getBoundsInLocal().getHeight()) {
                    rect.setHeight(rect.getHeight() + deltaY);
                }

                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });

        // center
        moveHandle.setOnMouseDragged(event -> {
            if (mouseLocation.value != null) {
                double deltaX = event.getSceneX() - mouseLocation.value.getX();
                double deltaY = event.getSceneY() - mouseLocation.value.getY();

                double newX = rect.getX() + deltaX;
                double newMaxX = newX + rect.getWidth();
                if (newX >= 0 && newMaxX <= rect.getParent().getBoundsInLocal().getWidth()) {
                    rect.setX(newX);
                }

                double newY = rect.getY() + deltaY;
                double newMaxY = newY + rect.getHeight();
                if (newY >= 0 && newMaxY <= rect.getParent().getBoundsInLocal().getHeight()) {
                    rect.setY(newY);
                }

                mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
            }

        });

        return rect;
    }

    private static void setUpDragging(Rectangle rect, Wrapper<Point2D> mouseLocation) {

        rect.setOnDragDetected(event -> {
            rect.getParent().setCursor(Cursor.CLOSED_HAND);
            mouseLocation.value = new Point2D(event.getSceneX(), event.getSceneY());
        });

        rect.setOnMouseReleased(event -> {
            rect.getParent().setCursor(Cursor.DEFAULT);
            mouseLocation.value = null;
        });
    }

    static class Wrapper<T> { T value; }
}