package it.polimi.adaptanalyzertool.model;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Path {

    private String name;
    private double executionProbability;
    private LinkedList<Message> messagesList;

    public Path(String name, double executionProbability) {
        this.name = name;
        this.executionProbability = executionProbability;
        messagesList = new LinkedList<>();
    }

    public Path(String name, double executionProbability, Message startingMessage) {
        this.name = name;
        this.executionProbability = executionProbability;
        messagesList = new LinkedList<>();
        messagesList.addFirst(startingMessage);
    }

    public String getName() {
        return name;
    }

    public double getExecutionProbability() {
        return executionProbability;
    }

    public void setExecutionProbability(double executionProbability) {
        this.executionProbability = executionProbability;
    }

    public LinkedList<Message> getMessagesList() {
        return messagesList;
    }

    public void addMessage(Message message) {
        messagesList.add(message);
    }

    public void removeMessage(Message message) {
        messagesList.remove(message);
    }

    public void removeMessage(int index) {
        messagesList.remove(index);
    }

    public Message getFirstMessage() {
        Message firstMessage = null;
        try {
            firstMessage = messagesList.getFirst();
        } catch (NoSuchElementException e) {
            /* do nothing */
        }
        return firstMessage;
    }

    public Message getLastMessage() {
        Message lastMessage = null;
        try {
            lastMessage = messagesList.getLast();
        } catch (NoSuchElementException e) {
            /* do nothing */
        }

        return lastMessage;
    }
}
