package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.graph.CellType;
import it.polimi.adaptanalyzertool.gui.graph.Graph;
import it.polimi.adaptanalyzertool.gui.graph.Layout;
import it.polimi.adaptanalyzertool.gui.graph.Model;
import it.polimi.adaptanalyzertool.gui.graph.layouts.RandomLayout;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.scene.layout.BorderPane;

public class GraphController {

    private Architecture architecture;
    private BorderPane root;
    private Graph graph;

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void inizitalize() {
        graph = new Graph();
        root.setCenter(graph.getScrollPane());

        //TODO remove this after testing
        addGraphComponents();
        Layout layout = new RandomLayout(graph);
        layout.execute();
    }


    private void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.RECTANGLE);
        model.addCell("Cell C", CellType.RECTANGLE);
        model.addCell("Cell D", CellType.TRIANGLE);
        model.addCell("Cell E", CellType.TRIANGLE);
        model.addCell("Cell F", CellType.RECTANGLE);
        model.addCell("Cell G", CellType.RECTANGLE);

        model.addEdge("Cell A", "Cell B");
        model.addEdge("Cell A", "Cell C");
        model.addEdge("Cell B", "Cell C");
        model.addEdge("Cell C", "Cell D");
        model.addEdge("Cell B", "Cell E");
        model.addEdge("Cell D", "Cell F");
        model.addEdge("Cell D", "Cell G");

        graph.endUpdate();

    }
}
