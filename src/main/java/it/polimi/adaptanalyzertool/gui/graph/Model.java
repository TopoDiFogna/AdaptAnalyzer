package it.polimi.adaptanalyzertool.gui.graph;


import it.polimi.adaptanalyzertool.gui.graph.cells.CircleCell;
import it.polimi.adaptanalyzertool.gui.graph.cells.RectangleCell;
import it.polimi.adaptanalyzertool.gui.graph.cells.TriangleCell;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class contains the model of the graph storing all cells and edges.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Model {

    private Map<String, Cell> cells;
    private List<Edge> edges;

    Model() {
        cells = new HashMap<>();
        edges = new ArrayList<>();
    }

    /**
     * <p>Adds a cell to the graph.</p>
     *
     * @param id        a unique id for the cell.
     * @param type      the type of cell.
     * @param fillColor the color of the cell.
     *
     * @see CellType CellType for the supported types
     */
    public void addCell(String id, CellType type, Color fillColor) {
        switch (type) {
            case RECTANGLE:
                RectangleCell rectangleCell = new RectangleCell(id, fillColor, Color.BLACK);
                addCell(rectangleCell);
                break;
            case CIRCLE:
                CircleCell circleCell = new CircleCell(id, fillColor, Color.BLACK);
                addCell(circleCell);
                break;
            case TRIANGLE:
                TriangleCell triangleCell = new TriangleCell(id, fillColor, Color.BLACK);
                addCell(triangleCell);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    private void addCell(Cell cell) {
        cells.put(cell.getId(), cell);
    }

    /**
     * <p>Gets all the cells that are in the graph.</p>
     *
     * @return a map containing alla the cells in the graph.
     */
    public Map<String, Cell> getCells() {
        return cells;
    }

    /**
     * <p>Adds an edge to the graph.</p>
     *
     * @param sourceId from which cell the edge should start.
     * @param targetId at which cell the edge should go.
     */
    public void addEdge(String sourceId, String targetId) {
        Cell source = cells.get(sourceId);
        Cell target = cells.get(targetId);

        Edge edge = new Edge(source, target);
        edges.add(edge);
    }

    /**
     * <p>Adds an arrow to the graph.</p>
     *
     * @param sourceId from which cell the arrow should start.
     * @param targetId at which cell the arrow should go.
     */
    public void addArrow(String sourceId, String targetId) {
        Cell source = cells.get(sourceId);
        Cell target = cells.get(targetId);

        Edge arrow = new Arrow(source, target);
        edges.add(arrow);
    }

    /**
     * <p>Returns a list of edges and arrows contained in the graph.</p>
     *
     * @return the list of edges and arrows in the graph.
     */
    public List<Edge> getEdges() {
        return edges;
    }
}
