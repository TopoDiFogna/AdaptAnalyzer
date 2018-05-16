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
        this(name, new HashMap<>(), new HashMap<>());
    }

    /**
     * Creates a new architecture with the specified set of components.
     * <p>
     * More components can be added later by using {@link Architecture#addComponent addComponent}.
     * </p>
     *
     * @param name       The name for this architecture.
     * @param components The HashMap containing the components.
     */
    public Architecture(String name, HashMap<String, Component> components) {
        this(name, components, new HashMap<>());
    }

    /**
     * Creates a new architecture with the specified set of components.
     * <p>
     * More components can be added later by using {@link Architecture#addComponent addComponent}.
     * </p>
     *
     * @param name       The name for this architecture.
     * @param components The HashMap containing the components.
     * @param workflows  The HashMap containing the workflows associated with this architecture.
     */
    public Architecture(String name, HashMap<String, Component> components, HashMap<String, Workflow> workflows) {
        this.name = name;
        this.components = components;
        this.workflows = workflows;
    }

    /**
     * Returns the name of this architecture.
     *
     * @return The <code>String</code> containing the name of the architecture.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets all the components associated with the current architecture.
     *
     * @return an <code>HashMap</code> containing all the components related to this architecture.
     * @see Component
     */
    public Set<Component> getComponents() {
        return new HashSet<>(components.values());
    }

    public Component getSingleComponent(String name) {
        return components.get(name);
    }

    public Set<String> getComponentsNames() {
        return components.keySet();
    }

    /**
     * Adds a component to the architecture.
     * <p>
     * No duplicates component can be added.
     * </p>
     *
     * @param component The <code>Component</code> to be added
     */
    public void addComponent(Component component) {
        components.put(component.getName(), component);
    }

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
}
