package it.polimi.adaptanalyzertool.model;

/**
 * <p>
 * This class represents a message in the sequence diagram.
 * </p>
 * <p>
 * It can be a forward message or a return message, this property can be setted with the appropriate method.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Message {

    private String startingGroupName;
    private String endingGroupName;
    private boolean isReturning;

    /**
     * <p>
     * Creates a message object with a starting component (or group) and an ending component (or group).
     * </p>
     *
     * @param startingComponent from where this message should start.
     * @param endingComponent   where this mesage should end.
     * @param isReturning       if this is a returning message.
     */
    public Message(String startingComponent, String endingComponent, boolean isReturning) {
        this.startingGroupName = startingComponent;
        this.endingGroupName = endingComponent;
        this.isReturning = isReturning;
    }

    /**
     * @return the name of the component (or group) from where this message starts.
     */
    public String getStartingGroupName() {

        return startingGroupName;
    }

    /**
     * @return the name of the component (or group) where this message ends.
     */
    public String getEndingGroupName() {
        return endingGroupName;
    }

    /**
     * @return if this message is a return message.
     */
    public boolean isReturning() {
        return isReturning;
    }
}
