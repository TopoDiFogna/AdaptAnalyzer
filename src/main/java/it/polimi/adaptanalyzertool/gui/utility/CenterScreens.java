package it.polimi.adaptanalyzertool.gui.utility;

/**
 * <p>This enum must contain all the screens that can be placed in the main window.</p>
 * <p>It is created like this in order to improve performance and load times.</p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public enum CenterScreens {
    /**
     * First Screen shown to the user.
     */
    WELCOME("welcome", "/it/polimi/adaptanalyzertool/gui/welcomescreen/welcomeScreen.fxml", null),
    /**
     * The screen containing all the architecture details.
     */
    ARCHITECTURE("architecture", "/it/polimi/adaptanalyzertool/gui/architecturescreen/architectureScreenBeta.fxml", null);

    private String name;
    private String layout;
    private ChildScreenController controller;

    CenterScreens(String name, String layout, ChildScreenController controller) {
        this.name = name;
        this.layout = layout;
        this.controller = controller;
    }

    /**
     * <p>Use this to retrieve the name of the loaded screen.</p>
     *
     * @return the name of the screen.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Use this to retrieve the fxml file that compose the screen.</p>
     *
     * @return the layout associated to the screen.
     */
    public String getLayout() {
        return layout;
    }

    /**
     * <p>Use this to retrieve the controller associated to the screen.</p>
     * <p>Note that by default the controller is {@code null} unless set with
     * {@link #setController(ChildScreenController)}.</p>
     * <p>Note that the controller associated is not for sure the one that is controlling the screen.</p>
     *
     * @return the controller associated with the screen.
     */
    public ChildScreenController getController() {
        return controller;
    }

    /**
     * <p>Associate the screen with a controller.</p>
     *
     * @param controller the controller associated with the screen.
     */
    public void setController(ChildScreenController controller) {
        this.controller = controller;
    }
}
