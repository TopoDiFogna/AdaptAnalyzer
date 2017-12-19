package it.polimi.adaptanalyzertool.gui.utility;

public enum CenterScreens {
    WELCOME("welcome", "/it/polimi/adaptanalyzertool/gui/welcomescreen/welcomeScreen.fxml", null),
    ARCHITECTURE("architecture", "/it/polimi/adaptanalyzertool/gui/architecturescreen/architectureScreenBeta.fxml", null);

    private String name;
    private String layout;
    private ChildScreenController controller;

    CenterScreens(String name, String layout, ChildScreenController controller) {
        this.name = name;
        this.layout = layout;
        this.controller = controller;
    }

    public String getName() {
        return name;
    }

    public String getLayout() {
        return layout;
    }

    public ChildScreenController getController() {
        return controller;
    }

    public void setController(ChildScreenController controller) {
        this.controller = controller;
    }
}
