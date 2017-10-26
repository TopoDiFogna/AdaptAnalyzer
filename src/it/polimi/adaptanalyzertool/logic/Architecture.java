package it.polimi.adaptanalyzertool.logic;

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
    private HashSet<Component> components;

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
        components = new HashSet<>();
    }

    /**
     * Creates a new architecture with the specified set of components.
     * <p>
     * More components can be added later by using {@link Architecture#addComponent addComponent}.
     * </p>
     *
     * @param name       The name for this architecture.
     * @param components The HashSet containing the components.
     */
    public Architecture(String name, HashSet<Component> components) {
        this.name = name;
        this.components = components;
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
     * @return an <code>HashSet</code> containing all the components related to this architecture.
     */
    public HashSet<Component> getComponents() {
        return components;
    }

    /**
     * Adds a component to the architecture.
     * <p>
     * No duplicates component can be added.
     * </p>
     *
     * @param component The <code>Component</code> to be added
     *
     * @return <code>true</code> if the component is successfully added to the architecture;
     * <code>false</code> otherwise.
     */
    public boolean addComponent(Component component) {
        return components.add(component);
    }

    /**
     * Removes a component from the current architecture.
     *
     * @param component The component to be removed.
     *
     * @return <code>true</code> if this set contained the specified element
     */
    public boolean removeComponent(Component component) {
        return components.remove(component);
    }

    /**
     * Removes all the component from the current architecture leaving it empty.
     * <p>
     * The architecture is not destroyed by this operation.
     * </p>
     *
     * @return <code>true</code> if the components are successfully removed; <code>false</code> otherwise.
     */
    public boolean removeAllComponents() {
        try {
            components.clear();
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }
}
