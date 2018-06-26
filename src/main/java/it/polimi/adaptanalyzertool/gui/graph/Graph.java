package it.polimi.adaptanalyzertool.gui.graph;

import javafx.scene.layout.Pane;

/**
 * <p>This class is the main class of the graph; it contains what is needed to display and modify the graphical
 * visualization of the graph.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 * @see Model
 * @see ZoomableScrollPane
 * @see MouseGestures
 */
public class Graph {

    private Model model;
    private Pane canvas;
    private ZoomableScrollPane scrollPane;
    private MouseGestures mouseGestures;


    /**
     * Initializes the graph by creating the structure and the mouse gestures.
     */
    public Graph() {
        model = new Model();
        mouseGestures = new MouseGestures(this);

        canvas = new Pane();

        scrollPane = new ZoomableScrollPane(canvas);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
    }

    /**
     * <p>Returns the scrollpane that encloses the graph.</p>
     *
     * @return the {@link ZoomableScrollPane} that contains the graph.
     */
    public ZoomableScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * <p>Returns the model associated to this graph.</p>
     *
     * @return the model associated to this graph.
     */
    public Model getModel() {
        return model;
    }

    /**
     * <p>Returns the canvas that goes into the scrollpane.</p>
     *
     * @return the canvas that goes into the {@link ZoomableScrollPane}.
     */
    public Pane getCanvas() {
        return canvas;
    }

    /**
     * After adding all what is needed apply the gestures to the cell.
     */
    public void endUpdate() {
        for (Cell cell : model.getCells().values()) {
            mouseGestures.makeDraggable(cell);
        }
    }
}
