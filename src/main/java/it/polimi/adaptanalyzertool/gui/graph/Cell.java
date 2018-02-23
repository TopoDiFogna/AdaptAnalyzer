package it.polimi.adaptanalyzertool.gui.graph;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {

    private List<Cell> childrenList;
    private List<Cell> parentList;

    public Cell(String cellId) {
        this.idProperty().setValue(cellId);
        this.childrenList = new ArrayList<>();
        this.parentList = new ArrayList<>();
    }

    public void addParentCell(Cell parent) {
        parentList.add(parent);
    }

    public void removeParentCell(Cell parent) {
        parentList.remove(parent);
    }

    public List<Cell> getParentList() {
        return parentList;
    }

    public void addChildCell(Cell child) {
        childrenList.add(child);
    }

    public void removeChildCell(Cell child) {
        childrenList.remove(child);
    }

    public List<Cell> getChildrenList() {
        return childrenList;
    }

    protected boolean isColorDark(Color fillColor) {
        double darkness = 1 - (0.299 * fillColor.getRed() * 255 + 0.587 * fillColor.getGreen() * 255 + 0.114 * fillColor.getBlue() * 255) / 255;
        return !(darkness < 0.5);
    }
}
