package it.polimi.adaptanalyzertool.model;

/**
 * @author Paolo Paterna
 * @version 0.1
 */
public class Message {

    private String startingComponentName;
    private String endingComponentName;
    private boolean isReturning;

    public Message(String startingComponent, String endingComponent, boolean isReturning) {
        this.startingComponentName = startingComponent;
        this.endingComponentName = endingComponent;
        this.isReturning = isReturning;
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
