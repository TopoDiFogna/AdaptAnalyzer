package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.graph.CellType;
import it.polimi.adaptanalyzertool.gui.graph.Graph;
import it.polimi.adaptanalyzertool.gui.graph.Layout;
import it.polimi.adaptanalyzertool.gui.graph.Model;
import it.polimi.adaptanalyzertool.gui.graph.layouts.RandomLayout;
import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;
import javafx.scene.layout.BorderPane;

public class GraphController {

    private Architecture architecture;
    private BorderPane root;
    private Graph graph;

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void setUp() {
        graph = new Graph();
        root.setCenter(graph.getScrollPane());

        Model model = graph.getModel();
        graph.beginUpdate();

        for (Component component: architecture.getComponents().values()) {

            model.addCell(component.getName(), CellType.RECTANGLE, component.getColor());

        }
        for (Component component: architecture.getComponents().values()) {//TODO remove spaghetti code
            for(RequiredService requiredService: component.getRequiredServices().values()){
                for(Component component1: architecture.getComponents().values()){
                    for (ProvidedService providedService: component1.getProvidedServices().values()){
                        if (requiredService.getName().equals(providedService.getName())){
                            model.addEdge(component.getName(), component1.getName());
                        }
                    }
                }
            }
        }

        graph.endUpdate();
        Layout layout = new RandomLayout(graph);
        layout.execute();
    }
}
