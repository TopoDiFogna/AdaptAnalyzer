package it.polimi.adaptanalyzertool.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ComponentGroup {

    private Set<Component> components;
    private String name;
    private Set<ComponentGroup> requiredGroups;


    public ComponentGroup(String name) {
        this.name = name;
        this.components = new HashSet<>();
        this.requiredGroups = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        if (components.isEmpty()) {
            components.add(component);
        } else {
            Component testComponent = components.iterator().next();
            if (testComponent.getProvidedServices().equals(component.getProvidedServices()) &&
                    testComponent.getRequiredServices().equals(component.getRequiredServices())) {
                components.add(component);
            } else {
                throw new UnsupportedOperationException("Wrong Component added!");
            }
        }
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public Set<ProvidedService> getProvidedServices() {
        HashSet<ProvidedService> providedServices = new HashSet<>();
        for (Component component : components) {
            providedServices.addAll(component.getProvidedServices());
        }
        return providedServices;
    }

    public Set<String> getProvidedServicesNames() {
        HashSet<String> providedServicesNames = new HashSet<>();
        Iterator<Component> componentIterator = components.iterator();
        for (ProvidedService ps : componentIterator.next().getProvidedServices()) {
            providedServicesNames.add(ps.getName());
        }
        return providedServicesNames;
    }

    public Set<String> getRequiredServicesNames() {
        HashSet<String> requiredServicesNames = new HashSet<>();
        Iterator<Component> componentIterator = components.iterator();
        for (RequiredService ps : componentIterator.next().getRequiredServices()) {
            requiredServicesNames.add(ps.getName());
        }
        return requiredServicesNames;
    }

    public Set<RequiredService> getRequiredServices() {
        HashSet<RequiredService> requiredServices = new HashSet<>();
        for (Component component : components) {
            requiredServices.addAll(component.getRequiredServices());
        }
        return requiredServices;
    }

    public void addRequiredGroup(ComponentGroup componentGroup) {
        requiredGroups.add(componentGroup);
    }

    public Set<ComponentGroup> getRequiredGroups() {
        return requiredGroups;
    }
}
