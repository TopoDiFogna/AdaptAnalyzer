package it.polimi.adaptanalyzertool.gui.graph.cells;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleCell extends Cell {

    public RectangleCell(String id, Color fillColor, Color strokeColor) {
        super(id);

        Rectangle view = new Rectangle(50, 50);

        view.setStroke(strokeColor);
        view.setFill(fillColor);

        setView(view);

    }

}