package it.polimi.adaptanalyzertool.gui.graph;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Scale;

public class ZoomableScrollPane extends ScrollPane {

    /**
     * How much we zoom in or out per scroll.
     */
    private final double DELTA = 0.1;
    private Scale scaleTransform;
    private double scaleValue = 1.0;

    public ZoomableScrollPane(Node content) {

        Group zoomGroup = new Group();
        zoomGroup.getChildren().add(content);

        Group contentGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);

        setContent(contentGroup);

        scaleTransform = new Scale(scaleValue, scaleValue, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);

        contentGroup.setOnScroll(new ZoomHandler());
    }

    public double getScaleValue() {
        return scaleValue;
    }

    /**
     * Zooms to the required scale.
     *
     * @param scaleValue how much to scale when zooming.
     */
    private void zoomTo(double scaleValue) {

        this.scaleValue = scaleValue;

        scaleTransform.setX(scaleValue);
        scaleTransform.setY(scaleValue);
    }

    /**
     * Reset the zoom to the default value.
     */
    public void resetZoom() {
        scaleValue = 1;
        zoomTo(scaleValue);
    }

    private class ZoomHandler implements EventHandler<ScrollEvent> {

        @Override
        public void handle(ScrollEvent scrollEvent) {
            if (scrollEvent.getDeltaY() < 0) {
                scaleValue -= DELTA;
            } else {
                scaleValue += DELTA;
            }

            zoomTo(scaleValue);

            scrollEvent.consume();
        }
    }
}
