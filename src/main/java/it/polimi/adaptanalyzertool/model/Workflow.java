package it.polimi.adaptanalyzertool.model;

import java.util.HashMap;

public class Workflow {

    private String name;
    private HashMap<String, Path> pathHashMap;

    public Workflow(String name) {
        this.name = name;
        pathHashMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Path> getPathHashMap() {
        return pathHashMap;
    }

    public void addPath(Path path) {
        pathHashMap.put(path.getName(), path);
    }

    public void removePath(Path path) {
        pathHashMap.remove(path.getName(), path);
    }

    public void removeAllPaths() {
        pathHashMap.clear();
    }
}
