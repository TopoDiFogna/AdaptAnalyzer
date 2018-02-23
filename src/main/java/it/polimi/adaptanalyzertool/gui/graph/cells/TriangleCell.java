package it.polimi.adaptanalyzertool.gui.graph.cells;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class TriangleCell extends Cell {

    public TriangleCell(String id, Color fillColor, Color strokeColor) {
        super(id);

        StackPane root = new StackPane();

        Text name = new Text(id);

        double width = 50;
        double height = 50;

        Polygon triangle = new Polygon(width / 2, 0, width, height, 0, height);

        triangle.setStroke(strokeColor);
        triangle.setFill(fillColor);

        if (isColorDark(fillColor)) {
            name.setFill(Color.WHITE); //set to white the text if the cell is dark
        }

        root.getChildren().addAll(triangle, name);

        getChildren().add(root);
    }
}
