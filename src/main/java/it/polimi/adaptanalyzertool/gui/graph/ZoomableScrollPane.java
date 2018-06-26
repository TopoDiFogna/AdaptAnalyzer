package it.polimi.adaptanalyzertool.gui.graph;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 * <p>This class was created because JavaFX doesn't include a scroll pane capable of zooming.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ZoomableScrollPane extends ScrollPane {

    private double scaleValue = 1.0;
    private Scale scaleTransform;

    ZoomableScrollPane(Pane content) {

        Group contentGroup = new Group();
        Group zoomGroup = new Group();

        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(content);

        setContent(contentGroup);

        scaleTransform = new Scale(scaleValue, scaleValue, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);

        contentGroup.setOnScroll(new ZoomHandler());
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
    void resetZoom() {
        scaleValue = 1;
        zoomTo(scaleValue);
    }

    double getScaleValue() {
        return scaleValue;
    }

    private class ZoomHandler implements EventHandler<ScrollEvent> {

        @Override
        public void handle(ScrollEvent scrollEvent) {
            double delta = 0.1;
            if (scrollEvent.getDeltaY() < 0) {
                scaleValue -= delta;
            } else {
                scaleValue += delta;
            }

            zoomTo(scaleValue);

            scrollEvent.consume();
        }
    }
}
