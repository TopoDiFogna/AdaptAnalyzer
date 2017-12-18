package it.polimi.adaptanalyzertool.model;

import java.util.LinkedList;

public class Path {

    private String name;
    private double executionProbability;
    private LinkedList<Message> messagesList;

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

    public void removeLastMessage() {
        messagesList.removeLast();
    }

    public void removeMessage(Message message) {
        messagesList.remove(message);
    }

    public void removeMessage(int index) {
        messagesList.remove(index);
    }
}
