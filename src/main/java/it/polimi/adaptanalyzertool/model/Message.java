package it.polimi.adaptanalyzertool.model;

/**
 * @author Paolo Paterna
 * @version 0.1
 */
public class Message {

    private Component startingComponent;
    private Component endingComponent;

    private String name;

    public Message(String name) {
        this(name, null, null);
    }

    public Message(String name, Component startingComponent, Component endingComponent) {
        this.name = name;
        this.startingComponent = startingComponent;
        this.endingComponent = endingComponent;
    }

    public String getName() {
        return name;
    }

    public Component getStartingComponent() {

        return startingComponent;
    }

    public void setStartingComponent(Component startingComponent) {
        this.startingComponent = startingComponent;
    }

    public void setEndingComponent(Component endingComponent) {
        this.endingComponent = endingComponent;
    }

    public Component getEndingComponent() {
        return endingComponent;
    }
}
