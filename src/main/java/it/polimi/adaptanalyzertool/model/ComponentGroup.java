package it.polimi.adaptanalyzertool.model;

import java.util.HashSet;
import java.util.Set;

public class ComponentGroup {

    Set<Component> components;
    private String name;


    public ComponentGroup(String name) {
        this.name = name;
        this.components = new HashSet<>();

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

    public Set<RequiredService> getRequiredServices() {
        HashSet<RequiredService> requiredServices = new HashSet<>();
        for (Component component : components) {
            requiredServices.addAll(component.getRequiredServices());
        }
        return requiredServices;
    }
}
