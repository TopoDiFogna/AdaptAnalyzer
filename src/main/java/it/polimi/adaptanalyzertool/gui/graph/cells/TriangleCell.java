package it.polimi.adaptanalyzertool.gui.graph.cells;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleCell extends Cell {

    public TriangleCell(String id, Color fillColor, Color strokeColor) {
        super(id);

        double width = 50;
        double height = 50;

        Polygon view = new Polygon(width / 2, 0, width, height, 0, height);

        view.setStroke(strokeColor);
        view.setFill(fillColor);

        setView(view);

    }

}
