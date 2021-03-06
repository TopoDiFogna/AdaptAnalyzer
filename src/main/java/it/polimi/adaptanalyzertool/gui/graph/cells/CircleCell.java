package it.polimi.adaptanalyzertool.gui.graph.cells;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * <p>Creates a circle cell with a fill color and a border color.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class CircleCell extends Cell {

    /**
     * <p>Creates a circle cell with a fill color and a border color.</p>
     *
     * @param id          unique id of the cell.
     * @param fillColor   the inner color of the cell.
     * @param strokeColor the border color of the cell.
     */
    public CircleCell(String id, Color fillColor, Color strokeColor) {
        super(id);

        StackPane root = new StackPane();

        Text name = new Text(id);
        Circle circle = new Circle(25);

        circle.setStroke(strokeColor);
        circle.setFill(fillColor);

        if (isColorDark(fillColor)) {
            name.setFill(Color.WHITE); //set to white the text if the cell is dark
        }

        root.getChildren().addAll(circle, name);

        getChildren().add(root);
    }
}
