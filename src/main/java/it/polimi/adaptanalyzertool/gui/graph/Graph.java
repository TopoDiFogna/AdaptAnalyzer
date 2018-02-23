package it.polimi.adaptanalyzertool.gui.graph;

import javafx.scene.layout.Pane;

public class Graph {

    private Model model;
    private Pane canvas;
    private ZoomableScrollPane scrollPane;
    private MouseGestures mouseGestures;


    public Graph() {
        model = new Model();
        mouseGestures = new MouseGestures(this);

        canvas = new Pane();

        scrollPane = new ZoomableScrollPane(canvas);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
    }

    public ZoomableScrollPane getScrollPane() {
        return scrollPane;
    }

    public Model getModel() {
        return model;
    }

    public Pane getCanvas() {
        return canvas;
    }

    public void endUpdate() {
        for (Cell cell : model.getCells().values()) {
            mouseGestures.makeDraggable(cell);
        }
    }
}
