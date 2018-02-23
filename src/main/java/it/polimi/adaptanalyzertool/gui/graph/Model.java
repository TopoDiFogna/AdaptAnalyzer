package it.polimi.adaptanalyzertool.gui.graph;


import it.polimi.adaptanalyzertool.gui.graph.cells.CircleCell;
import it.polimi.adaptanalyzertool.gui.graph.cells.RectangleCell;
import it.polimi.adaptanalyzertool.gui.graph.cells.TriangleCell;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    private Map<String, Cell> cells;
    private List<Edge> edges;

    Model() {
        cells = new HashMap<>();
        edges = new ArrayList<>();
    }

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

    public Map<String, Cell> getCells() {
        return cells;
    }

    public void addEdge(String sourceId, String targetId) {
        Cell source = cells.get(sourceId);
        Cell target = cells.get(targetId);

        Edge edge = new Edge(source, target);
        edges.add(edge);
    }

    public void addArrow(String sourceId, String targetId) {
        Cell source = cells.get(sourceId);
        Cell target = cells.get(targetId);

        Edge arrow = new Arrow(source, target);
        edges.add(arrow);
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
