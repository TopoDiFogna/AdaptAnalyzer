package it.polimi.adaptanalyzertool.model;

/**
 * @author Paolo Paterna
 * @version 0.1
 */
public class Message {

    private String startingGroupName;
    private String endingGroupName;
    private boolean isReturning;

    public Message(String startingComponent, String endingComponent, boolean isReturning) {
        this.startingGroupName = startingComponent;
        this.endingGroupName = endingComponent;
        this.isReturning = isReturning;
    }

    public String getStartingGroupName() {

        return startingGroupName;
    }

    public String getEndingGroupName() {
        return endingGroupName;
    }

    public boolean isReturning() {
        return isReturning;
    }
}
