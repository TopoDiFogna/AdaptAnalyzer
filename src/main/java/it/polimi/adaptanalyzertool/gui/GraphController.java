package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.graph.*;
import it.polimi.adaptanalyzertool.gui.graph.layouts.RandomLayout;
import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;

import it.polimi.adaptanalyzertool.model.RequiredService;
import javafx.scene.layout.BorderPane;

public class GraphController {

    private Architecture architecture;
    private BorderPane root;

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void setUp() {
        Graph graph = new Graph();
        root.setCenter(graph.getScrollPane());

        Model model = graph.getModel();
        Layout layout = new RandomLayout(graph);

        for (Component component : architecture.getComponents().values()) {
            model.addCell(component.getName(), CellType.CIRCLE, component.getColor());
        }

        layout.addCells();

        for (Component sourceComponent : architecture.getComponents().values()) {
            for (RequiredService requiredService : sourceComponent.getRequiredServices().values()) {
                for (Component targetComponent : architecture.getComponents().values()) {
                    if (targetComponent.getProvidedServices().containsKey(requiredService.getName())) {
                        model.addArrow(sourceComponent.getName(), targetComponent.getName());
                    }
                }
            }
        }

        layout.addEdges();

        graph.endUpdate();
    }
}
