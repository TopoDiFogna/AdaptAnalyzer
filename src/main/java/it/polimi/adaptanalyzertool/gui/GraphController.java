package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.gui.error.ErrorWindow;
import it.polimi.adaptanalyzertool.gui.graph.CellType;
import it.polimi.adaptanalyzertool.gui.graph.Graph;
import it.polimi.adaptanalyzertool.gui.graph.Layout;
import it.polimi.adaptanalyzertool.gui.graph.Model;
import it.polimi.adaptanalyzertool.gui.graph.layouts.RandomLayout;
import it.polimi.adaptanalyzertool.model.Architecture;
import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.RequiredService;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GraphController {

    private Architecture architecture;
    private BorderPane root;
    private ErrorWindow errorWindow = new ErrorWindow();

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

    @FXML
    private void closeGraphWindow() {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void exportGraphImage() {
        WritableImage image = root.getCenter().snapshot(new SnapshotParameters(), null);
        FileChooser fc = new FileChooser();
        fc.setTitle("Export Architecture");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Png file", "*.png"));
        File file = fc.showSaveDialog(root.getScene().getWindow());
        if (file != null) {
            saveImage(image, file);
        }
    }

    private void saveImage(Image image, File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            errorWindow.showErrorMessage("Error Saving the file.", root.getScene().getWindow());
        }
    }
}
