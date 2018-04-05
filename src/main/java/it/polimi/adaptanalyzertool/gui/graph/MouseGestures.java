package it.polimi.adaptanalyzertool.gui.graph;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * <p>Contains all the handlers for the mouse in order to zoom the scroll pane and move the nodes.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
class MouseGestures {

    private final DragContext dragContext = new DragContext();

    private Graph graph;

    private EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {

    };

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

            double scale = graph.getScrollPane().getScaleValue();

            dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
            dragContext.y = node.getBoundsInParent().getMinY() * scale - event.getScreenY();

        }
    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

            double offsetX = event.getScreenX() + dragContext.x;
            double offsetY = event.getScreenY() + dragContext.y;

            // adjust the offset in case we are zoomed
            double scale = graph.getScrollPane().getScaleValue();

            offsetX /= scale;
            offsetY /= scale;

            node.relocate(offsetX, offsetY);

        }
    };

    MouseGestures(Graph graph) {
        this.graph = graph;
    }

    void makeDraggable(final Node node) {

        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
    }

    private class DragContext {

        private double x;
        private double y;
    }
}
