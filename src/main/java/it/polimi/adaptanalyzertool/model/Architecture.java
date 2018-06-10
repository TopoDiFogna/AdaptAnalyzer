package it.polimi.adaptanalyzertool.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class for the architecture object which defines the components used by the architecture.
 * <p>
 * On this object are calculated all the architectures metrics found in
 * {@link it.polimi.adaptanalyzertool.metrics.ArchitectureMetrics ArchitectureMetrics}.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class Architecture {

    private String name;
    private HashMap<String, Component> components;
    private HashMap<String, Workflow> workflows;

    /**
     * Creates a new architecture with no components.
     * <p>
     * Components can be added later with {@link Architecture#addComponent addComponent}.
     * </p>
     *
     * @param name The name for this architecture.
     */
    public Architecture(String name) {
        this.name = name;
        this.components = new HashMap<>();
        this.workflows = new HashMap<>();
    }

    /**
     * Creates a new architecture with the specified set of components.
     * <p>
     * More components can be added later by using {@link Architecture#addComponent addComponent}.
     * </p>
     *
     * @param name       The name for this architecture.
     * @param components The Set containing the components.
     */
    public Architecture(String name, Set<Component> components) {
        this(name);
        for (Component component : components) {
            this.components.put(component.getName(), component);
        }
    }

    /**
     * Creates a new architecture with the specified set of components.
     * <p>
     * More components can be added later by using {@link Architecture#addComponent addComponent}.
     * </p>
     *
     * @param name       The name for this architecture.
     * @param components The Set containing the components.
     * @param workflows  The Set containing the workflows associated with this architecture.
     */
    public Architecture(String name, Set<Component> components, Set<Workflow> workflows) {
        this.name = name;
        for (Component component : components) {
            this.components.put(component.getName(), component);
        }
        for (Workflow workflow : workflows) {
            this.workflows.put(workflow.getName(), workflow);
        }
    }

    /**
     * Returns the name of this architecture.
     *
     * @return The {@code String} containing the name of the architecture.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets all the components associated with the current architecture.
     *
     * @return an {@code HashMap} containing all the components related to this architecture.
     * @see Component
     */
    public Set<Component> getComponents() {
        return new HashSet<>(components.values());
    }

    /**
     * Searches for a component in this architecture. It returns null if the component is not found.
     *
     * @param name the name of the component that has to be searched for.
     *
     * @return the component object if found, {@code null} otherwise.
     */
    public Component getSingleComponent(String name) {
        return components.get(name);
    }

    /**
     * Returns a set of names that corresponds to all the component names.
     *
     * @return a set of string that corresponds to all the component names.
     */
    public Set<String> getComponentsNames() {
        return components.keySet();
    }

    /**
     * Adds a component to the architecture.
     * <p>
     * No duplicates component can be added.
     * </p>
     *
     * @param component The {@code Component} to be added
     */
    public void addComponent(Component component) {
        components.put(component.getName(), component);
    }

    /**
     * <p>
     * Adds a set of components to this architecture.
     * </p>
     * <p>
     * It uses the {@link #addComponent(Component)} in a cycle to do so.
     * </p>
     *
     * @param components a set of components to be added to the architecture.
     */
    public void addComponents(Set<Component> components) {
        for (Component component : components) {
            this.components.put(component.getName(), component);
        }
    }

    /**
     * Removes a component from the current architecture.
     *
     * @param component The component to be removed.
     */
    public void removeComponent(Component component) {
        components.remove(component.getName(), component);
    }

    /**
     * Removes all the component from the current architecture leaving it empty.
     * <p>
     * The architecture is not destroyed by this operation.
     * </p>
     */
    public void removeAllComponents() {
        components.clear();
    }

    /**
     * Return the workflows associated with this architecture.
     *
     * @return the workflows associated with this architecture.
     * @see Workflow
     */
    public Set<Workflow> getWorkflows() {
        return new HashSet<>(workflows.values());
    }

    /**
     * Adds a workflows to the list of workflows associated with this architecture.
     *
     * @param workflow the workflows to be added.
     */
    public void addWorkflow(Workflow workflow) {
        this.workflows.put(workflow.getName(), workflow);
    }

    /**
     * Removes a workflows from the list of workflows associated with this architecture.
     *
     * @param workflow the workflows to remove from the list.
     */
    public void removeWorkflow(Workflow workflow) {
        this.workflows.remove(workflow.getName(), workflow);
    }

    /**
     * Removes all workflows from the current architecture.
     */
    public void removeAllWorkflow() {
        workflows.clear();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Architecture name= " + name + "\n\tComponents: ");
        for (Component c : components.values()) {
            if (c.isUsed()) {
                s.append(c.getName()).append(" ");
            }
        }
        return s.toString();
    }

    /**
     * <p>
     * This methods clones an architecture without carrying the workflows associated to it.
     * </p>
     *
     * @param name the name of the cloned component.
     *
     * @return a copy of this object without workflows.
     */
    public Architecture clone(String name) {
        Architecture architecture = new Architecture(name);
        for (Component c : this.getComponents()) {
            architecture.addComponent(c.clone());
        }
        return architecture;
    }
}
