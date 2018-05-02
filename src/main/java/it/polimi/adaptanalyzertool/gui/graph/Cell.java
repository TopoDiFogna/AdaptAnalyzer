package it.polimi.adaptanalyzertool.gui.graph;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class defines a cell and contains a reference its parents and children.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 * @see CellType
 */
public class Cell extends Pane {

    private List<Cell> childrenList;
    private List<Cell> parentList;

    /**
     * <p>Creates a cell with no parents nor children.</p>
     *
     * @param cellId the unique id of the cell.
     */
    public Cell(String cellId) {
        this.idProperty().setValue(cellId);
        this.childrenList = new ArrayList<>();
        this.parentList = new ArrayList<>();
    }

    void addParentCell(Cell parent) {
        parentList.add(parent);
    }

    void removeParentCell(Cell parent) {
        parentList.remove(parent);
    }

    List<Cell> getParentList() {
        return parentList;
    }

    void addChildCell(Cell child) {
        childrenList.add(child);
    }

    void removeChildCell(Cell child) {
        childrenList.remove(child);
    }

    List<Cell> getChildrenList() {
        return childrenList;
    }

    /**
     * <p>Using the luminescence formula, determines if a cell is dark or bright.</p>
     *
     * @param fillColor the color of the cell.
     *
     * @return <code>true</code> if the cell is fille dwitha dark color, <code>false</code> otherwise.
     */
    protected boolean isColorDark(Color fillColor) {
        double darkness = 1 - (0.299 * fillColor.getRed() * 255 + 0.587 * fillColor.getGreen() * 255 + 0.114 * fillColor.getBlue() * 255) / 255;
        return !(darkness < 0.5);
    }
}
