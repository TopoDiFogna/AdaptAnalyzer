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
 * The model contains all the graph's elements.
 * <p>
 * All the created Cells and Edges are stored in this model to be used later.
 * </p>
 * <p>
 * In particular this class contains all the methods to add and remove Cells and Edges; it also contains an
 * invisible Cell that is the parent of all Cells without parents.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Model {

    /**
     * The invisible node where all cells spawn at.
     */
    private Cell graphParent;

    private List<Cell> allCells;
    private List<Cell> addedCells;
    private List<Cell> removedCells;

    private List<Edge> allEdges;
    private List<Edge> addedEdges;
    private List<Edge> removedEdges;

    /**
     * Map containing the reference of every cell with its id
     */
    private Map<String, Cell> cellMap; // <id,cell>

    public Model() {

        graphParent = new Cell("_ROOT_");

        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();

        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();

        cellMap = new HashMap<>();
    }

    /**
     * Clears the list containing the added elements.
     */
    public void clearAddedLists() {
        addedCells.clear();
        addedEdges.clear();
    }

    /**
     * Gets all the cells added to the current model.
     *
     * @return the list of cells added to the model.
     */
    public List<Cell> getAddedCells() {
        return addedCells;
    }

    public List<Cell> getRemovedCells() {
        return removedCells;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Edge> getRemovedEdges() {
        return removedEdges;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }

    public void addCell(String id, CellType type, Color fillColor, Color strokeColor) {

        switch (type) {

            case RECTANGLE:
                RectangleCell rectangleCell = new RectangleCell(id, fillColor, strokeColor);
                addCell(rectangleCell);
                break;

            case TRIANGLE:
                TriangleCell triangleCell = new TriangleCell(id, fillColor, strokeColor);
                addCell(triangleCell);
                break;

            case CIRCLE:
                CircleCell circleCell = new CircleCell(id, fillColor, strokeColor);
                addCell(circleCell);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    public void addCell(String id, CellType type, Color fillColor){
        addCell(id, type,fillColor, Color.BLACK);
    }

    private void addCell(Cell cell) {

        addedCells.add(cell);

        cellMap.put(cell.getCellId(), cell);

    }

    public void addEdge(String sourceId, String targetId) {

        Cell sourceCell = cellMap.get(sourceId);
        Cell targetCell = cellMap.get(targetId);

        Edge edge = new Edge(sourceCell, targetCell);

        addedEdges.add(edge);

    }

    /**
     * Attach all cells which don't have a parent to graphParent.
     *
     * @param cellList
     */
    public void attachOrphansToGraphParent(List<Cell> cellList) {

        for (Cell cell : cellList) {
            if (cell.getCellParents().size() == 0) {
                graphParent.addCellChild(cell);
            }
        }

    }

    /**
     * Remove the graphParent reference if it is set.
     *
     * @param cellList
     */
    public void disconnectFromGraphParent(List<Cell> cellList) {

        for (Cell cell : cellList) {
            graphParent.removeCellChild(cell);
        }
    }

    public void merge() {

        // cells
        allCells.addAll(addedCells);
        allCells.removeAll(removedCells);

        addedCells.clear();
        removedCells.clear();

        // edges
        allEdges.addAll(addedEdges);
        allEdges.removeAll(removedEdges);

        addedEdges.clear();
        removedEdges.clear();

    }
}
