package it.polimi.adaptanalyzertool.gui.graph;

import eu.lestard.advanced_bindings.api.MathBindings;
import it.polimi.adaptanalyzertool.gui.graph.cells.CircleCell;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * <p>This class defines an edge that goes from cell to cell.</p>
 * <p>An edge is a line without head nor tail.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
class Edge extends Group {

    DoubleBinding cosTarget;
    DoubleBinding sinTarget;
    DoubleBinding targetCircleX;
    DoubleBinding targetCircleY;

    Edge(Cell source, Cell target) {

        source.addChildCell(target);
        target.addParentCell(source);

        Line line = new Line();

        if (target instanceof CircleCell) {
            double sourceXSize = source.getBoundsInParent().getWidth() / 2.0;
            double sourceYSize = source.getBoundsInParent().getHeight() / 2.0;
            double targetXSize = target.getBoundsInParent().getWidth() / 2.0;
            double targetYSize = target.getBoundsInParent().getHeight() / 2.0;

            ObservableDoubleValue xDistance = (ObservableDoubleValue) Bindings.add(source.layoutXProperty(), target.layoutXProperty().multiply(-1));
            ObservableDoubleValue yDistance = (ObservableDoubleValue) Bindings.add(source.layoutYProperty(), target.layoutYProperty().multiply(-1));
            DoubleBinding angle = MathBindings.atan2(yDistance, xDistance);
            DoubleBinding cosSource = Bindings.multiply(targetXSize, MathBindings.cos(angle.add(Math.PI)));
            DoubleBinding sinSource = Bindings.multiply(targetYSize, MathBindings.sin(angle.add(Math.PI)));
            cosTarget = Bindings.multiply(targetXSize, MathBindings.cos(angle));
            sinTarget = Bindings.multiply(targetYSize, MathBindings.sin(angle));
            targetCircleX = Bindings.add(cosTarget, targetXSize);
            targetCircleY = Bindings.add(sinTarget, targetYSize);
            DoubleBinding sourceCircleX = Bindings.add(cosSource, sourceXSize);
            DoubleBinding sourceCircleY = Bindings.add(sinSource, sourceYSize);

            line.startXProperty().bind(source.layoutXProperty().add(sourceCircleX));
            line.startYProperty().bind(source.layoutYProperty().add(sourceCircleY));

            line.endXProperty().bind(target.layoutXProperty().add(targetCircleX));
            line.endYProperty().bind(target.layoutYProperty().add(targetCircleY));
        } else {
            line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
            line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

            line.endXProperty().bind(target.layoutXProperty().add(target.getBoundsInParent().getWidth() / 2.0));
            line.endYProperty().bind(target.layoutYProperty().add(target.getBoundsInParent().getHeight() / 2.0));
        }

        getChildren().add(line);
    }
}
