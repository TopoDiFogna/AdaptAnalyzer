package it.polimi.adaptanalyzertool.gui.graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {

    private String cellId;

    private List<Cell> children = new ArrayList<>();
    private List<Cell> parents = new ArrayList<>();

    private Node view;

    public Cell(String cellId) {
        this.cellId = cellId;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public Node getView() {
        return this.view;
    }

    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);
    }

    public String getCellId() {
        return cellId;
    }

    protected boolean isColorDark(Color fillColor) {
        double darkness = 1 - (0.299 * fillColor.getRed() * 255 + 0.587 * fillColor.getGreen() * 255 + 0.114 * fillColor.getBlue() * 255) / 255;
        return !(darkness < 0.5);
    }
}