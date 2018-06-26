package it.polimi.adaptanalyzertool.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * This class represents a group that contains all the components that provide and require the same services.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class ComponentGroup {

    private Set<Component> components;
    private String name;
    private Set<ComponentGroup> requiredGroups;


    /**
     * <p>
     * Creates an empty ComponentGroup.
     * </p>
     *
     * @param name the name of this group.
     */
    public ComponentGroup(String name) {
        this.name = name;
        this.components = new HashSet<>();
        this.requiredGroups = new HashSet<>();
    }

    /**
     * @return the name of this group.
     */
    public String getName() {
        return name;
    }

    /**
     * @return a Set of the component in this group, possibly empty.
     */
    public Set<Component> getComponents() {
        return components;
    }

    /**
     * <p>
     * Adds a component to this group.
     * </p>
     * <p>
     * Note that if a wrong component is added, i.e. a component with different Required or Provided Service, this
     * method throws an {@link IllegalArgumentException}.
     * </p>
     *
     * @param component the component to be added.
     */
    public void addComponent(Component component) {
        if (components.isEmpty()) {
            components.add(component);
        } else {
            Component testComponent = components.iterator().next();
            if (testComponent.getProvidedServices().equals(component.getProvidedServices()) &&
                    testComponent.getRequiredServices().equals(component.getRequiredServices())) {
                components.add(component);
            } else {
                throw new IllegalArgumentException("Wrong Component added!");
            }
        }
    }

    /**
     * <p>
     * Removes a component from this group and returns it; if no component is removed {@code null} is returned instead.
     * </p>
     *
     * @param component the component to be removed.
     */
    public void removeComponent(Component component) {
        components.remove(component);
    }

    /**
     * @return a {@code HashSet} containing all the Provided Services of this group.
     */
    public Set<ProvidedService> getProvidedServices() {
        HashSet<ProvidedService> providedServices = new HashSet<>();
        for (Component component : components) {
            providedServices.addAll(component.getProvidedServices());
        }
        return providedServices;
    }

    /**
     * @return a {@code Set} containing all the names of the Provided Services of this group.
     */
    public Set<String> getProvidedServicesNames() {
        HashSet<String> providedServicesNames = new HashSet<>();
        Iterator<Component> componentIterator = components.iterator();
        for (ProvidedService ps : componentIterator.next().getProvidedServices()) {
            providedServicesNames.add(ps.getName());
        }
        return providedServicesNames;
    }

    /**
     * @return a {@code Set} containing all the names of the Required Services of this group.
     */
    public Set<String> getRequiredServicesNames() {
        HashSet<String> requiredServicesNames = new HashSet<>();
        Iterator<Component> componentIterator = components.iterator();
        for (RequiredService ps : componentIterator.next().getRequiredServices()) {
            requiredServicesNames.add(ps.getName());
        }
        return requiredServicesNames;
    }

    /**
     * @return a {@code Set} containing all the Required Services of this group.
     */
    public Set<RequiredService> getRequiredServices() {
        HashSet<RequiredService> requiredServices = new HashSet<>();
        for (Component component : components) {
            requiredServices.addAll(component.getRequiredServices());
        }
        return requiredServices;
    }

    /**
     * <p>
     * Adds a group that is required by this group.
     * </p>
     *
     * @param componentGroup the group required by this group.
     */
    public void addRequiredGroup(ComponentGroup componentGroup) {
        if (this.getRequiredServicesNames().containsAll(componentGroup.getProvidedServicesNames())) {
            requiredGroups.add(componentGroup);
        } else {
            throw new IllegalArgumentException("This group is not required");
        }
    }

    /**
     * @return a Set containing the groups required by this one.
     */
    public Set<ComponentGroup> getRequiredGroups() {
        return requiredGroups;
    }
}
