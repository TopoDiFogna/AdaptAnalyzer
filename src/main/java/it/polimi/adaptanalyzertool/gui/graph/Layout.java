package it.polimi.adaptanalyzertool.gui.graph;

/**
 * <p>Contains the method to dispose cells and edges in the graph.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public interface Layout {
    /**
     * Adds all cells to the graph.
     */
    void addCells();

    /**
     * Adds all edges and/or arrows to the graph.
     */
    void addEdges();
}
