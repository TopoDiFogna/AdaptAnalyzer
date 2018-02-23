package it.polimi.adaptanalyzertool.gui.graph;

import it.polimi.adaptanalyzertool.gui.graph.cells.CircleCell;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.Line;

class Arrow extends Edge {

    Arrow(Cell source, Cell target) {
        super(source, target);

        if (target instanceof CircleCell) {

            Line head1 = new Line();
            Line head2 = new Line();

            DoubleBinding x1 = cosTarget.multiply(0.5).add(sinTarget.multiply(Math.sqrt(3) / 2)).multiply(0.8);
            DoubleBinding y1 = sinTarget.multiply(0.5).add(cosTarget.multiply(-Math.sqrt(3) / 2)).multiply(0.8);

            DoubleBinding x2 = cosTarget.multiply(0.5).add(sinTarget.multiply(-Math.sqrt(3) / 2)).multiply(0.8);
            DoubleBinding y2 = sinTarget.multiply(0.5).add(cosTarget.multiply(Math.sqrt(3) / 2)).multiply(0.8);

            head1.startXProperty().bind(target.layoutXProperty().add(targetCircleX));
            head1.startYProperty().bind(target.layoutYProperty().add(targetCircleY));
            head1.endXProperty().bind(target.layoutXProperty().add(targetCircleX).add(x1));
            head1.endYProperty().bind(target.layoutYProperty().add(targetCircleY).add(y1));

            head2.startXProperty().bind(target.layoutXProperty().add(targetCircleX));
            head2.startYProperty().bind(target.layoutYProperty().add(targetCircleY));
            head2.endXProperty().bind(target.layoutXProperty().add(targetCircleX).add(x2));
            head2.endYProperty().bind(target.layoutYProperty().add(targetCircleY).add(y2));

            getChildren().addAll(head1, head2);
        } else {
            throw new UnsupportedOperationException("No arrow Specified for Cell that are not circles");
        }
    }
}
