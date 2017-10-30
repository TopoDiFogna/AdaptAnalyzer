package it.polimi.adaptanalyzertool.gui.utility;

public enum ScreenName {
    WELCOME("welcome", "/it/polimi/adaptanalyzertool/gui/welcomescreen/welcomeScreen.fxml"),
    ARCHITECTURESCREEN("architectureScreen", "/it/polimi/adaptanalyzertool/gui/architectureScreen/architectureScreen.fxml");


    String name;
    String file;

    ScreenName(String name, String file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return file;
    }
}
