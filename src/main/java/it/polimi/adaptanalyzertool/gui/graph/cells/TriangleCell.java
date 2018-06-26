package it.polimi.adaptanalyzertool.gui.graph.cells;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * <p>Creates a equilateral triangle cell with a fill color and a border color.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class TriangleCell extends Cell {

    /**
     * <p>Creates a equilateral triangle cell with a fill color and a border color.</p>
     *
     * @param id          unique id of the cell.
     * @param fillColor   the inner color of the cell.
     * @param strokeColor the border color of the cell.
     */
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
