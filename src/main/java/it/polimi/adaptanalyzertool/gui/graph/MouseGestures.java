package it.polimi.adaptanalyzertool.gui.graph;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseGestures {

    private final DragContext dragContext = new DragContext();

    Graph graph;
    private EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {

    };
    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {

        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

            double scale = graph.getScale();

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
            double scale = graph.getScale();

            offsetX /= scale;
            offsetY /= scale;

            node.relocate(offsetX, offsetY);

        }
    };

    public MouseGestures(Graph graph) {
        this.graph = graph;
    }

    public void makeDraggable(final Node node) {

        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
        node.setOnMouseReleased(onMouseReleasedEventHandler);
    }

    private class DragContext {

        double x;
        double y;

    }
}
