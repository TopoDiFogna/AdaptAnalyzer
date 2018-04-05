package it.polimi.adaptanalyzertool.gui.graph.cells;

import it.polimi.adaptanalyzertool.gui.graph.Cell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * <p>Creates a rectangle cell with a fill color and a border color.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class RectangleCell extends Cell {

    /**
     * <p>Creates a rectangle cell with a fill color and a border color.</p>
     *
     * @param id          unique id of the cell.
     * @param fillColor   the inner color of the cell.
     * @param strokeColor the border color of the cell.
     */
    public RectangleCell(String id, Color fillColor, Color strokeColor) {
        super(id);

        StackPane root = new StackPane();

        Text name = new Text(id);
        Rectangle rectangle = new Rectangle(50, 50);

        rectangle.setStroke(strokeColor);
        rectangle.setFill(fillColor);

        if (isColorDark(fillColor)) {
            name.setFill(Color.WHITE); //set to white the text if the cell is dark
        }

        root.getChildren().addAll(rectangle, name);

        getChildren().add(root);
    }
}
