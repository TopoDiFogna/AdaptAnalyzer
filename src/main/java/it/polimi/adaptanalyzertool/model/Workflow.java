package it.polimi.adaptanalyzertool.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This class represent a sequence diagram.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Workflow {

    private String name;
    private HashMap<String, Path> pathHashMap;

    /**
     * <p>
     * Creates a new workflow with a given name.
     * </p>
     *
     * @param name the name of the workflow.
     */
    public Workflow(String name) {
        this.name = name;
        pathHashMap = new HashMap<>();
    }

    /**
     * @return the name of the workflow.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the set of the paths associated with the workflow, {@code null} if empty.
     */
    public Set<Path> getPaths() {
        return new HashSet<>(pathHashMap.values());
    }

    /**
     * @param name the name of the wanted path.
     * @return the path object asociated woth the given name, {@code null} if not found.
     */
    public Path getSinglePath(String name) {
        return pathHashMap.get(name);
    }

    /**
     * <p>
     * Adds a path to the workflow.
     * </p>
     *
     * @param path the path to be added.
     */
    public void addPath(Path path) {
        pathHashMap.put(path.getName(), path);
    }

    /**
     * <p>
     * Removes a path from the workflow.
     * </p>
     *
     * @param path the path to be removed from the workflow.
     */
    public void removePath(Path path) {
        pathHashMap.remove(path.getName(), path);
    }

    /**
     * Removes all the paths associated with the workflow.
     */
    public void removeAllPaths() {
        pathHashMap.clear();
    }
}
