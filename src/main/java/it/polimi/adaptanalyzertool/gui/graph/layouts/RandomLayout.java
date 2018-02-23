package it.polimi.adaptanalyzertool.gui.graph.layouts;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import it.polimi.adaptanalyzertool.gui.graph.Graph;
import it.polimi.adaptanalyzertool.gui.graph.Layout;

import java.util.Random;

public class RandomLayout implements Layout {

    private Graph graph;

    private Random rnd = new Random();

    public RandomLayout(Graph graph) {
        this.graph = graph;
    }

    public void addCells() {

        for (Cell cell : graph.getModel().getCells().values()) {

            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;

            cell.relocate(x, y);
            graph.getCanvas().getChildren().add(cell);
        }
    }

    @Override
    public void addEdges() {
        graph.getCanvas().getChildren().addAll(graph.getModel().getEdges());
    }
}
