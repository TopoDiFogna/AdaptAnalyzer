package it.polimi.adaptanalyzertool.gui.graph;

import javafx.scene.shape.Line;

class Arrow extends Edge { //TODO finish arrow displaying

    Arrow(Cell source, Cell target) {
        super(source, target);

        Line head1 = new Line();
        Line head2 = new Line();

        double arrowStartX = source.getBoundsInParent().getWidth() / 2.0;
        double arrowStartY = source.getBoundsInParent().getHeight() / 2.0;
        double arrowEndX = source.getBoundsInParent().getWidth() / 2.0;
        double arrowEndY = source.getBoundsInParent().getHeight() / 2.0;

        head1.startXProperty().bind(target.layoutXProperty().add(arrowStartX));
        head1.startYProperty().bind(target.layoutYProperty().add(arrowStartY));
        head2.startXProperty().bind(target.layoutXProperty().add(arrowEndX));
        head2.startYProperty().bind(target.layoutYProperty().add(arrowEndY));

        //Arrow head
        double angle = Math.atan2((arrowEndY - arrowStartY), (arrowEndX - arrowStartX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 50 + arrowEndX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 50 + arrowEndY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 50 + arrowEndX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 50 + arrowEndY;

        head1.endXProperty().bind(target.layoutXProperty().add(x1));
        head1.endYProperty().bind(target.layoutYProperty().add(y1));
        head2.endXProperty().bind(target.layoutXProperty().add(x2));
        head2.endYProperty().bind(target.layoutYProperty().add(y2));

        getChildren().add(head1);
        getChildren().add(head2);

    }
}
