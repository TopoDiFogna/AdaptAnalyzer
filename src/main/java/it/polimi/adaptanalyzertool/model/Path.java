package it.polimi.adaptanalyzertool.model;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * This class represents an alt or opt block within a sequence diagram.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Path {

    private String name;
    private double executionProbability;
    private LinkedList<Message> messagesList;

    /**
     * <p>
     * Creates a path object with a name and a probability to be executed.
     * </p>
     *
     * @param name                 the name of the path.
     * @param executionProbability the probability ranging from 0 to 1.
     */
    public Path(String name, double executionProbability) {
        this.name = name;
        this.executionProbability = executionProbability;
        messagesList = new LinkedList<>();
    }

    /**
     * <p>
     * Creates a path object with a name and a probability to be executed. A starting message can also be specified.
     * </p>
     *
     * @param name                 the name of the path.
     * @param executionProbability the probability ranging from 0 to 1.
     * @param startingMessage      the first message of this path.
     */
    public Path(String name, double executionProbability, Message startingMessage) {
        this.name = name;
        this.executionProbability = executionProbability;
        messagesList = new LinkedList<>();
        messagesList.addFirst(startingMessage);
    }

    /**
     * @return the name of the path.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the probability that this path is executed.
     */
    public double getExecutionProbability() {
        return executionProbability;
    }

    /**
     * @return the ordered list associated with this path.
     */
    public LinkedList<Message> getMessagesList() {
        return messagesList;
    }

    /**
     * <p>
     * Adds a message to this path, but does not checks if it is consisten with the others.
     * </p>
     *
     * @param message the message that has to be added.
     */
    public void addMessage(Message message) {
        messagesList.add(message);
    }

    /**
     * @return the first message of this path.
     */
    public Message getFirstMessage() {
        Message firstMessage = null;
        try {
            firstMessage = messagesList.getFirst();
        } catch (NoSuchElementException e) {
            /* do nothing */
        }
        return firstMessage;
    }

    /**
     * @return the last message of this path.
     */
    public Message getLastMessage() {
        Message lastMessage = null;
        try {
            lastMessage = messagesList.getLast();
        } catch (NoSuchElementException e) {
            /* do nothing */
        }

        return lastMessage;
    }

    /**
     * <p>
     * Removes a message and all his followers since following message don't make sense without the deleted
     * message.
     * </p>
     *
     * @param index the index in the list of the message that has to be removed.
     */
    public void removeMessageAndFollowers(int index) {
        ListIterator<Message> listIterator = messagesList.listIterator(index);
        while (listIterator.hasNext()) {
            listIterator.next();
            listIterator.remove();
        }
    }

    /**
     * <p>
     * Removes a message and all his followers since following message don't make sense without the deleted
     * message.
     * </p>
     *
     * @param message the message that has to be removed.
     */
    public void removeMessageAndFollowers(Message message) {
        int index = messagesList.indexOf(message);
        removeMessageAndFollowers(index);
    }
}
