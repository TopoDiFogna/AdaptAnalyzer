package it.polimi.adaptanalyzertool.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    public Set<Path> getPaths() {
        return new HashSet<>(pathHashMap.values());
    }

    public Path getSinglePath(String name) {
        return pathHashMap.get(name);
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
