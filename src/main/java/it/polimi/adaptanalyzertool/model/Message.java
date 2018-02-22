package it.polimi.adaptanalyzertool.model;

/**
 * @author Paolo Paterna
 * @version 0.1
 */
public class Message {

    private String startingComponentName;
    private String endingComponentName;

    private String name;

    public Message(String name) {
        this(name, null, null);
    }

    public Message(String name, String startingComponent, String endingComponent) {
        this.name = name;
        this.startingComponentName = startingComponent;
        this.endingComponentName = endingComponent;
    }

    public String getName() {
        return name;
    }

    public String getStartingComponentName() {

        return startingComponentName;
    }

    public void setStartingComponentName(String startingComponentName) {
        this.startingComponentName = startingComponentName;
    }

    public String getEndingComponentName() {
        return endingComponentName;
    }

    public void setEndingComponentName(String endingComponentName) {
        this.endingComponentName = endingComponentName;
    }
}
