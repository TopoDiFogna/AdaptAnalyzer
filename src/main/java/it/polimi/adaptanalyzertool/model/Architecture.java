package it.polimi.adaptanalyzertool.model;

import java.util.HashMap;

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
    private HashMap<String, Workflow> workflow;

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
     * @param workflow   The HashMap containing the workflow associated with this architecture.
     */
    public Architecture(String name, HashMap<String, Component> components, HashMap<String, Workflow> workflow) {
        this.name = name;
        this.components = components;
        this.workflow = workflow;
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
     * Changes the name of the architecture.
     * <p>
     * Note that the architecture and its components remains unchanged.
     * </p>
     *
     * @param name the new name for the architecture.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets all the components associated with the current architecture.
     *
     * @return an <code>HashMap</code> containing all the components related to this architecture.
     * @see Component
     */
    public HashMap<String, Component> getComponents() {
        return components;
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
     * Return the workflow associated with this architecture.
     *
     * @return the workflow associated with this architecture.
     * @see Workflow
     */
    public HashMap<String, Workflow> getWorkflow() {
        return workflow;
    }

    /**
     * Adds a workflow to the list of workflow associated with this architecture.
     *
     * @param workflow the workflow to be added.
     */
    public void addWorkflow(Workflow workflow) {
        this.workflow.put(workflow.getName(), workflow);
    }

    /**
     * Removes a workflow from the list of workflow associated with this architecture.
     *
     * @param workflow the workflow to remove from the list.
     */
    public void removeWorkflow(Workflow workflow) {
        this.workflow.remove(workflow.getName(), workflow);
    }

    /**
     * Removes all workflow from the current architecture.
     */
    public void removeAllWorkflow() {
        workflow.clear();
    }
}
