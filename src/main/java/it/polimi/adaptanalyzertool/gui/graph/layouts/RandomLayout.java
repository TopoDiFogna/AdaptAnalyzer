package it.polimi.adaptanalyzertool.gui.graph.layouts;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import it.polimi.adaptanalyzertool.gui.graph.Graph;
import it.polimi.adaptanalyzertool.gui.graph.Layout;

import java.util.Random;

/**
 * <p>This class is in chard to display the cells in a random way inside the window.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class RandomLayout implements Layout {

    private Graph graph;

    private Random rnd = new Random();

    /**
     * <p>Initialize the layout.</p>
     *
     * @param graph the graph to be shown.
     */
    public RandomLayout(Graph graph) {
        this.graph = graph;
    }

    public void addCells() {

        for (Cell cell : graph.getModel().getCells().values()) {

            double x = rnd.nextDouble() * 1000;
            double y = rnd.nextDouble() * 1000;

            cell.relocate(x, y);
            graph.getCanvas().getChildren().add(cell);
        }
    }

    @Override
    public void addEdges() {
        graph.getCanvas().getChildren().addAll(graph.getModel().getEdges());
    }
}
