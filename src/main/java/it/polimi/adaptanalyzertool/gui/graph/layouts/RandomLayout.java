package it.polimi.adaptanalyzertool.gui.graph.layouts;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import it.polimi.adaptanalyzertool.gui.graph.Graph;
import it.polimi.adaptanalyzertool.gui.graph.Layout;

import java.util.List;
import java.util.Random;

public class RandomLayout extends Layout {

    private Graph graph;

    private Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;
    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;

            cell.relocate(x, y);

        }

    }

}
